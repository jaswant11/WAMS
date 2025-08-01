import React, { useEffect, useState } from 'react';
import { api } from '../../services/api';

const ViewEmployeesWithFatigue = () => {
  const [employees, setEmployees] = useState([]);

  const fetchAllWithFatigue = async () => {
    try {
      const empRes = await api.get('/admin/employees');
      const results = await Promise.all(
        empRes.data.map(async (e) => {
          try {
            const scoreRes = await api.get(`/fatigue/employee/${e.username}`);
            return { ...e, fatigueScore: scoreRes.data.fatigueScore };
          } catch {
            return { ...e, fatigueScore: 'Error' };
          }
        })
      );
      setEmployees(results);
    } catch (err) {
      console.error('Failed to load employee data', err);
    }
  };

  useEffect(() => {
    fetchAllWithFatigue();
  }, []);

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Employees with Fatigue Scores</h2>

      {employees.length === 0 ? (
        <p>No employees found.</p>
      ) : (
        <table className="w-full bg-white shadow rounded overflow-hidden">
          <thead className="bg-gray-200">
            <tr>
              <th className="p-2">Name</th>
              <th className="p-2">Username</th>
              <th className="p-2">Department</th>
              <th className="p-2">Fatigue Score</th>
            </tr>
          </thead>
          <tbody>
            {employees.map((e) => (
              <tr key={e.id} className="border-t">
                <td className="p-2">{e.name}</td>
                <td className="p-2">{e.username}</td>
                <td className="p-2">{e.department}</td>
                <td className="p-2 font-semibold">
                  {e.fatigueScore === 'Error' ? (
                    <span className="text-red-500">Failed</span>
                  ) : (
                    e.fatigueScore
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ViewEmployeesWithFatigue;
