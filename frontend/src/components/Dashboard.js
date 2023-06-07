import Navbar from "./Navbar";
import React, {useEffect, useState} from "react";
import Axios from "axios";
import Construction from "./Construction";
import {useNavigate} from "react-router-dom";


const Dashboard = () => {

    const [users, setUsers] = useState("")

    const [user, setUser] = useState("")

    const [team, setTeam] = useState("")
    const navigate = useNavigate();

    const [constructions, setConstructions] = useState("")

    const token = localStorage.getItem('user');

    const email = localStorage.getItem('email');



    useEffect(() => {
            Axios.post("http://localhost:8080/allUsers", {}, {
                headers: {
                    Authorization: 'Bearer ' + token
                }
            }).then((res) => {
                setUsers(res.data)
            }, fail => {
            })
            Axios.get("http://localhost:8080/allConstructions", {
                headers: {
                    Authorization: 'Bearer ' + token
                }
            }).then((res) => {
                setConstructions(res.data)
            }, fail => {

            })

        Axios.post("http://localhost:8080/oneUser", {
            email: email
        }, {
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((res) => {
            setUser(res.data)
            setTeam(res.data.team)
            console.log(res.data)
        }, fail => {
            if(fail.message=="Request failed with status code 403"){
                alert("You have no permission to access the data!")
                navigate('/')
            }
            console.error(fail);
            alert("Some error has occurred, please try again.")
        })


    }, [email, token]);

    const current = new Date();
    const date = `${current.toDateString()}`;
    return (
        <div className="dashboardPage">
            <Navbar/>
            <div className="dashboard">
                <div className="dashboardUp">
                    <div className="avatarBackgroundViolet">
                        <div className="title">
                            Total Year Expenses
                        </div>
                        <div className="text">12,713k</div>
                    </div>
                    <div className="avatarBackgroundViolet">
                        <div className="title">Total Year Income
                        </div>
                        <div className="text">18,960k</div>
                    </div>
                    <div className="avatarBackgroundGrey">
                        <div className="avatar">
                            <div className="info">
                                <div className="name">{user.name} {user.surname}</div>
                                <div className="function">{user.job}</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="dashboardNavi">
                    {constructions &&
                        constructions.length > 0 &&
                        constructions.map(({idConstruction, name, town, street, buildingNumber, deadlineDay, progress}) => {
                            return (<Construction nameCon = {name}/>);
                        })}
                </div>
                <div className="dashboardDown">
                    <div className="textDown-1">
                        <div className="titleActive">
                            Today
                        </div>
                        <div className="todayDate">
                            {date}
                        </div>
                    </div>
                    <div className="textDown-2">
                        <div className="consAndWorkers">
                            <div className="activeCons">
                                <div className="titleActiveCons">
                                    Active Constructions
                                </div>
                                <div className="activeConstruction">
                                    {constructions.length}
                                </div>
                            </div>
                            <div className="activeWorkers">
                                <div className="titleActiveWorkers">
                                    Active Workers
                                </div>
                                <div className="activeConstruction">
                                    {users.length}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Dashboard;