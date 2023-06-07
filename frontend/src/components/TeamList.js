import {VictoryLabel, VictoryLegend, VictoryPie} from "victory";
import { useEffect, useState } from "react";
import Axios from "axios";
import {useNavigate} from "react-router-dom";


const TeamList = () => {
    const [users,setUsers] = useState("")

    const [constructions,setConstructions] = useState("")

    const token = localStorage.getItem('user');

    const navigate = useNavigate();

    useEffect(()=>{
        Axios.post("http://localhost:8080/teamUsers",{
            teamName: 'Alfa'
        }, {headers: { Authorization: 'Bearer ' + token
            }}).then((res)=>{
            setUsers(res.data)
        }, fail => {
            if(fail.message=="Request failed with status code 403"){
                alert("You have no permission to access the data!")
                navigate('/')
            }
            console.error(fail);
            alert("Some error has occurred, please try again.")
        })
    },[token]);

    useEffect(()=>{
        Axios.post("http://localhost:8080/oneConstruction",{
            name: 'Budowa Bloku Mieszkalnego'
        }, {headers: { Authorization: 'Bearer ' + token
            }}).then((res)=>{
            setConstructions(res.data)
        }, fail => {
            if(fail.message=="Request failed with status code 403"){
                alert("You have no permission to access the data!")
                navigate('/')
            }
            console.error(fail);
            alert("Some error has occurred, please try again.")
        })
    },[token]);


    return (
        <nav className="construction">
            <div className="teamList">
                <div className="teamListUp">
                    <div className="teamListTitle">
                        Construction - {constructions.town}, {constructions.street} {constructions.buildingNumber}
                    </div>
                    <div className="team">
                        Team
                    </div>
                </div>
                <div className="employeesFlex">
                    {users &&
                        users.length > 0 &&
                        users.map(({ _id, name, surname, email,password,telephone,job,team}) => {
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
            </div>
            <div className="progressAndDeadline">
                <div className="progressBar">
                    <svg viewBox="0 0 200 150">
                        <VictoryLegend standalone={false}
                                       colorScale={["white", "darkviolet"]}
                                       x={36} y={97}
                                       gutter={38}
                                       orientation="horizontal"
                                       style={{labels: {fontSize: 10, fill: "white"}, border: {stroke: "black"}}}
                                       data={[
                                           {name: "To do"}, {name: "Done"}
                                       ]}
                        />
                        <VictoryPie
                            standalone={false}
                            width={200} height={120}
                            colorScale={["white", "darkviolet"]}
                            data={[
                                {x: "", y: 100-constructions.progress},
                                {x: "", y: constructions.progress}
                            ]}
                            innerRadius={26} labelRadius={100}
                            radius={36}
                            style={{labels: {fontSize: 15, fill: "black"}}}

                        />
                        <VictoryLabel
                            textAnchor="middle" verticalAnchor="middle"
                            x={100} y={60}
                            style={{fontSize: 11, fill: "white"}}
                            text={constructions.progress+"%"}
                        />
                        <VictoryLabel
                            textAnchor="middle" verticalAnchor="middle"
                            x={100} y={7}
                            style={{fontSize: 12, fill: "white"}}
                            text="Progress"
                        />
                    </svg>
                </div>
                <div className="deadline">
                    <div className="deadlineTitle">
                        Deadline
                    </div>
                    <div className="deadlineDate">
                        {constructions.deadlineDay}
                    </div>
                </div>
            </div>
        </nav>
    );
}

export default TeamList;