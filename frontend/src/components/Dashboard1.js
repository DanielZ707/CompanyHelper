import TeamList from "./TeamList";
import {NavLink} from "react-router-dom";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import Navbar from "./Navbar";
import {useEffect, useState} from "react";
import Axios from "axios";

const Dashboard = () => {

    const [users,setUsers] = useState("")

    const [constructions,setConstructions] = useState("")

    const token = localStorage.getItem('user');

    useEffect(() => {
        Axios.post("http://localhost:8080/allUsers", {
        }, {
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((res) => {
            setUsers(res.data)
        })
    }, [token]);

    useEffect(()=>{
        Axios.get("http://localhost:8080/allConstructions",{headers: { Authorization: 'Bearer ' + token
            }}).then((res)=>{
            setConstructions(res.data)
        })
    },[token]);

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
                        <div className="title">Your Card
                        </div>
                        <div className="text">189 768 $</div>
                    </div>
                </div>
                <div className="dashboardNavi-double">
                    <NavLink activeClassName="active" to="/dashboard"><ArrowBackIosNewIcon
                        style={{fill: "white", width: "2vw", height: "8vh"}}/></NavLink>
                    <TeamList/>
                    <NavLink activeClassName="active" to="/dashboard3"><ArrowForwardIosIcon
                        style={{fill: "white", width: "2vw", height: "8vh"}}/></NavLink>
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