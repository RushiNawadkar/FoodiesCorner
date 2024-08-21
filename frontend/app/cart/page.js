"use client"

import { useState, useEffect } from 'react';
import { useSearchParams } from 'next/navigation';
import CartItem from '@/components/cartItems';
import styles from '../../public/static/css/cart.module.css';
import { useRouter } from 'next/navigation';

// Utility functions for localStorage
const getCartItemsFromLocalStorage = () => {
  const storedItems = localStorage.getItem('cartItems');
  return storedItems ? JSON.parse(storedItems) : [];
};

const saveCartItemsToLocalStorage = (items) => {
  localStorage.setItem('cartItems', JSON.stringify(items));
};

export default function CartPage() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const [cartItems, setCartItems] = useState(getCartItemsFromLocalStorage());

  const restId = searchParams.get('restId');
  const image = searchParams.get('image');
  const title = searchParams.get('title');
  const price = searchParams.get('price');

  console.log('Search Params:', restId, image, title, price);

  useEffect(() => {

    if (image && title && price) {
      const numericPrice = parseFloat(price);
      if (isNaN(numericPrice)) return;

      setCartItems(prevItems => {
        const existingItemIndex = prevItems.findIndex(item => item.name === title);
        let updatedItems;
        if (existingItemIndex !== -1) {
          updatedItems = [...prevItems];
          // updatedItems[existingItemIndex].quantity += 1;
        } else {
          updatedItems = [
            ...prevItems,
            {
              id: Date.now(),
              name: title,
              quantity: 1,
              price: numericPrice,
              image
            }
          ];
        }

        saveCartItemsToLocalStorage(updatedItems);
        return updatedItems;
      });
    }
  }, [searchParams]);

  useEffect(() => {
    saveCartItemsToLocalStorage(cartItems);
  }, [cartItems]);

  const handleQuantityChange = (id, change) => {
    setCartItems(prevItems =>
      prevItems.map(item =>
        item.id === id
          ? { ...item, quantity: Math.max(item.quantity + change, 1) }
          : item
      )
    );
  };

  const handleDelete = (id) => {
    setCartItems(prevItems =>
      prevItems.filter(item => item.id !== id)
    );
  };

  const handleRedirect = () => {
    router.push(`/checkout?totalPrice=${encodeURIComponent(totalPrice)}
    &restId=${encodeURIComponent(restId)}`);
}


  const totalItems = cartItems.reduce((sum, item) => sum + item.quantity, 0);
  const totalPrice = cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0).toFixed(2);

  return (
    <section className={styles.cart}>
      <h2>Your Cart</h2>
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        cartItems.map(item => (
          <CartItem
            key={item.id}
            item={item}
            onQuantityChange={handleQuantityChange}
            onDelete={handleDelete}
          />
        ))
      )}
      <div className={styles.cartSummary}>
        <h2>Order Summary</h2>
        <p>Total Items: {totalItems}</p>
        <p>Total Price: {totalPrice} Rs</p>
        <button
          onClick={handleRedirect}
          className={styles.deleteBtn}>Proceed to Checkout</button>
      </div>
    </section>
  );
}
