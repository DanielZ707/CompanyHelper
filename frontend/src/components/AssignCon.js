import React, {useState} from "react"
import axios from "axios"
import logo from "../images/logo.png";
import {Link, useNavigate} from "react-router-dom";

const AssignCon = () => {

    const [name, setName] = useState("");

    const token = localStorage.getItem('user');

    const navigate = useNavigate();

  function setData(name){
      setName(name);
  }
    async function save(event) {
        event.preventDefault();
        try {
            await axios.post("http://localhost:8080/assignToCon", {
                name: name,
                nameCon: localStorage.getItem('currentBuilding')
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
                    localStorage.removeItem('currentBuilding')
                    navigate('/constructionsManager');
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
        <div className="registerPage-teamCon">
            <div className="logo"><img src={logo}/></div>
            <div className="register-teamCon">
                <form>
                    <div className="login-header">
                        Assign team to a created construction!
                    </div>
                    <div className="login-header">
                        Current team : {name}
                    </div>
                    <div className="login-email">
                        <button type="button"  onClick={()=>setData("Alfa")} >Alfa</button>
                        <button type="button"  onClick={()=>setData("Beta")} >Beta</button>
                        <button type="button"  onClick={()=>setData("Gamma")} >Gamma</button>
                        <button type="button"  onClick={()=>setData("Delta")} >Delta</button>
                        <button type="button"  onClick={()=>setData("Omega")} >Omega</button>
                    </div>
                    <button type ="submit" onClick={save}>Submit</button>
                </form>
            </div>
        </div>
    )
}
export default AssignCon;