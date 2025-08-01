// src/pages/login/Login.jsx
import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../../services/api';
import { AuthContext } from '../../context/AuthContext';
import './LoginForm.css'; // Import the CSS for styling

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [focusField, setFocusField] = useState(null);

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const res = await api.post('/Login', { username, password });
      const userData = res.data;
      console.log("Login response from backend:", res.data);
      login(userData);

      if (userData.role === 'ADMIN') navigate('/admin/dashboard');
      else if (userData.role === 'EMPLOYEE') navigate('/employee/dashboard');
      else if (userData.role === 'MANAGER') navigate('/manager/dashboard');
      else setError('Unknown role');
    } catch (err) {
      setError('Invalid credentials or server error');
    }
  };

  return (
    <div className="login-container">
      <div className="login-background">
        <div className="gradient-bg">
          <div className="gradient-circle-top"></div>
          <div className="gradient-circle-bottom"></div>
        </div>
      </div>

      <div className="login-card">
        <div className="card-header">
          
          <h2 className="welcome-title">
            <span className="welcome-text">Welcome to</span>
            <span className="company-name">WAMS</span>
          </h2>
          <p className="welcome-subtitle">Sign in to WAMS</p>
        </div>

        <form onSubmit={handleLogin} className="login-form">
          <div className={`form-group ${focusField === 'username' ? 'focused' : ''} ${username ? 'has-value' : ''}`}>
            <input
              type="text"
              id="username"
              name="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              onFocus={() => setFocusField('username')}
              onBlur={() => setFocusField(null)}
              required
            />
            <label htmlFor="username">Username</label>
            <div className="input-highlight"></div>
            <i className="input-icon">👤</i>
          </div>

          <div className={`form-group ${focusField === 'password' ? 'focused' : ''} ${password ? 'has-value' : ''}`}>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              onFocus={() => setFocusField('password')}
              onBlur={() => setFocusField(null)}
              required
            />
            <label htmlFor="password">Password</label>
            <div className="input-highlight"></div>
            <i className="input-icon">🔒</i>
          </div>

          <div className="form-actions">
            <button type="submit" className="login-btn">
              <span className="btn-text">Login</span>
              <span className="btn-icon">→</span>
              <div className="btn-glow"></div>
            </button>
          </div>

          {error && (
            <div className="error-message">
              <i className="error-icon">⚠️</i>
              {error}
            </div>
          )}
        </form>

        <div className="card-footer">
          <button className="forgot-password">Forgot password?</button>
          <div className="divider">
            <span>or</span>
          </div>
          <button className="create-account">
            Create new account <span>→</span>
          </button>
        </div>
      </div>

      {/* Animated floating elements */}
      <div className="floating-elements">
        <div className="floating-circle circle-1"></div>
        <div className="floating-circle circle-2"></div>
        <div className="floating-circle circle-3"></div>
        <div className="floating-square"></div>
        <div className="floating-triangle"></div>
      </div>
    </div>
  );
}

export default Login;
