import React, { useState } from 'react';
import { api } from '../../services/api';

const RequestTimeOff = () => {
  const user = JSON.parse(localStorage.getItem('user'));
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [reason, setReason] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post(`/timeoff/request/${user.username}`, {
        startDate,
        endDate,
        reason
      });
      setMessage('Time-off request submitted successfully.');
      setStartDate('');
      setEndDate('');
      setReason('');
    } catch (err) {
      setMessage('Failed to submit time-off request.');
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h2 className="text-2xl font-bold mb-4">Request Time Off</h2>
      <form onSubmit={handleSubmit} className="bg-white p-6 rounded shadow space-y-4 max-w-md">
        <div>
          <label className="block font-medium">Start Date</label>
          <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <div>
          <label className="block font-medium">End Date</label>
          <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <div>
          <label className="block font-medium">Reason</label>
          <textarea value={reason} onChange={(e) => setReason(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Submit</button>
        {message && <p className="mt-4 text-sm font-medium text-green-600">{message}</p>}
      </form>
    </div>
  );
};

export default RequestTimeOff;
