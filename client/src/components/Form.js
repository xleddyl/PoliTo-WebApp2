import {Form} from 'react-bootstrap';
import {BsFillEnvelopeFill,BsLockFill} from "react-icons/bs";


//Tutti i componenti voglio props.obj = {email,setEmail,label};

//props = email,setEmail,icon = {true/false}
const Email = (props)=>{

    const {email,setEmail,icon} = props.obj;

    return <>
        <Form.Group className="mb-3" controlId="formPlaintextEmail">
            <Form.Label>
                {icon ? <BsFillEnvelopeFill />:false} Email
            </Form.Label>

            <Form.Control type="email" placeholder='email@example.com'
                          value={email}
                          required={true}
                          onChange={(event) => setEmail(event.target.value)}
            />

        </Form.Group>
    </>;
}

//props = password,setPassword,icon = {true/false}
const Pwd = (props)=>{

    const {password,setPassword,icon} = props.obj;

    return <>
        <Form.Group className="mb-3" controlId="formPlaintextPassword">
            <Form.Label>
                {icon ? <BsLockFill />:false} Password
            </Form.Label>

            <Form.Control type="password" placeholder="password"
                          value={password}
                          required={true}
                          onChange={(event) => setPassword(event.target.value)}
            />

        </Form.Group>
    </>;
}

//props = label,text,setText,
const Text = (props)=>{

    const {label,text,setText} = props.obj;

    return <>
        <Form.Group className="mb-3" >

            <Form.Label>{label} </Form.Label>

            <Form.Control type="text" placeholder={"Enter "+label}
                          value={text}
                          required={true}
                          onChange={(event) => setText(event.target.value)}
            />

        </Form.Group>
    </>;
}

const Url = (props)=>{

    const {label,url,setUrl} = props.obj;

    return <>
        <Form.Group className="mb-3" >

            <Form.Label>{label} </Form.Label>

            <Form.Control type="url" placeholder={"Enter "+label}
                          value={url}
                          required={false}
                          onChange={(event) => setUrl(event.target.value)}
            />

        </Form.Group>
    </>;
}


//props = label,text,disabled={true}
const DisabledText = (props)=>{

    const {label,text} = props.obj;

    return <>
        <Form.Group className="mb-3" controlId="formPlaintext">

            <Form.Label>{label} </Form.Label>

            <Form.Control type="text" placeholder={"Enter "+label}
                          value={text}
                          required={true}
                          disabled={true}
            />

        </Form.Group>
    </>;
}

//props = label,time,setTime
const Time = (props) =>{

    const {label,time,setTime} = props.obj;

    return <>
        <Form.Label>{label}</Form.Label>
        <Form.Control type="time"
                      value={time}
                      required={true}
                      onChange={ev => setTime(ev.target.value)}
        />
    </>;

}


//props = label,number,setNumber,
const Number = (props)=>{

    const {disabled,label,number,setNumber} = props.obj;

    return <>
        <Form.Group className="mb-3" >
            <Form.Label>{label}</Form.Label>
            <Form.Control
                type="number"
                placeholder={"Enter "+label}
                value={number}
                required={true}
                disabled={disabled}
                onChange={(ev)=>setNumber(ev.target.value)}
            />
        </Form.Group>
    </>;
}

//props = label,area,setArea,row
const Area = (props)=>{

    const {label,area,setArea,row} = props.obj;

    return <>
        <Form.Group className="mb-3" >
            <Form.Label>{label}</Form.Label>

            <Form.Control
                as="textarea"
                value={area}
                placeholder={"Enter "+label}
                row={row}
                required={true}
                onChange={(ev)=>setArea(ev.target.value)}
            />
        </Form.Group>
    </>;
}

//props = setFile
const FileItem = (props)=>{

    return <>
        <Form.Group controlId="formFile" className="mb-3">

            <Form.Control type="file"  required={true}
                          onChange={(ev)=>{props.setFile(ev.target.files[0])}}
            />
        </Form.Group>
    </>;
}

//label,options=[],setSelect,firstOption
const Select = (props)=>{

    const {label,firstOption,setSelect,options} = props.obj;

    return <>
        <Form.Label>{label}</Form.Label>

        <Form.Select aria-label="Default select example" onChange={ev => setSelect(ev.target.value)}>
            <option>{firstOption}</option>
            {options.map(o=>
                <option key={options.indexOf(o)} value={o}>{o}</option>
            )}
        </Form.Select>
    </>;
}



export {Email,Pwd,Text,Number,DisabledText,Area,FileItem,Select,Url,Time};