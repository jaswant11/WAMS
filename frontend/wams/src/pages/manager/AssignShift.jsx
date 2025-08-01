import React, { useState, useEffect } from 'react';
import { api } from '../../services/api';

const AssignShift = () => {
  const [templates, setTemplates] = useState([]);
  const [selectedTemplateId, setSelectedTemplateId] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const [recommendations, setRecommendations] = useState([]);
  const [selectedEmployees, setSelectedEmployees] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    fetchTemplates();
  }, []);

  const fetchTemplates = async () => {
    const res = await api.get('/templates/all');
    setTemplates(res.data);
  };

  const fetchRecommendations = async () => {
    if (!selectedTemplateId || !selectedDate) return;
    try {
      const res = await api.get(`/fatigue/recommend/${selectedDate}/${selectedTemplateId}`);
      setRecommendations(res.data);
      setSelectedEmployees([]);
      setMessage('');
    } catch (err) {
      console.error('Failed to fetch recommendations', err);
    }
  };

  const handleEmployeeSelect = (id) => {
    setSelectedEmployees((prev) =>
      prev.includes(id) ? prev.filter((eid) => eid !== id) : [...prev, id]
    );
  };
const assignShift = async () => {
  if (!selectedTemplateId || !selectedEmployees.length || !selectedDate) {
    setMessage('Please select template, date, and employees');
    return;
  }

  const queryParams = new URLSearchParams();
  selectedEmployees.forEach((id) => queryParams.append('employeeIds', id));
  queryParams.append('startDate', selectedDate);
  queryParams.append('endDate', selectedDate); // assigning for one day

  try {
    await api.post(
      `/manager/shift-template/assign/${selectedTemplateId}?${queryParams.toString()}`,
      {}
    );
    setMessage('Shift assigned successfully!');
    setSelectedEmployees([]);
  } catch (err) {
    console.error('Failed to assign shift', err);
    setMessage('Error assigning shift');
  }
};

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Assign Employees to Shift</h2>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
        <select
          className="border p-2 rounded"
          value={selectedTemplateId}
          onChange={(e) => setSelectedTemplateId(e.target.value)}
        >
          <option value="">Select Shift Template</option>
          {templates.map((t) => (
            <option key={t.id} value={t.id}>
              {t.name} ({t.startTime}–{t.endTime})
            </option>
          ))}
        </select>

        <input
          type="date"
          className="border p-2 rounded"
          value={selectedDate}
          onChange={(e) => setSelectedDate(e.target.value)}
        />

        <button
          onClick={fetchRecommendations}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Get Recommended Employees
        </button>
      </div>

      {recommendations.length > 0 && (
        <>
          <h3 className="text-lg font-semibold mb-2">Recommended Employees</h3>
          <table className="w-full bg-white shadow rounded overflow-hidden">
            <thead className="bg-gray-100">
              <tr>
                <th className="p-2">Select</th>
                <th className="p-2">Name</th>
                <th className="p-2">Department</th>
                <th className="p-2">Fatigue Score</th>
              </tr>
            </thead>
            <tbody>
              {recommendations.map((emp) => (
                <tr key={emp.employeeId} className="border-t">
                  <td className="p-2 text-center">
                    <input
                      type="checkbox"
                      checked={selectedEmployees.includes(emp.employeeId)}
                      onChange={() => handleEmployeeSelect(emp.employeeId)}
                    />
                  </td>
                  <td className="p-2">{emp.name}</td>
                  <td className="p-2">{emp.department}</td>
                  <td className="p-2">{emp.fatigueScore}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <button
            onClick={assignShift}
            className="mt-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
          >
            Assign Selected Employees
          </button>
        </>
      )}

      {message && <p className="mt-4 font-semibold">{message}</p>}
    </div>
  );
};

export default AssignShift;
