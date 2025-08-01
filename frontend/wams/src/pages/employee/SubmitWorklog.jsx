import React, { useState } from 'react';
import { api } from '../../services/api';

const SubmitWorklog = () => {
  const user = JSON.parse(localStorage.getItem('user'));
  const [date, setDate] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [details, setDetails] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/employee/worklog/submit', {
        username: user.username,
        date,
        startTime,
        endTime,
        details
      });
      setMessage('Worklog submitted successfully.');
      setDate('');
      setStartTime('');
      setEndTime('');
      setDetails('');
    } catch (err) {
      setMessage('Failed to submit worklog.');
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h2 className="text-2xl font-bold mb-4">Submit Worklog</h2>
      <form onSubmit={handleSubmit} className="bg-white p-6 rounded shadow space-y-4 max-w-md">
        <div>
          <label className="block font-medium">Date</label>
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <div>
          <label className="block font-medium">Start Time</label>
          <input type="time" value={startTime} onChange={(e) => setStartTime(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <div>
          <label className="block font-medium">End Time</label>
          <input type="time" value={endTime} onChange={(e) => setEndTime(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <div>
          <label className="block font-medium">Details</label>
          <textarea value={details} onChange={(e) => setDetails(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Submit</button>
        {message && <p className="mt-4 text-sm font-medium text-green-600">{message}</p>}
      </form>
    </div>
  );
};

export default SubmitWorklog;