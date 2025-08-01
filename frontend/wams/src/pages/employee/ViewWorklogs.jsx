import React, { useState } from 'react';
import { api } from '../../services/api';

const ViewWorklogs = () => {
  const user = JSON.parse(localStorage.getItem('user'));
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [worklogs, setWorklogs] = useState([]);
  const [totalHours, setTotalHours] = useState(0);

  const fetchWorklogs = async (e) => {
    e.preventDefault();
    try {
      const res = await api.post('/worklog/view', {
        username: user.username,
        startDate,
        endDate
      });
      setWorklogs(res.data.entries || []);
      setTotalHours(res.data.totalHours || 0);
    } catch (err) {
      console.error('Failed to fetch worklogs');
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h2 className="text-2xl font-bold mb-4">View Worklogs</h2>

      <form onSubmit={fetchWorklogs} className="bg-white p-4 rounded shadow space-y-4 max-w-md mb-6">
        <div>
          <label className="block font-medium">Start Date</label>
          <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <div>
          <label className="block font-medium">End Date</label>
          <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Fetch Worklogs</button>
      </form>

      {worklogs.length > 0 && (
        <div className="bg-white p-4 rounded shadow">
          <h3 className="text-lg font-semibold mb-2">Worklog Entries</h3>
          <table className="w-full table-auto border">
            <thead className="bg-gray-200">
              <tr>
                <th className="p-2 border">Date</th>
                <th className="p-2 border">Start</th>
                <th className="p-2 border">End</th>
                <th className="p-2 border">Hours</th>
                <th className="p-2 border">Details</th>
              </tr>
            </thead>
            <tbody>
              {worklogs.map((w, i) => (
                <tr key={i} className="border-t">
                  <td className="p-2 border">{w.date}</td>
                  <td className="p-2 border">{w.startTime}</td>
                  <td className="p-2 border">{w.endTime}</td>
                  <td className="p-2 border">{w.hours}</td>
                  <td className="p-2 border">{w.details}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <p className="mt-4 font-medium">Total Hours: {totalHours}</p>
        </div>
      )}
    </div>
  );
};

export default ViewWorklogs;