import { Col, Row, Card, Button } from "react-bootstrap";
import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import jwt_decode from "jwt-decode";

import JavaEduDataContext from "../../../contexts/JavaEduDataContext";

/*
    This is the signout component. This will be the frontend for signing out.
*/
export default function JavaEduSignOut(props) {

    const [signedIn, setSignedIn] = useContext(JavaEduDataContext);

    //create function to navigate back to home
    const navigate = useNavigate();
    const handleGoToHomePage = () => navigate("/");

    /*
     * Signs out the user when the button is clicked. Remove all their data from localstorage and set them back to "guest mode".
     * The user is then redirected to the home page.
     */
    function handleSignOut() {
        
        localStorage.removeItem("user");
        setSignedIn(false);

        handleGoToHomePage();
    }

    return <div className={"sign-in-container"}>
        <Row>
            <Col></Col>
            <Col>
                <Card>
                    <div className={"sign-in-header"}>
                        <h4>Sign Out</h4>
                    </div>
                    <div className="form">
                        <form>
                            <div className={"sign-in-button text-center"}>
                                <Button onClick={handleSignOut} variant="success">Sign Out</Button>
                            </div>
                        </form>
                    </div>
                </Card>
            </Col>
            <Col></Col>
        </Row>
    </div>
}