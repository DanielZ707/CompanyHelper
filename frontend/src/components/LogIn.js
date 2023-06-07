import React, {useState} from "react"
import axios from "axios"
import logo from "../images/logo.png";
import {Link, useNavigate} from "react-router-dom";

const LogIn = () => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const axiosStrict = axios.create({
        transitional: {
            silentJSONParsing: false
        },
        responseType: "json"
    });

    async function login(event) {
        event.preventDefault();
            try {
                await axiosStrict.post("http://localhost:8080/authenticate", {
                    email: email,
                    password: password,
                }).then((res) => {
                    console.log(res.data)


                    if (res.data === "We are sorry but you are not one of our employees") {
                        alert("We are sorry but you are not one of our employees");
                    } else {
                        localStorage.setItem('user', res.data.accessToken)
                        localStorage.setItem('refresh', res.data.refreshToken)
                        localStorage.setItem('email', email)
                        navigate('/dashboard');

                    }
                }, fail => {
                    console.error(fail);
                    alert("Some error has occurred, please try again.")
                })
            } catch (err) {
                alert(err);
            }

    }

    return (
        <div className="loginPage">
            <div className="logo"><img src={logo}/></div>
            <div className="login">
                <form>
                    <div className="login-header">
                        Log In
                    </div>
                    <div className="login-email">
                        <div className="email-header">
                            E-mail
                        </div>
                        <input type={"email"} id="email" value={email}
                               onChange={(event) => {
                                   setEmail(event.target.value);
                               }}/>
                    </div>
                    <div className="login-password">
                        <div className="password-header">
                            Password
                        </div>
                        <input type={"password"} id="password"
                               value={password}
                               onChange={(event) => {
                                   setPassword(event.target.value);
                               }}/>
                    </div>
                    <Link className="link" to="/dashboard">
                        <button type="submit" onClick={login} >Log In</button>
                    </Link>
                    <Link className="link" to="/register">
                        <button>I do not have an account</button>
                    </Link>
                </form>
            </div>
        </div>
    )
}
export default LogIn;