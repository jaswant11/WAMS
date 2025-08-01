import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';

const ApproveTimeOff = () => {
  const [requests, setRequests] = useState([]);
  const [message, setMessage] = useState('');

  const fetchRequests = async () => {
    try {
      const res = await api.get('/timeoff/all');
      setRequests(res.data);
    } catch (err) {
      console.error('Failed to fetch time-off requests', err);
    }
  };

  const handleDecision = async (id, approve) => {
    try {
      const endpoint = approve
        ? `/timeoff/approve/${id}`
        : `/timeoff/deny/${id}`;
      await api.put(endpoint);
      setMessage(`Request ${approve ? 'approved' : 'denied'} successfully.`);
      fetchRequests(); // refresh list
    } catch (err) {
      console.error('Failed to update request', err);
      setMessage('Error processing request.');
    }
  };

  useEffect(() => {
    fetchRequests();
  }, []);

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Employee Time-Off Requests</h2>

      {requests.length === 0 ? (
        <p>No time-off requests pending.</p>
      ) : (
        <table className="w-full bg-white shadow rounded overflow-hidden">
          <thead className="bg-gray-200">
            <tr>
              <th className="p-2">Employee</th>
              <th className="p-2">Start Date</th>
              <th className="p-2">End Date</th>
              <th className="p-2">Reason</th>
              <th className="p-2">Status</th>
              <th className="p-2">Actions</th>
            </tr>
          </thead>
          <tbody>
            {requests.map((req) => (
              <tr key={req.id} className="border-t">
                <td className="p-2">{req.employeeName || req.username}</td>
                <td className="p-2">{req.startDate}</td>
                <td className="p-2">{req.endDate}</td>
                <td className="p-2">{req.reason}</td>
                <td className="p-2">{req.status}</td>
                <td className="p-2 space-x-2">
                  {req.status === 'PENDING' && (
                    <>
                      <button
                        onClick={() => handleDecision(req.id, true)}
                        className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
                      >
                        Approve
                      </button>
                      <button
                        onClick={() => handleDecision(req.id, false)}
                        className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700"
                      >
                        Deny
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {message && <p className="mt-4 font-semibold text-blue-600">{message}</p>}
    </div>
  );
};

export default ApproveTimeOff;
