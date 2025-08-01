import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';

const ShiftTemplateManager = () => {
  const [templates, setTemplates] = useState([]);
  const [name, setName] = useState('');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [daysOfWeek, setDaysOfWeek] = useState([]);
  const [message, setMessage] = useState('');

  const dayOptions = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];

  const toggleDay = (day) => {
    setDaysOfWeek((prev) =>
      prev.includes(day) ? prev.filter((d) => d !== day) : [...prev, day]
    );
  };

  const fetchTemplates = async () => {
    const res = await api.get('/templates/all');
    setTemplates(res.data);
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      await api.post('/templates/create', {
        name,
        startTime,
        endTime,
        daysOfWeek
      });
      setName('');
      setStartTime('');
      setEndTime('');
      setDaysOfWeek([]);
      setMessage('Template created successfully.');
      fetchTemplates();
    } catch (err) {
      setMessage('Failed to create template.');
    }
  };

  useEffect(() => {
    fetchTemplates();
  }, []);

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h2 className="text-2xl font-bold mb-4">Shift Template Manager</h2>

      <form onSubmit={handleCreate} className="bg-white p-6 rounded shadow space-y-4 max-w-lg mb-6">
        <div>
          <label className="block font-medium">Template Name</label>
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} className="w-full border p-2 rounded" required />
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block font-medium">Start Time</label>
            <input type="time" value={startTime} onChange={(e) => setStartTime(e.target.value)} className="w-full border p-2 rounded" required />
          </div>
          <div>
            <label className="block font-medium">End Time</label>
            <input type="time" value={endTime} onChange={(e) => setEndTime(e.target.value)} className="w-full border p-2 rounded" required />
          </div>
        </div>
        <div>
          <label className="block font-medium mb-1">Days of the Week</label>
          <div className="grid grid-cols-2 gap-2">
            {dayOptions.map((day) => (
              <label key={day} className="inline-flex items-center">
                <input
                  type="checkbox"
                  checked={daysOfWeek.includes(day)}
                  onChange={() => toggleDay(day)}
                  className="mr-2"
                />
                {day}
              </label>
            ))}
          </div>
        </div>
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Create Template</button>
        {message && <p className="text-green-600 mt-2">{message}</p>}
      </form>

      <div className="bg-white p-4 rounded shadow">
        <h3 className="text-lg font-semibold mb-2">Existing Templates</h3>
        {templates.length === 0 ? (
          <p>No templates found.</p>
        ) : (
          <table className="w-full table-auto">
            <thead className="bg-gray-200">
              <tr>
                <th className="p-2">Name</th>
                <th className="p-2">Start</th>
                <th className="p-2">End</th>
                <th className="p-2">Days</th>
              </tr>
            </thead>
            <tbody>
              {templates.map((tpl) => (
                <tr key={tpl.id} className="border-t">
                  <td className="p-2">{tpl.name}</td>
                  <td className="p-2">{tpl.startTime}</td>
                  <td className="p-2">{tpl.endTime}</td>
                  <td className="p-2">{tpl.daysOfWeek?.join(', ')}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default ShiftTemplateManager;