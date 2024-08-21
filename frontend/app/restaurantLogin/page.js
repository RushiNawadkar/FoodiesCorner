'use client';

import { useState } from 'react';
import axios from 'axios';
import styles from "../../public/static/css/signup.module.css";
import { useRouter } from 'next/navigation';
import Link from 'next/link';

const Login = () => {
  const router = useRouter();
  const [formData, setFormData] = useState({
    email: '',
    password: '',
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
      const response = await axios.post('http://localhost:8080/users/signin', formData, {
        headers: { "Content-Type": "application/json" }
      });
      localStorage.setItem("loginToken",JSON.stringify({
        email: formData.email,
        jwt: response.data.jwt
      }));
      setSuccess('Login successful!');
      // console.log('Response:', response.data.jwt);
      setFormData({
        email: '',
        password: '',
      })
      // Redirect or perform another action on successful login
      router.push("/userDashboard");
    } catch (err) {
      setError(err.response?.data?.message || 'An error occurred');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={`d-flex justify-content-center align-items-center vh-100 ${styles.background}`}>
      <div className={`card p-4 shadow-sm ${styles.regForm}`} style={{ width: '400px' }}>
        <h3 className="card-title text-center mb-4">Restaurent Login</h3>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <input
              type="email"
              id="email"
              name="email"
              placeholder='Enter your email'
              className={`form-control ${styles.formInput}`}
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            <input
              type="password"
              id="password"
              name="password"
              placeholder='Enter your password'
              className={`form-control ${styles.formInput}`}
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}
          <button type="submit" className={`btn btn-primary w-100 ${styles.button}`} disabled={loading}>
            {loading ? 'Logging In...' : 'Login'}
          </button>
        </form>
        <Link 
        href="/signup"
        style={{margin:"8px auto", }}
        >Not Registered? SignUp</Link>
        <Link 
        href="/forgetPassword"
        style={{}}
        >Forget Password?</Link>
      </div>
    </div>
  );
};

export default Login;
