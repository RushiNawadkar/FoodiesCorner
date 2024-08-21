// app/profile/page.js
"use client"
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import styles from '../../public/static/css/userDashboard.module.css';

// Fetch user data using Axios
const fetchUserData = async () => {
  try {
    const token = localStorage.getItem("loginToken");
    const parsedData = JSON.parse(token);
    // console.log("Email : ",parsedData.jwt);
    const response = await axios.get(`http://localhost:8080/users/user/${parsedData.email}`, {
      headers: {
        "Authorization": `Bearer ${parsedData.jwt}`
      }
    });
    // console.log(("user details : ", response.data));

    // Fetch user orders
    const ordersResponse = await axios.get(`http://localhost:8080/users/orders/${parsedData.email}`, {
      headers: {
        "Authorization": `Bearer ${parsedData.jwt}`
      }
    });
    console.log(("user Orders : ", ordersResponse.data));

    return {
      user: response.data,
      orders: ordersResponse.data
    };
  } catch (error) {
    console.error('Error fetching user data:', error);
    return null;
  }
};

const ProfilePage = () => {
  const [user, setUser] = useState({
    email: "",
    name: "",
    phone: "",
    userId: null
  });

  const [orders, setOrders] = useState({
    "orderId": 0,
    "userId": 0,
    "restaurantId": 0,
    "orderStatus": "string",
    "totalAmount": 0,
    "paymentId": 0,
    "orderId": 0,
    "amount": 0,
    "paymentMethod": "string",
    "paymentStatus": "string",
    "deliveryPersonId": 0
  })

  useEffect(() => {
    const getUserData = async () => {
      const userData = await fetchUserData();
      if(userData.user){
        setUser({
          name: userData.user.name,
          email: userData.user.email,
          phone: userData.user.phone,
          userId: userData.user.userId
        });
      }
      if(userData.orders){
        setOrders({
          "orderId": userData.orders.orderId,
          "userId": userData.orders.userId,
          "restaurantId": userData.orders.restaurantId,
          "orderStatus": userData.orders.orderId,
          "totalAmount": userData.orders.totalAmount,
          "paymentId": userData.orders.paymentId,
          "orderId": userData.orders.orderId,
          "amount": userData.orders.amount,
          "paymentMethod": userData.orders.paymentMethod,
          "paymentStatus": userData.orders.paymentMethod,
          "deliveryPersonId": userData.orders.deliveryPersonId
        })
      };
      }

    getUserData();
  }, []);

  if (!user) {
    return <div className={styles.container}>Loading...</div>;
  }

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Profile</h1>
      <div className={styles.profileCard}>
        <p >Name : {user.name}</p>
        <p className={styles.profileEmail}>Email: {user.email}</p>
        <p className={styles.profilePhone}>Phone: {user.phone}</p>

        {/* <div className={styles.buttonContainer}>
          <button className={styles.editButton}>Edit Profile</button>
        </div> */}
      </div>

      <div className={styles.profileCard}>
        <p>order id : {orders.orderId}</p>
        <p>user Id: {orders.userId}</p>
        <p>restaurant Id: {orders.restaurantId}</p>
        <p>order Status: {orders.orderStatus}</p>
        <p>total Amount: {orders.totalAmount}</p>
        <hr/>
        <p>payment Id: {orders.paymentId}</p>
        <p>order Id: {orders.orderId}</p>
        <p>payment Method: {orders.paymentMethod}</p>
        <p>payment Status: {orders.paymentStatus}</p>
        <p>delivery PersonId: {orders.deliveryPersonId}</p>

        {/* <div className={styles.buttonContainer}>
          <button className={styles.editButton}>Edit Profile</button>
        </div> */}
      </div>
    </div>
  );
};

export default ProfilePage;
