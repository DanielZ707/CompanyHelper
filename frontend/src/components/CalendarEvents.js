import { Calendar, momentLocalizer } from 'react-big-calendar'
import moment from 'moment'
import Navbar from "./Navbar";
import React from "react";

const localizer = momentLocalizer(moment)

const CalendarEvents = (props) => (
    <div className="calendarPage">
        <Navbar/>
        <Calendar
            localizer={localizer}
            events={"Party"}
            startAccessor="start"
            endAccessor="end"
            style={{ height: 500 }}
        />
    </div>
)

export default CalendarEvents;