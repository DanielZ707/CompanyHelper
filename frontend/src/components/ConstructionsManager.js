import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-regular-svg-icons";
import Navbar from "./Navbar";
import React, {useEffect, useState} from "react";
import Axios from "axios";
import {useNavigate} from "react-router-dom";
import Construction2 from "./Construction2";

const ConstructionsManager = () => {

    async function addCon(event) {
        event.preventDefault();
        navigate('/addCon')

    }

    const [constructions, setConstructions] = useState("")
    const navigate = useNavigate();

    const token = localStorage.getItem('user');


    useEffect(() => {
        Axios.get("http://localhost:8080/allConstructions", {
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((res) => {
            setConstructions(res.data)
        }, fail => {
            if(fail.message==="Request failed with status code 403"){
                alert("You have no permission to access the data!")
                navigate('/')
            }
            console.error(fail);
            alert("Some error has occurred, please try again.")
        })


    }, [constructions, navigate, token]);



    return (
        <div className="constructionsPage">
            <Navbar/>
            <div className="constructions">
                {constructions &&
                    constructions.length > 0 &&
                    constructions.map(({name}) => {
                        return (<Construction2 nameCon = {name}/>);
                    })}
                <div className="button1">
                    <button onClick={addCon}><FontAwesomeIcon icon={faSquarePlus}/> Add Construction</button>
                </div>
            </div>
        </div>
    );
}

export default ConstructionsManager;