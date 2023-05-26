import {useState} from 'react'
//import {callAPI} from './api/API'

import { BrowserRouter, Routes, Route, useNavigate, Navigate} from 'react-router-dom';
import Home from './components/Home';
import LoginForm from "./components/Login/Login";
import SignIn from "./components/Login/RegPage";
import Layout from "./components/Layout";


import './css/styles.css';
import './css/App.css'
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {
    return(
        <BrowserRouter>
            <App2/>
        </BrowserRouter>
    )
}
function App2() {

    const [status,setStatus] = useState("undefined");
    const [msg, setMsg] = useState("");
    const [user, setUser] = useState(undefined);

    const login = async (credentials) => {

        /*try {
            const user = await APILogin.logIn(credentials);
            if(user.message !== undefined)
            {
                setMsg({message: user.message, type: "danger"});
            } else {
                setUser(user);
                navigate(`/`);
                setMsg({message: `Welcome ${user.username}!`, type: "success"})
            }
        }
        catch (err) {
            setMsg({message: err.message, type: "danger"});
        }*/
    }

    const logout = async () => {
       /* await APILogin.logOut()
        setUser(undefined);
        setMsg({message: "You have been logged out!", type: "warning"})
        navigate("/");*/
    }
    const addUser = async (email,password,role, name, surname,username)=>{

        const user = {email,password,role, name, surname,username}
        try {
            //await APILogin.addUser(user);
            setStatus("success");
            setMsg({message: 'Check email to activate your account', type: "success"});

        }
        catch(error){
            setStatus("error");

            if(error.message.includes("Email already registered!") || error.message.includes("Username already used!"))
                setMsg({message: 'User previously defined', type: "danger"});
            else if(error.message.includes("Invalid value"))
                setMsg({message: 'Error with format of data', type: "danger"});
            else
                setMsg({message: 'Sorry, something went wrong', type: "danger"});


        }
    }




    return (
       <Routes>
           <Route element = {<Layout user = {user} logout = {logout}/>}>
               <Route path="/" element={<Home />}/>
               {/* <Route path='/login' element={(!user && <LoginForm login={login} msg={msg} setMsg={setMsg}/>) || <Navigate replace to='/' />}/>
               <Route path='/register' element={(!user && <SignIn addUser={addUser} status={status} setStatus={setStatus} msg={msg}/>) || <Navigate replace to='/' />} /> */}
           </Route>

       </Routes>
    )
}

export default App
