import { Col, Row, Card, Button } from "react-bootstrap";
import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { GoogleLogin } from '@react-oauth/google';
import jwt_decode from "jwt-decode";

import JavaEduDataContext from "../../../contexts/JavaEduDataContext";

/*
    This is the signin component. This will be the frontend for registering/login.
*/
export default function JavaEduSignIn(props) {

    const [isAdmin, setIsAdmin] = useState(false);

    const [signedIn, setSignedIn] = useContext(JavaEduDataContext);

    //create function to navigate back to home
    const navigate = useNavigate();
    const handleGoToHomePage = () => navigate("/");

    /*
    * Takes a credential response as argument. This should only ever come from and be called by the GoogleLogin element.
    * Decodes the token, gathers the information and creates a post for the user.
    * Will sign in if they exist, will create an account and sign in if they dont exist (check by email).
    */
    function handleGoogleSSO(credentialResponse) {

        var credentialResponseDecoded = jwt_decode(credentialResponse.credential);

        //posting the info to create new user (wont create a user if their email exists)
        fetch('http://localhost:8080/user', {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify({
                email: credentialResponseDecoded.email,
                name: credentialResponseDecoded.name,
                adminStatus: isAdmin,
                operatingMode: "practice",
                score: 0
            })
        })
        .then(res => res.text())
        .then((dataStr) => {
            console.log(dataStr)

            let userData = JSON.stringify({
                email: credentialResponseDecoded.email,
                name: credentialResponseDecoded.name,
                adminStatus: isAdmin,
                operatingMode: "practice",
                score: 0
            });

            localStorage.setItem("user", userData);
            setSignedIn(true);

            handleGoToHomePage();
        })
    }

    /*
     * Toggles the isAdmin boolean
     */
    function handleAdminStatus() {
        if(isAdmin) {
            setIsAdmin(false);
        }
        else {
            setIsAdmin(true);
        }
    }

    /*
     * Toggles the sign in for developers that may not have a spring-boot connection.
     * 
     */
    function handleDeveloperSignIn() {
        setSignedIn(true);
        handleGoToHomePage();
    }

    return <div className={"sign-in-container"}>
        <Row>
            <Col></Col>
            <Col>
                <Card>
                    <div className={"sign-in-header"}>
                        <h4>Sign in</h4>
                    </div>
                    <div className="form">
                        <form>
                            <div className={"sign-in-button text-center"}>
                                <GoogleLogin
                                    onSuccess={credentialResponse => handleGoogleSSO(credentialResponse)}
                                    onError={() => {
                                        console.log('Login Failed');
                                }}
                                />
                                <p>Signing in as <b>{isAdmin ? "admin" : "student"}</b></p>
                                <p><Button variant="link" onClick={handleAdminStatus}>{isAdmin ? "not an admin?" : "not a student?"}</Button></p>
                            </div>
                        </form>
                    </div>
                </Card>
                <Card style={{marginTop: 50}}>
                    <div className={"sign-in-header"}>
                        <h4>Continue as Developer</h4>
                    </div>
                    <div className="form">
                        <form>
                            <div className={"sign-in-button text-center"}>
                                <p><b>WARNING</b>: You may encounter bugs if you have no spring-boot connection!</p>
                                <Button onClick={handleDeveloperSignIn}>Continue</Button>
                            </div>
                        </form>
                    </div>
                </Card>
            </Col>
            <Col></Col>
        </Row>
    </div>
}