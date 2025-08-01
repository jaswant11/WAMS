import React from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';

const localizer = momentLocalizer(moment);

const ShiftCalendar = ({ events, onSelectEvent }) => {
  return (
    <div style={{ height: 600 }}>
      <Calendar
        localizer={localizer}
        events={events}
        startAccessor="start"
        endAccessor="end"
        style={{ backgroundColor: 'white', padding: '1rem' }}
        onSelectEvent={onSelectEvent}
      />
    </div>
  );
};

export default ShiftCalendar;
