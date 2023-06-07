import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import CalendarEvents from "./components/CalendarEvents";
import Constructions from "./components/Constructions";
import Dashboard from "./components/Dashboard";
import ProfileBox from "./components/ProfileBox";
import EmployeesBox from "./components/EmployeesBox";
import Dashboard1 from "./components/Dashboard1";
import Dashboard2 from "./components/Dashboard2";
import EmployeesBox2 from "./components/EmployeesBox2";
import EmployeesBox3 from "./components/EmployeesBox3";
import LogIn from "./components/LogIn";
import EmployeesBox1 from "./components/EmployeesBox1";
import Register from "./components/Register";
import AddCon from "./components/AddCon";
import AssignCon from "./components/AssignCon";
import AddEmployee from "./components/AddEmployee";
import DashboardAdmin from "./components/DashboardAdmin";
import DashboardManager from "./components/DashboardManager";
import EmployeesBoxAdmin from "./components/EmployeesBoxAdmin";
import ConstructionsAdmin from "./components/ConstructionsAdmin";
import EmployeesBoxManager from "./components/EmployeesBoxManager";
import ConstructionsManager from "./components/ConstructionsManager";
import AddEmployeeAdmin from "./components/AddEmployeeAdmin";
import MessagesApp from "./components/MessagesApp";

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route exact path="/"  element={<LogIn/>}>
                    </Route>
                    <Route exact path="/register"  element={<Register/>}>
                    </Route>
                    <Route exact path="/dashboard"  element={<Dashboard/>}>
                    </Route>
                    <Route exact path="/calendar" element={<CalendarEvents/>}>
                    </Route>
                    <Route exact path="/profile" element={<ProfileBox/>}>
                    </Route>
                    <Route exact path="/employees" element={<EmployeesBox/>}>
                    </Route>
                    <Route exact path="/employees1" element={<EmployeesBox1/>}>
                    </Route>
                    <Route exact path="/constructions" element={<Constructions/>}>
                    </Route>
                    <Route exact path="/employees3" element={<EmployeesBox3/>}>
                    </Route>
                    <Route exact path="/employees2" element={<EmployeesBox2/>}>
                    </Route>
                    <Route exact path="/dashboard2" element={<Dashboard1/>}>
                    </Route>
                    <Route exact path="/dashboard3" element={<Dashboard2/>}>
                    </Route>
                    <Route exact path="/assignCon" element={<AssignCon/>}>
                    </Route>
                    <Route exact path="/addCon" element={<AddCon/>}>
                    </Route>
                    <Route exact path="/employeesAdmin" element={<EmployeesBoxAdmin/>}>
                    </Route>
                    <Route exact path="/dashboardAdmin" element={<DashboardAdmin/>}>
                    </Route>
                    <Route exact path="/constructionsAdmin" element={<ConstructionsAdmin/>}>
                    </Route>
                    <Route exact path="/employeesManager" element={<EmployeesBoxManager/>}>
                    </Route>
                    <Route exact path="/dashboardManager" element={<DashboardManager/>}>
                    </Route>
                    <Route exact path="/constructionsManager" element={<ConstructionsManager/>}>
                    </Route>
                    <Route exact path="/addEmployeeAdmin" element={<AddEmployeeAdmin/>}>
                    </Route>
                    <Route exact path="/addEmployee" element={<AddEmployee/>}>
                    </Route>
                    <Route exact path="/chat" element={<MessagesApp/>}>
                    </Route>
                </Routes>
            </div>
        </Router>
    );
}

export default App;
