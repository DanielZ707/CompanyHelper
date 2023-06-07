import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSquarePlus} from "@fortawesome/free-regular-svg-icons";
import Navbar from "./Navbar";
import React, {useEffect, useState} from "react";
import Axios from "axios";
import Construction from "./Construction";
import {useNavigate} from "react-router-dom";
import Construction2 from "./Construction2";

const Constructions = () => {


    const [constructions, setConstructions] = useState("")

    const token = localStorage.getItem('user');
    const navigate = useNavigate();


    useEffect(() => {
        Axios.get("http://localhost:8080/allConstructions", {
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((res) => {
            setConstructions(res.data)
        }, fail => {
            if(fail.message=="Request failed with status code 403"){
                alert("You have no permission to access the data!")
                navigate('/')
            }
            console.error(fail);
            alert("Some error has occurred, please try again.")
        })


    }, [constructions, token]);



    return (
        <div className="constructionsPage">
            <Navbar/>
            <div className="constructions">
                {constructions &&
                    constructions.length > 0 &&
                    constructions.map(({idConstruction, name, town, street, buildingNumber, deadlineDay, progress}) => {
                        return (<Construction2 nameCon = {name}/>);
                    })}
            </div>
        </div>
    );
}

export default Constructions;