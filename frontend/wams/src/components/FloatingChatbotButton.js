import React, { useEffect, useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';

export default function FloatingChatButton() {
    const [showTip, setShowTip] = useState(true);

    // Optional: hide the tooltip after 5 seconds
    useEffect(() => {
        const timer = setTimeout(() => setShowTip(false), 5000);
        return () => clearTimeout(timer);
    }, []);

    return (
        <div style={{ position: 'fixed', right: 20, bottom: 20, zIndex: 9999 }}>
            {/* Tooltip */}
            {showTip && (
                <div
                    style={{
                        background: 'rgba(255,255,255,0.9)',
                        color: '#333',
                        padding: '8px 12px',
                        borderRadius: '20px',
                        fontSize: '14px',
                        marginBottom: '8px',
                        boxShadow: '0 4px 10px rgba(0,0,0,0.15)',
                        textAlign: 'center',
                        whiteSpace: 'nowrap',
                        animation: 'fadeIn 0.5s ease-in-out',
                    }}
                >
                    💬 Chat with Team
                </div>
            )}

            {/* Button */}
            <RouterLink
                to="/chat"
                aria-label="Open chat"
                style={{
                    width: 56,
                    height: 56,
                    borderRadius: '50%',
                    background: '#6C63FF', // <-- update this color to match theme
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    boxShadow: '0 10px 20px rgba(0,0,0,0.3)',
                    textDecoration: 'none',
                    fontSize: '24px',
                    color: 'white',
                    animation: 'popIn 0.5s ease-out',
                }}
            >
                💬
            </RouterLink>

            {/* Animations */}
            <style>
                {`
          @keyframes popIn {
            0% { transform: scale(0); opacity: 0; }
            100% { transform: scale(1); opacity: 1; }
          }
          @keyframes fadeIn {
            0% { opacity: 0; transform: translateY(10px); }
            100% { opacity: 1; transform: translateY(0); }
          }
        `}
            </style>
        </div>
    );
}
