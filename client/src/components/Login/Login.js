
import '../../css/App.css';

import {Form,Button,Alert,Container,Row, Col} from 'react-bootstrap';
import {BsBoxArrowInRight } from "react-icons/bs";
import {useState,useEffect} from 'react';

import {Email,Pwd} from '../Form';

const AlertLogin = (props)=>{

    const [show,setShow] = useState();

    return <>
        {(props.msg.message!=="" &&  props.msg.message!==undefined) ?
            <center>
                <Alert variant={props.msg.type} className="w-100 " onClose={() => setShow(false)} show={show} dismissible>
                    {props.msg.message}
                </Alert>
            </center>
            :false}

    </>
}

function LoginForm(props) {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const signInHandler = (event) => {
        event.preventDefault();

        props.login({username:email,password});

    }

    useEffect(() =>{
        props.setMsg({message:"",type:""});
        // eslint-disable-next-line
    },[]);


    return (<>

            <AlertLogin user={props.user} msg={props.msg}/>
            <Row className = "justify-content-center">
                <Col lg = {4} sm = {10} className = "ms-4">
                    <Container className="shadow-sm p-5 mt-5" id = "cardscontainer">
                        <center className="mb-3 fs-2">
                            {/*<Row>
                                <Col className="d-none d-md-block">
                                    <img src = "http://localhost:3000/brand.svg" style = {{'height': '70px', 'marginLeft': '20px', 'marginTop': '-10px'}}
                                         alt = "navicon"/>
                                </Col>
                            </Row>*/}
                        </center>
                        <Form onSubmit={signInHandler}>

                            <Email obj={{email,setEmail,icon:true}}/>

                            <Pwd obj={{password,setPassword,icon:true}}/>


                            <center>
                                <Button variant="success" size="lg" type="submit">
                                    <BsBoxArrowInRight/>  Login
                                </Button>
                            </center>

                        </Form>
                    </Container>
                </Col>

            </Row>

        </>
    );
}





export default LoginForm;
