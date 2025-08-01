import React, { useState, useEffect } from 'react';
import { api } from '../../services/api';
import './ManagerDashboard.css'; // Ensure this CSS file is linked

const AdminPanel = () => {
  useEffect(() => { document.title = 'Manager Dashboard'; }, []);

  const [users, setUsers] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [managers, setManagers] = useState([]);
  const [form, setForm] = useState({ username: '', password: '', role: 'EMPLOYEE' });
  const [editUserId, setEditUserId] = useState(null);
  const [editData, setEditData] = useState({});
  const [menuOpen, setMenuOpen] = useState(null);
  const [fatigueScores, setFatigueScores] = useState({});
  const [shiftForm, setShiftForm] = useState({ name: '', startTime: '', endTime: '', daysOfWeek: [], startDate: '', endDate: '' });
  const [assignForm, setAssignForm] = useState({ templateId: '', employeeIds: '', startDate: '', endDate: '' });
  const [recommendations, setRecommendations] = useState([]);
  const [sortKey, setSortKey] = useState('fatigueScore');
  const [worklogForm, setWorklogForm] = useState({ employeeId: '', date: '', startTime: '', endTime: '', description: '' });
  const [worklogResponse, setWorklogResponse] = useState(null);
  const [activeSection, setActiveSection] = useState('userManagement'); // State to manage active section

  const fetchAll = async () => {
    try {
      const [usersRes, empRes, mgrRes] = await Promise.all([
        api.get('/admin/all-users'),
        api.get('/admin/employees'),
        api.get('/admin/managers'),
      ]);
      setUsers(usersRes.data);
      setEmployees(empRes.data);
      setManagers(mgrRes.data);
    } catch (error) {
      console.error("Failed to fetch all data:", error);
      // Handle error, e.g., show a message to the user
    }
  };

  useEffect(() => { fetchAll(); }, []);

  const getDetails = (u) => {
    if (u.role === 'EMPLOYEE') return employees.find(e => e.id === u.id) || {};
    if (u.role === 'MANAGER') return managers.find(m => m.id === u.id) || {};
    return {};
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    try {
      await api.post('/admin/create', form);
      setForm({ username: '', password: '', role: 'EMPLOYEE' });
      fetchAll();
      alert('User created successfully!');
    } catch (error) {
      console.error('Failed to create user:', error);
      alert('Error creating user.');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      try {
        await api.delete(`/admin/delete/${id}`);
        fetchAll();
        alert('User deleted successfully!');
      } catch (error) {
        console.error('Failed to delete user:', error);
        alert('Error deleting user.');
      }
    }
  };

  const handleEdit = (user) => {
    const d = getDetails(user);
    setEditUserId(user.id);
    setEditData({ name: d?.name || '', department: d?.department || '', available: d?.available ?? true, role: user.role });
    setMenuOpen(null);
  };

  const handleUpdate = async () => {
    try {
      if (editData.role === 'EMPLOYEE') {
        await api.put(`/employee/update/${editUserId}`, { name: editData.name, department: editData.department, available: editData.available });
      } else if (editData.role === 'MANAGER') {
        await api.put(`/manager/update/${editUserId}`, { name: editData.name, department: editData.department });
      }
      setEditUserId(null);
      fetchAll();
      alert('User updated successfully!');
    } catch (error) {
      console.error('Failed to update user:', error);
      alert('Error updating user.');
    }
  };

  const handleViewFatigue = async (id) => {
    try {
      const res = await api.get(`/admin/fatigue/${id}`);
      setFatigueScores((prev) => ({ ...prev, [id]: res.data.fatigueScore }));
    } catch (error) {
      console.error('Failed to fetch fatigue score:', error);
      setFatigueScores((prev) => ({ ...prev, [id]: 'Error' }));
      alert('Error fetching fatigue score.');
    }
  };

  const handleCreateTemplate = async () => {
    try {
      await api.post('/manager/shift-template/create', shiftForm);
      alert('Shift template created successfully!');
      setShiftForm({ name: '', startTime: '', endTime: '', daysOfWeek: [], startDate: '', endDate: '' });
    } catch (error) {
      console.error('Failed to create template:', error);
      alert('Error creating template.');
    }
  };

  const handleAssignTemplate = async () => {
    try {
      const employeeIdsArray = assignForm.employeeIds.split(',').map(id => id.trim()).filter(Boolean);
      const params = new URLSearchParams();
      employeeIdsArray.forEach(id => params.append('employeeIds', id));
      params.append('startDate', assignForm.startDate);
      params.append('endDate', assignForm.endDate);
      const url = `/manager/shift-template/assign/${assignForm.templateId}?${params.toString()}`;
      await api.post(url);
      alert('Shifts successfully assigned from template!');
      setAssignForm({ templateId: '', employeeIds: '', startDate: '', endDate: '' });
    } catch (error) {
      console.error('Error assigning shifts:', error);
      alert('Failed to assign shifts.');
    }
  };

  const fetchRecommendations = async () => {
    try {
      const res = await api.get('/fatigue/recommendation');
      setRecommendations(res.data);
      alert('Worker recommendations fetched successfully!');
    } catch (error) {
      console.error('Failed to fetch fatigue recommendations', error);
      alert('Error fetching worker recommendations.');
    }
  };

  const handleWorklogSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/employee/worklog/submit', worklogForm);
      setWorklogResponse('Worklog submitted successfully!');
      setWorklogForm({ employeeId: '', date: '', startTime: '', endTime: '', description: '' });
    } catch (err) {
      setWorklogResponse('Error submitting worklog.');
      console.error(err);
    }
  };

  return (
    <div className="admin-dashboard-container">
      <div className="sidebar">
        <div className="sidebar-header">
          <h2>Navigation</h2>
        </div>
        <ul className="sidebar-menu">
          <li className={activeSection === 'userManagement' ? 'active' : ''} onClick={() => setActiveSection('userManagement')}>
            User Management
          </li>
          <li className={activeSection === 'shiftScheduling' ? 'active' : ''} onClick={() => setActiveSection('shiftScheduling')}>
            Shift Scheduling
          </li>
          <li className={activeSection === 'submitWorklog' ? 'active' : ''} onClick={() => setActiveSection('submitWorklog')}>
            Submit Worklog
          </li>
        </ul>
      </div>

      <div className="main-content">
        <div className="dashboard-header">
          <h1>Dashboard</h1>
        </div>

        {activeSection === 'userManagement' && (
          <div className="dashboard-section">
            <h3>Create New User</h3>
            <form onSubmit={handleAdd} className="dashboard-form">
              <input placeholder="Username" value={form.username} onChange={e => setForm({ ...form, username: e.target.value })} required />
              <input placeholder="Password" type="password" value={form.password} onChange={e => setForm({ ...form, password: e.target.value })} required />
              <select value={form.role} onChange={e => setForm({ ...form, role: e.target.value })}>
                <option value="EMPLOYEE">Employee</option>
                <option value="MANAGER">Manager</option>
                <option value="ADMIN">Admin</option>
              </select>
              <button type="submit" className="btn-primary">Add User</button>
            </form>

            <h3>Manage Users</h3>
            <table className="user-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Username</th>
                  <th>Role</th>
                  <th>Name</th>
                  <th>Department</th>
                  <th>Available</th>
                  <th>Fatigue Score</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {users.map(user => {
                  const details = getDetails(user);
                  const isEditing = editUserId === user.id;
                  return (
                    <tr key={user.id}>
                      <td>{user.id}</td>
                      <td>{user.username}</td>
                      <td>{user.role}</td>
                      <td>
                        {isEditing ? (
                          <input value={editData.name} onChange={e => setEditData({ ...editData, name: e.target.value })} />
                        ) : (
                          details.name || 'N/A'
                        )}
                      </td>
                      <td>
                        {isEditing ? (
                          <input value={editData.department} onChange={e => setEditData({ ...editData, department: e.target.value })} />
                        ) : (
                          details.department || 'N/A'
                        )}
                      </td>
                      <td>
                        {isEditing && user.role === 'EMPLOYEE' ? (
                          <input type="checkbox" checked={editData.available} onChange={e => setEditData({ ...editData, available: e.target.checked })} />
                        ) : (
                          user.role === 'EMPLOYEE' ? (details.available ? 'Yes' : 'No') : 'N/A'
                        )}
                      </td>
                      <td>
                        {fatigueScores[user.id] !== undefined ? (
                          <span className="fatigue-score">{fatigueScores[user.id]}</span>
                        ) : (
                          <button onClick={() => handleViewFatigue(user.id)} className="btn-secondary">View Fatigue</button>
                        )}
                      </td>
                      <td className="user-actions">
                        {isEditing ? (
                          <button onClick={handleUpdate} className="btn-success">Save</button>
                        ) : (
                          <>
                            <button onClick={() => handleEdit(user)} className="btn-edit">Edit</button>
                            <button onClick={() => handleDelete(user.id)} className="btn-delete">Delete</button>
                          </>
                        )}
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        )}

        {activeSection === 'shiftScheduling' && (
          <>
            <div className="dashboard-section">
              <h3>Create Shift Template</h3>
              <form onSubmit={(e) => { e.preventDefault(); handleCreateTemplate(); }} className="dashboard-form">
                <input placeholder="Template Name" value={shiftForm.name} onChange={e => setShiftForm({ ...shiftForm, name: e.target.value })} required />
                <input type="time" placeholder="Start Time" value={shiftForm.startTime} onChange={e => setShiftForm({ ...shiftForm, startTime: e.target.value })} required />
                <input type="time" placeholder="End Time" value={shiftForm.endTime} onChange={e => setShiftForm({ ...shiftForm, endTime: e.target.value })} required />
                <input type="date" placeholder="Start Date" value={shiftForm.startDate} onChange={e => setShiftForm({ ...shiftForm, startDate: e.target.value })} required />
                <input type="date" placeholder="End Date" value={shiftForm.endDate} onChange={e => setShiftForm({ ...shiftForm, endDate: e.target.value })} required />
                <div className="days-of-week-checkboxes">
                  {['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'].map(day => (
                    <label key={day}>
                      <input
                        type="checkbox"
                        value={day}
                        checked={shiftForm.daysOfWeek.includes(day)}
                        onChange={(e) => {
                          const { value, checked } = e.target;
                          setShiftForm(prev => ({
                            ...prev,
                            daysOfWeek: checked
                              ? [...prev.daysOfWeek, value]
                              : prev.daysOfWeek.filter(d => d !== value)
                          }));
                        }}
                      />
                      {day.substring(0, 3)}
                    </label>
                  ))}
                </div>
                <button type="submit" className="btn-primary">Create Template</button>
              </form>
            </div>

            <div className="dashboard-section">
              <h3>Assign Shift Template</h3>
              <form onSubmit={(e) => { e.preventDefault(); handleAssignTemplate(); }} className="dashboard-form">
                <input placeholder="Template ID" value={assignForm.templateId} onChange={e => setAssignForm({ ...assignForm, templateId: e.target.value })} required />
                <input placeholder="Employee IDs (comma-separated)" value={assignForm.employeeIds} onChange={e => setAssignForm({ ...assignForm, employeeIds: e.target.value })} required />
                <input type="date" placeholder="Start Date" value={assignForm.startDate} onChange={e => setAssignForm({ ...assignForm, startDate: e.target.value })} required />
                <input type="date" placeholder="End Date" value={assignForm.endDate} onChange={e => setAssignForm({ ...assignForm, endDate: e.target.value })} required />
                <button type="submit" className="btn-primary">Assign Shifts</button>
                <button type="button" onClick={fetchRecommendations} className="btn-recommendation">
                  Worker Recommendations
                  <span className="recommendation-icon">💡</span> {/* Placeholder for attractive hovering icon */}
                </button>
              </form>
              {recommendations.length > 0 && (
                <div className="recommendations-list">
                  <h4>Recommended Workers (Low Fatigue):</h4>
                  <ul>
                    {recommendations.map(rec => (
                      <li key={rec.employeeId}>
                        Employee ID: {rec.employeeId}, Fatigue Score: {rec.fatigueScore.toFixed(2)}
                      </li>
                    ))}
                  </ul>
                </div>
              )}
            </div>
          </>
        )}

        {activeSection === 'submitWorklog' && (
          <div className="dashboard-section worklog-section">
            <h3>Submit Worklog</h3>
            <form className="dashboard-form" onSubmit={handleWorklogSubmit}>
              <select value={worklogForm.employeeId} onChange={e => setWorklogForm({ ...worklogForm, employeeId: e.target.value })} required>
                <option value="">Select Employee</option>
                {employees.map(emp => (
                  <option key={emp.id} value={emp.id}>{emp.username} ({emp.name}) – ID: {emp.id}</option>
                ))}
              </select>
              <input type="date" value={worklogForm.date} onChange={e => setWorklogForm({ ...worklogForm, date: e.target.value })} required />
              <input type="time" value={worklogForm.startTime} onChange={e => setWorklogForm({ ...worklogForm, startTime: e.target.value })} required />
              <input type="time" value={worklogForm.endTime} onChange={e => setWorklogForm({ ...worklogForm, endTime: e.target.value })} required />
              <input type="text" placeholder="Description" value={worklogForm.description} onChange={e => setWorklogForm({ ...worklogForm, description: e.target.value })} required />
              <button type="submit" className="btn-primary worklog-submit-btn">Submit Worklog</button>
            </form>
            {worklogResponse && <p className="worklog-response">{worklogResponse}</p>}
           
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminPanel;
