"use client"
import React, { useState } from 'react';
import { useRouter } from 'next/navigation';

const VerifyOtp = () => {
  const [otp, setOtp] = useState('');
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
      const response = await fetch('/api/verify-otp', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ otp }),
      });

      const data = await response.json();

      if (response.ok) {
        setSuccess('OTP verified! You can now reset your password.');
        setOtp('');
        router.push('/reset-password'); // Redirect to password reset page
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
      <h1>Verify OTP</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="otp">OTP</label>
        <input
          id="otp"
          type="text"
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Verifying...' : 'Verify OTP'}
        </button>
        {error && <p className="error">{error}</p>}
        {success && <p className="success">{success}</p>}
      </form>
      <style jsx>{`
        .container {
          max-width: 400px;
          margin: 30px auto;
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

export default VerifyOtp;
