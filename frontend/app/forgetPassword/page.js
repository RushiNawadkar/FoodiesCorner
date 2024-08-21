"use client"
import React, { useState } from 'react';
import { useRouter } from 'next/navigation';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const router = useRouter();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(null);

    try {
      const response = await fetch('/api/request-otp', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email }),
      });

      const data = await response.json();

      if (response.ok) {
        setSuccess('OTP sent to your email! Check your inbox.');
        setEmail('');
        router.push('/verify-otp'); // Redirect to OTP verification page
      } else {
        setError(data.message || 'An error occurred.');
      }
    } catch (err) {
      setError('An unexpected error occurred.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>Forgot Your Password?</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="email">Email</label>
        <input
          id="email"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Sending...' : 'Send OTP'}
        </button>
        {error && <p className="error">{error}</p>}
        {success && <p className="success">{success}</p>}
      </form>
      <style jsx>{`
        .container {
          max-width: 400px;
          margin: auto;
          padding: 1rem;
          text-align: center;
        }
        form {
          display: flex;
          flex-direction: column;
        }
        label {
          margin-bottom: 0.5rem;
        }
        input {
          margin-bottom: 1rem;
          padding: 0.5rem;
        }
        button {
          padding: 0.5rem;
        }
        .error {
          color: red;
        }
        .success {
          color: green;
        }
      `}</style>
    </div>
  );
};

export default ForgotPassword;
