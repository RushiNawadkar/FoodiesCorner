"use client"
import React, { useState } from 'react';
import { useRouter } from 'next/navigation';

const ResetPassword = () => {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const router = useRouter();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(null);

    if (password !== confirmPassword) {
      setError('Passwords do not match.');
      setLoading(false);
      return;
    }

    try {
      const response = await fetch('/api/reset-password', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ password }),
      });

      const data = await response.json();

      if (response.ok) {
        setSuccess('Password reset successful! You can now log in.');
        setPassword('');
        setConfirmPassword('');
        setTimeout(() => router.push('/login'), 2000); // Redirect after a short delay
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
      <h1>Reset Your Password</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="password">New Password</label>
        <input
          id="password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <label htmlFor="confirmPassword">Confirm Password</label>
        <input
          id="confirmPassword"
          type="password"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Resetting...' : 'Reset Password'}
        </button>
        {error && <p className="error">{error}</p>}
        {success && <p className="success">{success}</p>}
      </form>
      <style jsx>{`
        .container {
          max-width: 400px;
          margin: 20px auto;
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

export default ResetPassword;
