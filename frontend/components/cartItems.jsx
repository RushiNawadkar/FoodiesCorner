// components/CartItem.js
import styles from '../public/static/css/cart.module.css';
import Image from 'next/image';

const CartItem = ({ item, onQuantityChange, onDelete }) => {
  const { id, name, quantity, price, image } = item;

  // Ensure price is a number
  const numericPrice = parseFloat(price);
  if (isNaN(numericPrice)) {
    console.error(`Invalid price value: ${price}`);
    return null; // or handle the error as needed
  }

  const totalPrice = (numericPrice * quantity).toFixed(2);

  return (
    <div className={styles.cartItem}>
      {/* { image!==undefined ?  */}
      <Image
        src={image}
        alt={name}
        width={100}  // Adjust as needed
        height={100} // Adjust as needed
        className={styles.itemImage}
      />
      {/* : "" } */}
      <div className={styles.itemDetails}>
        <h3>{name}</h3>
        <div className={styles.quantityControls}>
          <button onClick={() => onQuantityChange(id, -1)} className={styles.quantityBtn}>-</button>
          <p>Quantity: {quantity}</p>
          <button onClick={() => onQuantityChange(id, 1)} className={styles.quantityBtn}>+</button>
        </div>
        <p>Price: {numericPrice.toFixed(2)} Rs each</p>
        <button className={styles.deleteBtn} onClick={() => onDelete(id)}>Remove</button>
      </div>
      <div className={styles.itemTotal}>
        <p>{totalPrice} Rs</p>
      </div>
    </div>
  );
};

export default CartItem;
