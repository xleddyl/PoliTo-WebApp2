import {  Form, Button,  Alert, Container, Row, Col } from 'react-bootstrap';
import '../../css/App.css';
import { useState,useEffect } from 'react';

import {Text,Email,Pwd} from '../Form';

import {FaRegIdCard} from 'react-icons/fa';

const AlertSignIn = (props)=>{

    const [show,setShow] = useState();

    return <>
        {props.status === "success" ?
            <Alert variant={props.msg.type} className="w-100 " onClose={() => setShow(false)} show={show} dismissible>
                {props.msg.message}
            </Alert> : false}
        {props.status === "error" ?
            <Alert variant={props.msg.type} className="w-100 " onClose={() => setShow(false)} show={show} dismissible>
                {props.msg.message}
            </Alert> : false}
    </>
}

function SignIn(props) {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [username, setUsername] = useState("");

    const resetState = () => {
        setEmail("");
        setPassword("");
        setName("");
        setSurname("");
        setUsername("");
    }
    useEffect(() =>{
        props.setStatus("");
    },[]);
    const signInHandler = (event) => {

        event.preventDefault();

        props.addUser(email, password, "hiker", name, surname, username);

        resetState();


    }

    return (
        <>
            <Row className = "justify-content-center">
                <Col lg = {4} sm = {10} className = "ms-4">
                    <Container className="shadow-sm p-5 mt-5" id = "cardscontainer">

                        <AlertSignIn msg={props.msg} status={props.status}/>

                        <center><FaRegIdCard fontSize="3rem"/></center>

                        <Form onSubmit={signInHandler}>

                            <Text obj={{label:"Username",text:username,setText:setUsername}}/>
                            <Text obj={{label:"Name",text:name,setText:setName}}/>
                            <Text obj={{label:"Surname",text:surname,setText:setSurname}}/>

                            <Email obj={{icon:false,email,setEmail}}/>

                            <Pwd obj={{icon:false,password,setPassword}}/>

                            <center><Button variant="primary" size="lg" type="submit">Register</Button></center>

                        </Form>
                    </Container>
                </Col>
            </Row>

        </>
    );
}





export default SignIn;