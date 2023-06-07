import Calendar from "react-calendar";
import React, { useState } from 'react';
import 'react-calendar/dist/Calendar.css';
import Navbar from "./Navbar";
const CalendarPage = () => {
        const [value, onChange] = useState(new Date());
        return (
            <div className="calendar">
                <Calendar onChange={onChange} value={value}/>
            </div>
        );
}

export default CalendarPage;