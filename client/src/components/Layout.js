import { Row } from "react-bootstrap";
import { Outlet } from "react-router-dom";
//import Navigation from "./Navigation";

function Layout(props) {
    return (
        <>
            {/* -- NAVIGATION BAR -- */}
            <Row className = "g-0">
                {/*<Navigation user = {props.user} logout = {props.logout}/>*/}
            </Row>

            {/* -- BODY  -- */}
            <div className = "row gx-0" id = "bg">
                <div className = "row gx-0">
                    <Outlet />
                </div>
            </div>
        </>
    )
};

export default Layout;