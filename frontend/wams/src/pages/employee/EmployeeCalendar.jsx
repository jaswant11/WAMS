import React, { useEffect, useState } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { api } from '../../services/api';

const localizer = momentLocalizer(moment);

function EmployeeCalendar() {
  const [events, setEvents] = useState([]);
  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    const fetchCalendarData = async () => {
      try {
        const [shiftsRes, timeOffRes] = await Promise.all([
          api.get(`/employee/assigned-shifts/${user.id}`),
          api.get(`/employee/timeoff/${user.id}`)
        ]);

        const shiftEvents = shiftsRes.data.map(shift => ({
          title: 'Scheduled Shift',
          start: new Date(`${shift.date}T${shift.startTime}`),
          end: new Date(`${shift.date}T${shift.endTime}`),
          allDay: false
        }));

        const timeOffEvents = timeOffRes.data.map(off => ({
          title: `Time Off: ${off.reason} (${off.status})`,
          start: new Date(off.startDate),
          end: new Date(moment(off.endDate).add(1, 'days').format('YYYY-MM-DD')),
          allDay: true
        }));

        // Filter shifts that overlap with APPROVED time-off
        const approvedTimeOffDates = new Set();
        timeOffRes.data
          .filter(off => off.status === 'APPROVED')
          .forEach(off => {
            let current = moment(off.startDate);
            const end = moment(off.endDate);
            while (current <= end) {
              approvedTimeOffDates.add(current.format('YYYY-MM-DD'));
              current = current.add(1, 'days');
            }
          });

        const filteredShifts = shiftEvents.filter(ev =>
          !approvedTimeOffDates.has(moment(ev.start).format('YYYY-MM-DD'))
        );

        setEvents([...filteredShifts, ...timeOffEvents]);

      } catch (err) {
        console.error('Failed to load calendar data', err);
      }
    };

    fetchCalendarData();
  }, [user.id]);

  return (
    <div style={{ padding: '20px' }}>
      <h2>My Calendar</h2>
      <Calendar
        localizer={localizer}
        events={events}
        startAccessor="start"
        endAccessor="end"
        style={{ height: 600 }}
      />
    </div>
  );
}

export default EmployeeCalendar;
