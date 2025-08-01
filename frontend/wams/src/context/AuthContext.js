import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  // Load user from localStorage on page load
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem("user");
    return storedUser ? JSON.parse(storedUser) : null;
  });

  // Save user to localStorage on login
  const login = (userData) => {
    localStorage.setItem("user", JSON.stringify(userData));
    setUser(userData);
  };

  // Clear user on logout
  const logout = () => {
    localStorage.removeItem("user");
    setUser(null);
  };

  // Optional: keep synced with localStorage changes
  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser && !user) {
      setUser(JSON.parse(storedUser));
    }
  }, [user]);

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
