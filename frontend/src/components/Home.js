import { Calendar, momentLocalizer } from 'react-big-calendar'
import moment from 'moment'

const localizer = momentLocalizer(moment)

const CalendarEvents = (props) => (
    <div>
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