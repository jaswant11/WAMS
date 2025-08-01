import React from 'react';
import { useNavigate } from 'react-router-dom';

const ManagerDashboard = () => {
  const user = JSON.parse(localStorage.getItem('user'));
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem('user');
    navigate('/');
  };

  return (
    <div className="flex min-h-screen bg-gray-100">
      {/* Sidebar */}
      <aside className="w-64 bg-white shadow p-6">
        <h2 className="text-xl font-bold mb-6">Manager Panel</h2>
        <nav className="space-y-3">
          <button
            onClick={() => navigate('/manager/create-template')}
            className="block w-full text-left px-4 py-2 bg-gray-200 rounded hover:bg-gray-300"
          >
            Create Shift Template
          </button>
          <button
            onClick={() => navigate('/manager/assign-shift')}
            className="block w-full text-left px-4 py-2 bg-gray-200 rounded hover:bg-gray-300"
          >
            Assign Employees to Shift
          </button>
          <button
            onClick={() => navigate('/manager/approve-timeoff')}
            className="block w-full text-left px-4 py-2 bg-gray-200 rounded hover:bg-gray-300"
          >
            <button
  onClick={() => navigate('/manager/view-employees')}
  className="block w-full text-left px-4 py-2 bg-gray-200 rounded hover:bg-gray-300"
>
  View Employees + Fatigue
</button>
            Approve Time-Off
          </button>
          <button
            onClick={() => navigate('/manager/view-availability')}
            className="block w-full text-left px-4 py-2 bg-gray-200 rounded hover:bg-gray-300"
          >
            View Availabilities
          </button>
          <button
            onClick={logout}
            className="block w-full text-left px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600 mt-6"
          >
            <button
  onClick={() => navigate('/manager/view-worklogs')}
  className="block w-full text-left px-4 py-2 bg-gray-200 rounded hover:bg-gray-300"
>
  View Worklogs
</button>
            Logout
          </button>
        </nav>
      </aside>

      {/* Main content */}
      <main className="flex-1 p-8">
        <h1 className="text-3xl font-bold mb-4">Welcome, {user?.username} 👋</h1>
        <p className="text-lg text-gray-600">
          Use the sidebar to manage shift templates, assign employees, and approve time-off requests.
        </p>
      </main>
    </div>
  );
};

export default ManagerDashboard;
