import React, {useState} from "react"
import axios from "axios"
import logo from "../images/logo.png";
import {Link, useNavigate} from "react-router-dom";

const Register = () => {

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirmation, setPasswordConfirmation] = useState("");
    const [telephone, setTelephone] = useState("");
    const [job, setJob] = useState("");
    const [surname, setSurname] = useState("");
    const [team, setTeam] = useState("not assigned");
    const navigate = useNavigate();

    function setData(name) {
        setTeam(name);
    }

    async function save(event) {
        event.preventDefault();
        if (email.localeCompare('kingaManager@gmail.com') !== 0 && email.localeCompare('henrykManager@gmail.com') !== 0 && email.localeCompare('katarzynaManager@gmail.com') !== 0 && email.localeCompare('danielAdmin@gmail.com') !== 0 && team.localeCompare('not assigned') === 0) {
            alert("You are not an admin or a manager!!!" +
                "Please choose the team!")
        }
        else{
            try {
                await axios.post("http://localhost:8080/register", {
                    name: name,
                    surname: surname,
                    email: email,
                    password: password,
                    passwordConfirmation: passwordConfirmation,
                    telephone: telephone,
                    job: job,
                    team: team
                }).then((res) => {
                    console.log(res.data);

                    if (res.data.message == "We are sorry but you are not one of our employees") {
                        alert("We are sorry but you are not one of our employees");
                    } else if (res.data.message == "Passwords do not match") {
                        alert("Passwords do not match");
                    } else if (res.data.message == "This email is used by existing account") {
                        alert("This email is used by existing account");
                    } else {
                        alert("Employee has been registered successfully!");
                        navigate('/');
                    }
                }, fail => {
                    alert(fail.message); // Error!
                });
            } catch (err) {
                alert(err);
            }
        }

    }

    return (
        <div className="registerPage1">
            <div className="backButton">
                <div className="logo"><img src={logo}/></div>
            </div>
            <div className="registerPage">
                <div className="register">
                    <form>
                        <div className="login-header">
                            Register
                        </div>
                        <div className="login-email-register">
                            <div className="registerForm">
                                <div className="registerForm2">
                                    <div className="email-header">
                                        Name
                                    </div>
                                    <input type={"text"} value={name}
                                           onChange={(event) => {
                                               setName(event.target.value);
                                           }}/>
                                </div>
                                <div className="registerForm2">
                                    <div className="email-header">
                                        Surname
                                    </div>
                                    <input type={"text"} value={surname}
                                           onChange={(event) => {
                                               setSurname(event.target.value);
                                           }}/>
                                </div>
                            </div>
                            <div className="registerForm">
                                <div className="registerForm2">
                                    <div className="email-header">
                                        E-mail
                                    </div>
                                    <input type={"email"} value={email}
                                           onChange={(event) => {
                                               setEmail(event.target.value);
                                           }}/>
                                </div>
                                <div className="registerForm2">
                                    <div className="email-header">
                                        Telephone Number
                                    </div>
                                    <input type={"text"}
                                           value={telephone}
                                           onChange={(event) => {
                                               setTelephone(event.target.value);
                                           }}/>
                                </div>
                            </div>
                            <div className="registerForm2">
                                <div className="email-header">
                                    Job
                                </div>
                                <input type={"text"}
                                       value={job}
                                       onChange={(event) => {
                                           setJob(event.target.value);
                                       }}/>
                            </div>
                            <div className="registerForm">
                                <div className="registerForm2">
                                    <div className="email-header">
                                        Password
                                    </div>
                                    <input type={"password"}
                                           value={password}
                                           onChange={(event) => {
                                               setPassword(event.target.value);
                                           }}/>
                                </div>
                                <div className="registerForm2">
                                    <div className="email-header">
                                        Repeat Password
                                    </div>
                                    <input type={"password"}
                                           value={passwordConfirmation}
                                           onChange={(event) => {
                                               setPasswordConfirmation(event.target.value);
                                           }}/>
                                </div>
                            </div>
                        </div>
                        <Link className="link" to="/">
                            <button type="submit" onClick={save}>Submit</button>
                        </Link>
                    </form>
                </div>
                <div className="setTeam">
                    <form>
                        <div className="login-header-2">
                            If you are not an admin or a manager
                        </div>
                        <div className="login-header-2">
                            please choose a team!
                        </div>
                        <div className="login-header-2">
                            Current team : {team}
                        </div>
                        <div className="login-email">
                            <button type="button" onClick={() => setData("Alfa")}>Alfa</button>
                            <button type="button" onClick={() => setData("Delta")}>Beta</button>
                            <button type="button" onClick={() => setData("Gamma")}>Gamma</button>
                            <button type="button" onClick={() => setData("Delta")}>Delta</button>
                            <button type="button" onClick={() => setData("Omega")}>Omega</button>
                        </div>
                    </form>
                </div>
            </div>
            <div className="backButton">
                <Link className="link" to="/">
                    <button>Back</button>
                </Link>
            </div>
        </div>
    )
}
export default Register;