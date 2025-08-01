import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { useEffect, useState } from 'react';
import { api } from '../../services/api';
import './EmployeeDashboard.css'; // Assuming you have some CSS for styling
const localizer = momentLocalizer(moment);

function EmployeeDashboard() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user'));
  const [events, setEvents] = useState([]);

  useEffect(() => {
    const fetchShifts = async () => {
      try {
        const res = await api.get(`/employee/assigned-shifts/${user.id}`);
        const shiftEvents = res.data.map((shift) => ({
          title: 'Scheduled Shift',
          start: new Date(shift.date + 'T' + shift.startTime),
          end: new Date(shift.date + 'T' + shift.endTime),
          allDay: false,
        }));
        setEvents(shiftEvents);
      } catch (err) {
        console.error('Failed to load calendar data:', err);
      }
    };

    fetchShifts();
  }, [user.id]);

  return (
   <div className="employee-dashboard">
  {/* Sidebar */}
  <div className="employee-sidebar">
    <h3>Employee Menu</h3>
    <ul>
      <li><button onClick={() => navigate('/employee/training')}>View Training</button></li>
      <li><button onClick={() => navigate('/employee/submit-availability')}>Submit Availability</button></li>
      <li><button onClick={() => navigate('/employee/request-timeoff')}>Request Time Off</button></li>
      <li><button onClick={() => navigate('/employee/submit-worklog')}>Submit Worklog</button></li>
      <li><button onClick={() => navigate('/employee/view-worklogs')}>View Worklogs</button></li>
      <li><button onClick={() => navigate('/employee/FatigueScore')}>View Fatigue Score</button></li>
      <li><button onClick={() => navigate('/employee/employee-wellbeing')}>View EmployeeWellbeing</button></li>
    </ul>
  </div>

  {/* Calendar */}
  <div className="employee-calendar">
    <h2>My Shift Calendar</h2>
    <Calendar
      localizer={localizer}
      events={events}
      startAccessor="start"
      endAccessor="end"
      style={{ height: 600 }}
    />
  </div>
</div>
  );
}

export default EmployeeDashboard;
