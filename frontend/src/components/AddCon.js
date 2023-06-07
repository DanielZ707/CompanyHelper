import React, {useState} from "react"
import axios from "axios"
import logo from "../images/logo.png";
import {Link, useNavigate} from "react-router-dom";

const AddCon = () => {

    const [name, setName] = useState("");
    const [town, setTown] = useState("");
    const [street, setStreet] = useState("");
    const [buildingNumber, setBuildingNumber] = useState(0);
    const [progress, setProgress] = useState(0);
    const [dateOfBegging, setDateOfBegging] = useState("");
    const [deadlineDay, setDeadlineDay] = useState("");
    const token = localStorage.getItem('user');
    const navigate = useNavigate();
    async function save(event) {
        event.preventDefault();
        try {
            await axios.post("http://localhost:8080/addConstruction", {
                name: name,
                town: town,
                street: street,
                buildingNumber: buildingNumber,
                progress: progress,
                dateOfBegging:dateOfBegging,
                deadlineDay: deadlineDay
            },{
                headers: {
                    Authorization: 'Bearer ' + token
                }
            }).then((res) =>
            {
                console.log(res.data);

                if (res.data.message == "We are sorry but you are not one of our employees")
                {
                    alert("We are sorry but you are not one of our employees");
                }
                else if(res.data.message == "Passwords do not match")
                {
                    alert("Passwords do not match");
                }
                else if(res.data.message == "This email is used by existing account")
                {
                    alert("This email is used by existing account");
                }
                else
                {
                    alert("Construction has been added successfully!");
                    localStorage.setItem('currentBuilding',name)
                    navigate('/assignCon');
                }
            }, fail => {
                if(fail.message=="Request failed with status code 403"){
                    alert("You have no permission to access the data!")
                    navigate('/')
                }
                console.error(fail);
                alert("Some error has occurred, please try again.")
            });
        }
        catch (err) {
            alert(err);
        }

    }

    return (
        <div className="registerPage-start">
            <div className="logo"><img src={logo}/></div>
            <div className="register-start">
                <form>
                    <div className="login-header">
                        Add new construction!
                    </div>
                    <div className="login-email">
                        <div className="email-header">
                            Name
                        </div>
                        <input type={"text"} value={name}
                               onChange={(event) => {
                                   setName(event.target.value);
                               }}/>
                        <div className="email-header">
                            Town
                        </div>
                        <input type={"text"} value={town}
                               onChange={(event) => {
                                   setTown(event.target.value);
                               }}/>
                        <div className="email-header">
                            Street
                        </div>
                        <input type={"text"} value={street}
                               onChange={(event) => {
                                   setStreet(event.target.value);
                               }}/>
                        <div className="email-header">
                            Building Number
                        </div>
                        <input type={"number"} value={buildingNumber}
                               onChange={(event) => {
                                   setBuildingNumber(event.target.value);
                               }}/>
                        <div className="email-header">
                            Progress
                        </div>
                        <input type={"number"} value={progress}
                               onChange={(event) => {
                                   setProgress(event.target.value);
                               }}/>
                        <div className="email-header">
                            Date of Begging
                        </div>
                        <input type={"date"} value={dateOfBegging}
                               onChange={(event) => {
                                   setDateOfBegging(event.target.value);
                               }}/>
                        <div className="email-header">
                            Deadline Day
                        </div>
                        <input type={"date"} value={deadlineDay}
                               onChange={(event) => {
                                   setDeadlineDay(event.target.value);
                               }}/>
                    </div>
                        <button type ="submit" onClick={save}>Submit</button>
                </form>
            </div>
        </div>
    )
}
export default AddCon;