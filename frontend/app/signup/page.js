'use client';

import { useState } from 'react';
import axios from 'axios';
import styles from "../../public/static/css/signup.module.css";
import Link from 'next/link';

const SignupForm = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    password: '',
    address: '',
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(null);

    try {
      const response = await axios.post('http://localhost:8080/users/signup', formData,{
        headers:{"Content-Type":"application/json"}
      });
      setSuccess('Signup successful!');
      console.log('Response:', response.data);
      setFormData({
        name: '',
        email: '',
        mobile: '',
        password: '',
        address: '',
      });
    } catch (err) {
      setError(err.response.data.message );
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={`d-flex justify-content-center align-items-center vh-100 ${styles.background}`}>
      <div className={`card p-4 shadow-sm ${styles.regForm}`} style={{ width: '400px' }}>
        <h3 className="card-title text-center mb-4">Sign Up</h3>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            {/* <label htmlFor="name" className="form-label">Name:</label> */}
            <input
              type="text"
              id="name"
              name="name"
              placeholder='Enter Name'
              className={`form-contro ${styles.formInput}`}
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            {/* <label htmlFor="email" className="form-label">Email:</label> */}
            <input
              type="email"
              id="email"
              name="email"
              placeholder='Enter Email'
              className={`form-contro ${styles.formInput}`}
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            {/* <label htmlFor="mobile" className="form-label">Mobile Number:</label> */}
            <input
              type="text"
              id="mobile"
              name="phone"
              placeholder='Enter Mobile Number'
              className={`form-contro ${styles.formInput}`}
              value={formData.mobile}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            {/* <label htmlFor="password" className="form-label">Password:</label> */}
            <input
              type="password"
              id="password"
              name="password"
              placeholder='Enter Password'
              className={`form-contro ${styles.formInput}`}
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
          {/* <div className="mb-3">
            <label htmlFor="address" className="form-label">Address:</label>
            <input
              type="text"
              id="address"
              name="address"
              className={`form-contro ${styles.formInput}`}
              value={formData.address}
              onChange={handleChange}
              required
            />
          </div> */}
          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}
          <button type="submit" className={`btn w-100 ${styles.button}`} disabled={loading}>
            {loading ? 'Signing Up...' : 'Sign Up'}
          </button>
        </form>
        
      </div>
    </div>
  );
};

export default SignupForm;
