import {Avatar, Badge} from "@mui/material";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import {faSquarePlus} from "@fortawesome/free-regular-svg-icons";
import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';
import {NavLink} from "react-router-dom";
import Navbar from "./Navbar";
import {useEffect, useState} from "react";
import Axios from "axios";

const EmployeesBox1 = () => {

    function changeAvatar(name) {
        JSON.stringify(name)
        if (name.localeCompare('Henryk')===0){
            return <Avatar src={require('../images/avatar1.jpg')} sx={{width: 70, height: 70}}/>;
        }else if(name.localeCompare('Kinga')===0){
            return <Avatar src={require('../images/avatar2.jpg')} sx={{width: 70, height: 70}}/>;
        }else{
            return <Avatar src={require('../images/avatar9.jpg')} sx={{width: 70, height: 70}}/>;
        }
    }


    const [managers, setManagers] = useState("")

    const [users, setUsers] = useState("")

    const token = localStorage.getItem('user')


    useEffect(() => {
        Axios.post("http://localhost:8080/teamUsers", {
            teamName: 'Manager'
        }, {
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((res) => {
            setManagers(res.data)
        }, fail => {
            console.error(fail);
            alert("Some error has occurred, please try again.")
        })
    }, [token]);

    useEffect(() => {
        Axios.post("http://localhost:8080/allUsers", {
            filterString: '7,11'
        }, {
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((res) => {
            setUsers(res.data)
        }, fail => {
            console.error(fail);
            alert("Some error has occurred, please try again.")
        })
    }, [token]);

    return (
        <div className="employeesPage">
            <Navbar/>
            <nav className="employees">
                <div className="dashboardUp">
                    {managers &&
                        managers.length > 0 &&
                        managers.map(({_id, name, surname, email, password, telephone, job, team}) => {
                            return (
                                <div className="avatarBackground">
                                    <div className="avatar">
                                        <div className="info">
                                            {changeAvatar(name)}
                                            <div className="name">{name} {surname}</div>
                                            <div className="function">{job}</div>
                                        </div>
                                    </div>
                                </div>
                            );
                        })}
                </div>
                <div className="arrowRight">
                    <NavLink activeClassName="active" to="/employees"><ArrowBackIosNewIcon
                        style={{fill: "white", width: "5vw", height: "5vh"}}/></NavLink>
                    <div className="employeesBox">
                        <div className="employeesTitle">
                            Employees
                        </div>
                        <div className="employeesFlex">
                            {users &&
                                users.length > 0 &&
                                users.map(({_id, name, surname, email, password, telephone, job, team}) => {
                                    return (
                                        <div className="post-card" key={_id}>
                                            <div className="employeesNameColumn">
                                                <div className="employeesTitleName">Name</div>
                                                <p className="post-body">{name}</p>
                                            </div>
                                            <div className="employeesStatusColumn">
                                                <div className="employeesTitleStatus">Surname</div>
                                                <p className="post-body">{surname}</p>
                                            </div>
                                            <div className="employeesEmailColumn">
                                                <div className="employeesTitleEmail">Email Address</div>
                                                <p className="post-title">{email}</p>
                                            </div>
                                            <div className="employeesNumberColumn">
                                                <div className="employeesTitleNumber">Contact Number</div>
                                                <p className="post-body">{telephone}</p>
                                            </div>
                                            <div className="employeesLocationColumn">
                                                <div className="employeesTitleLocation">Job</div>
                                                <p className="post-body">{job}</p>
                                            </div>
                                        </div>
                                    );
                                })}
                        </div>
                        <div className="button2">
                            <button><FontAwesomeIcon icon={faSquarePlus}/> Add Employee</button>
                        </div>
                    </div>
                    <NavLink activeClassName="active" to="/employees2"><ArrowForwardIosIcon
                        style={{fill: "white", width: "5vw", height: "5vh"}}/></NavLink>
                </div>
            </nav>
        </div>
    )
}

export default EmployeesBox1;