"use client"
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react';

export default function Checkout() {
    const router = useRouter();
    const [totalPrice, setTotalPrice] = useState(null);
    const [restId, setRestId] = useState(null);

    useEffect(() => {
        // Get query parameters from the URL
        const params = new URLSearchParams(window.location.search);
        const price = params.get('totalPrice');
        const id = params.get('restId');

        setTotalPrice(price);
        setRestId(id);
    }, []);

    return (
        <div>
            <h1>Checkout</h1>
            <p>Total Price: {totalPrice ? `$${totalPrice}` : 'Loading...'}</p>
            <p>Rest Id: {restId ? restId : 'Loading...'}</p>
        </div>
    );
}
