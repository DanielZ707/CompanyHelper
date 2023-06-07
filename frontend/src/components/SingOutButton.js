import React, {useState} from "react"
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";


const SignOutComponent = () => {

    const navigate = useNavigate();
    const token = localStorage.getItem('user');

    async function logout(event) {
        event.preventDefault();
        try {
            await axios.post("http://localhost:8080/logout").then((res) => {
                console.log(res.data);
                localStorage.removeItem('user')
                localStorage.removeItem('email')
                navigate('/');
            }, fail => {
                if(fail.message=="Request failed with status code 403"){
                    alert("You have no permission to access the data!")
                    navigate('/')
                }
                console.error(fail);
                alert("Some error has occurred, please try again.")
            })
        } catch (err) {
            alert(err);
        }

    }

    return (
        <div className="logout">
            <Link className="link" to="/">
                <button type="submit" onClick={logout}>Log Out</button>
            </Link>
        </div>
    )
}

export default SignOutComponent;