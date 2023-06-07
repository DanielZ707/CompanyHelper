import {Avatar} from "@mui/material";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-regular-svg-icons";
import Navbar from "./Navbar";
import React, {useEffect, useState} from "react";
import Axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import Popup from "reactjs-popup";

const EmployeesBoxManager = () => {
    const navigate = useNavigate();

    async function addEmp(event) {
        event.preventDefault();
        navigate('/addEmployee')

    }

    function refreshPage() {
        window.location.reload(false);
    }



    function save(emailUser, teamName) {
        try {
            axios.post("http://localhost:8080/changeTeam", {
                email: emailUser,
                teamName: teamName

            }, {
                headers: {
                    Authorization: 'Bearer ' + token
                }
            }).then((res) => {
                console.log(res.data);
                refreshPage()
            }, fail => {
                if(fail.message=="Request failed with status code 403"){
                    alert("You have no permission to access the data!")
                    navigate('/')
                }
                console.error(fail);
                alert("Some error has occurred, please try again.")
            });
        } catch (err) {
            alert(err);
        }

    }

    function save2(emailUser, roleName) {
        try {
            axios.post("http://localhost:8080/changeRole", {
                email: emailUser,
                role: roleName

            }, {
                headers: {
                    Authorization: 'Bearer ' + token
                }
            }).then((res) => {
                console.log(res.data);
                refreshPage()
            }, fail => {
                alert(fail.message); // Error!
            });
        } catch (err) {
            alert(err);
        }

    }

    function changeAvatar(name) {
        JSON.stringify(name)
        if (name.localeCompare('Henryk') === 0) {
            return <Avatar src={require('../images/avatar1.jpg')} sx={{width: 70, height: 70}}/>;
        } else if (name.localeCompare('Kinga') === 0) {
            return <Avatar src={require('../images/avatar2.jpg')} sx={{width: 70, height: 70}}/>;
        } else if(name.localeCompare('Katarzyna') === 0){
            return <Avatar src={require('../images/avatar9.jpg')} sx={{width: 70, height: 70}}/>;
        }else{
            return null;
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
        })
    }, [token]);

    useEffect(() => {
        Axios.post("http://localhost:8080/allUsers", {}, {
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((res) => {
            setUsers(res.data)
        }, fail => {
            alert("You have no permission to access the data!")
            navigate("/")
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
                    <div className="employeesBox">
                        <div className="employeesTitle">
                            Employees
                        </div>
                        <div className="employeesFlex">
                            {users &&
                                users.length > 0 &&
                                users.map(({idUser, name, surname, email, password, telephone, job, team,role}) => {
                                    return (<div className="usersWithButtons">
                                            <div className="post-card" key={idUser}>
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
                                                <div className="employeesLocationColumn">
                                                    <div className="employeesTitleLocation">Team</div>
                                                    <p className="post-body">{team.name}</p>
                                                </div>
                                            </div>
                                            <div className="popupAdmin">
                                                <Popup trigger={<button type="submit">Change Team</button>} modal
                                                       nested>
                                                    {close => (
                                                        <div className="modal-team">
                                                            <div className="actions-team">
                                                                <div className="setTeam-admin">
                                                                    <form>
                                                                        <div className="login-header-2">
                                                                            Select a new team for an Employee!
                                                                        </div>
                                                                        <div className="login-header-2">
                                                                            Current team : {team.name}
                                                                        </div>
                                                                        <div className="login-email">
                                                                            <div className="setTeam-admin-field">
                                                                                <button type="button"
                                                                                        onClick={() => save(email, 'Alfa')}>Alfa
                                                                                </button>
                                                                                <button type="button"
                                                                                        onClick={() => save(email, 'Beta')}>Beta
                                                                                </button>
                                                                            </div>
                                                                            <div className="setTeam-admin-field">
                                                                                <button type="button"
                                                                                        onClick={() => save(email, 'Gamma')}>Gamma
                                                                                </button>
                                                                                <button type="button"
                                                                                        onClick={() => save(email, 'Delta')}>Delta
                                                                                </button>
                                                                            </div>
                                                                            <button type="button"
                                                                                    onClick={() => save(email, 'Omega')}>Omega
                                                                            </button>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    )}
                                                </Popup>
                                                <Popup trigger={<button type="submit">Change Role</button>} modal
                                                       nested>
                                                    {close => (
                                                        <div className="modal-team-role">
                                                            <div className="actions-team">
                                                                <div className="setTeam-admin-role">
                                                                    <form>
                                                                        <div className="login-header-2">
                                                                            Select a new role for an Employee!
                                                                        </div>
                                                                        <div className="login-header-2">
                                                                            Current role : {role}
                                                                        </div>
                                                                        <div className="login-email">
                                                                            <div className="setTeam-admin-field">
                                                                                <button type="button"
                                                                                        onClick={() => save2(email, 'Admin')}>Admin
                                                                                </button>
                                                                                <button type="button"
                                                                                        onClick={() => save2(email, 'Manager')}>Manager
                                                                                </button>
                                                                                <button type="button"
                                                                                        onClick={() => save2(email, 'Worker')}>Worker
                                                                                </button>
                                                                            </div>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    )}
                                                </Popup>
                                            </div>
                                        </div>
                                    );
                                })}
                        </div>
                        <div className="button2">
                            <button onClick={addEmp}><FontAwesomeIcon icon={faSquarePlus}/> Add Employee</button>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    )
}

export default EmployeesBoxManager;