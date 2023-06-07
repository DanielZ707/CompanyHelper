import logo from "../images/logo.png";
import {NavLink, useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import EmailIcon from '@mui/icons-material/Email';
import {
    faPersonDigging,
    faHouse,
    faCalendarDays,
    faRightFromBracket,
    faPeopleGroup
} from "@fortawesome/free-solid-svg-icons";
import {faIdCard} from "@fortawesome/free-regular-svg-icons";
import SingOutButton from "./SingOutButton";
import {useEffect, useState} from "react";
import Axios from "axios";

const Navbar = () => {

    const [user, setUser] = useState("")

    const token = localStorage.getItem('user');

    const email = localStorage.getItem('email');
    const navigate = useNavigate();


    useEffect(() => {
        Axios.post("http://localhost:8080/oneUser", {
            email: email
        }, {
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((res) => {
            setUser(res.data)
            console.log(res.data)
        }, fail => {
            if(fail.message==="Request failed with status code 403"){
                alert("You have no permission to access the data!")
                navigate('/')
            }
            console.error(fail);
            alert("Some error has occurred, please try again.")
        })


    }, [email, navigate, token]);

    function changeLinks(job) {
        if (job !== undefined) {
            if (job.localeCompare('MANAGER') === 0) {
                return (<div className="links">
                    <div className="link"><FontAwesomeIcon icon={faHouse}/>
                        <NavLink activeClassName="active" to="/dashboard">Dashboard</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faPersonDigging}/>
                        <NavLink activeClassName="active" to="/constructionsManager">Constructions</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faPeopleGroup}/>
                        <NavLink activeClassName="active" to="/employeesManager">Employees</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faCalendarDays}/>
                        <NavLink activeClassName="active" to="/calendar">Calendar</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faIdCard}/>
                        <NavLink activeClassName="active" to="/profile">Profile</NavLink></div>
                    <div className="notifications">
                        <NavLink activeClassName="active" to="/chat"><EmailIcon/></NavLink>
                    </div>
                    <div className="link-logout"><FontAwesomeIcon icon={faRightFromBracket}/>
                        <SingOutButton/></div>
                </div>);
            } else if (job.localeCompare('ADMIN') === 0) {
                return (<div className="links">
                    <div className="link"><FontAwesomeIcon icon={faHouse}/>
                        <NavLink activeClassName="active" to="/dashboard">Dashboard</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faPersonDigging}/>
                        <NavLink activeClassName="active" to="/constructionsAdmin">Constructions</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faPeopleGroup}/>
                        <NavLink activeClassName="active" to="/employeesAdmin">Employees</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faCalendarDays}/>
                        <NavLink activeClassName="active" to="/calendar">Calendar</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faIdCard}/>
                        <NavLink activeClassName="active" to="/profile">Profile</NavLink></div>
                    <div className="notifications">
                        <NavLink activeClassName="active" to="/chat"><EmailIcon/></NavLink>

                    </div>
                    <div className="link-logout"><FontAwesomeIcon icon={faRightFromBracket}/>
                        <SingOutButton/></div>
                </div>);
            } else {
                return (<div className="links">
                    <div className="link"><FontAwesomeIcon icon={faHouse}/>
                        <NavLink activeClassName="active" to="/dashboard">Dashboard</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faPersonDigging}/>
                        <NavLink activeClassName="active" to="/constructions">Constructions</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faPeopleGroup}/>
                        <NavLink activeClassName="active" to="/employees">Employees</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faCalendarDays}/>
                        <NavLink activeClassName="active" to="/calendar">Calendar</NavLink></div>
                    <div className="link"><FontAwesomeIcon icon={faIdCard}/>
                        <NavLink activeClassName="active" to="/profile">Profile</NavLink></div>
                    <div className="notifications">
                        <NavLink activeClassName="active" to="/chat"><EmailIcon/></NavLink>
                    </div>
                    <div className="link-logout"><FontAwesomeIcon icon={faRightFromBracket}/>
                        <SingOutButton/></div>
                </div>);
            }
        }
    }

    return (
        <nav className="navbar">
            <div className="logo"><img src={logo}/></div>
            {changeLinks(user.role)}
        </nav>
    );
}

export default Navbar;