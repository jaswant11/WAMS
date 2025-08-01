import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';

const FatigueScore = () => {
  const user = JSON.parse(localStorage.getItem('user'));
  const [score, setScore] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchScore = async () => {
      try {
        const res = await api.get(`/fatigue/employee/${user.username}`);
        setScore(res.data.score);
      } catch (err) {
        setScore(null);
      } finally {
        setLoading(false);
      }
    };
    fetchScore();
  }, [user.username]);

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h2 className="text-2xl font-bold mb-4">Your Fatigue Score</h2>
      <div className="bg-white p-6 rounded shadow text-center">
        {loading ? (
          <p>Loading...</p>
        ) : score !== null ? (
          <p className="text-3xl font-semibold text-blue-700">{score}</p>
        ) : (
          <p className="text-red-600">Failed to load fatigue score.</p>
        )}
      </div>
    </div>
  );
};

export default FatigueScore;
