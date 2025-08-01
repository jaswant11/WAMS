import React, { useState } from 'react';
import { api } from '../../services/api';

const ViewWorklogsManager = () => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [logs, setLogs] = useState([]);
  const [filter, setFilter] = useState('');

 const fetchLogs = async () => {
  try {
    const res = await api.get('/employee/worklog/all');
    setLogs(res.data);
  } catch (err) {
    console.error('Failed to fetch worklogs', err);
  }
};


  const filteredLogs = logs.filter((log) =>
    log.employeeName?.toLowerCase().includes(filter.toLowerCase())
  );

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">All Employee Worklogs</h2>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
        <input
          type="date"
          className="border p-2 rounded"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
        />
        <input
          type="date"
          className="border p-2 rounded"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
        />
        <button
          onClick={fetchLogs}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          View Logs
        </button>
      </div>

      {logs.length > 0 && (
        <div className="mb-4">
          <input
            type="text"
            placeholder="Filter by employee name"
            className="border p-2 rounded w-full md:w-1/3"
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
          />
        </div>
      )}

      <div className="overflow-auto">
        <table className="w-full bg-white shadow rounded overflow-hidden">
          <thead className="bg-gray-200">
            <tr>
              <th className="p-2">Employee</th>
              <th className="p-2">Date</th>
              <th className="p-2">Start Time</th>
              <th className="p-2">End Time</th>
              <th className="p-2">Details</th>
              <th className="p-2">Hours</th>
            </tr>
          </thead>
          <tbody>
            {filteredLogs.map((log) => (
              <tr key={log.id} className="border-t">
                <td className="p-2">{log.employeeName}</td>
                <td className="p-2">{log.date}</td>
                <td className="p-2">{log.startTime}</td>
                <td className="p-2">{log.endTime}</td>
                <td className="p-2">{log.details}</td>
                <td className="p-2">{log.hoursWorked}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ViewWorklogsManager;
