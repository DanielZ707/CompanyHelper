import React, {useEffect} from "react";
import {useStateWithCallback} from "./useStateWithCallback";
import Axios from "axios";
import {useNavigate} from "react-router-dom";

const TeamDelta = () => {
    const navigate = useNavigate();

    const [users, setUsers] = useStateWithCallback("");
    const setData = (usersData) => {
        setUsers(usersData, (prevValue, newValue) => {
            console.log(newValue);
        });
    };

    const token = localStorage.getItem('user');

    useEffect(() => {

        Axios.post("http://localhost:8080/teamUsers", {
            teamName: 'Delta'
        }, {
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((res) => {
            setData(res.data)
        }, fail => {
            if(fail.message=="Request failed with status code 403"){
                alert("You have no permission to access the data!")
                navigate('/')
            }
            console.error(fail);
            alert("Some error has occurred, please try again.")
        })
    },[setData, token]);

    return (
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
    )
}
export default TeamDelta;