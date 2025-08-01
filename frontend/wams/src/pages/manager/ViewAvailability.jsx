import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';

const ViewAvailability = () => {
  const [availabilities, setAvailabilities] = useState([]);

  useEffect(() => {
    fetchAvailability();
  }, []);

  const fetchAvailability = async () => {
    try {
      const res = await api.get('/availability/all');
      setAvailabilities(res.data);
    } catch (err) {
      console.error('Failed to fetch availability', err);
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Employee Availabilities</h2>

      {availabilities.length === 0 ? (
        <p>No availability records found.</p>
      ) : (
        <table className="w-full bg-white shadow rounded overflow-hidden">
          <thead className="bg-gray-200">
            <tr>
              <th className="p-2">Employee</th>
              <th className="p-2">Start Date</th>
              <th className="p-2">End Date</th>
              <th className="p-2">Start Time</th>
              <th className="p-2">End Time</th>
            </tr>
          </thead>
          <tbody>
            {availabilities.map((a) => (
              <tr key={a.id} className="border-t">
                <td className="p-2">{a.employeeName || a.username}</td>
                <td className="p-2">{a.startDate}</td>
                <td className="p-2">{a.endDate}</td>
                <td className="p-2">{a.startTime}</td>
                <td className="p-2">{a.endTime}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ViewAvailability;
