import { Col, Row, Card, Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import JavaEduUser from "./JavaEduUser";

/*
    This is the scoreboard component. This will fetch all the users from the database and display the student scores.
*/
export default function JavaEduScore(props) {

    const [users, setUsers] = useState([]);

    /*
     * Fetch all the users from the database and filter them to display students only
     */
    useEffect(() => {
        fetch('http://localhost:8080/users', {
        method: "GET"
        })
        .then(res => res.json())
        .then((data) => {
            //console.log(data)
            if(data) {
                let students = data.filter((user) => {
                    if(user.adminStatus) {
                        return false
                    }
                    else {
                        return true
                    }
                })
                setUsers(students)
            }
        })
    }, [])

    useEffect(() => {
        fetch('http://localhost:8080/maze/easy', {
        method: "GET"
        })
        .then(res => res.json())
        .then((data) => {
            console.log(data)
        })
    }, [])

    useEffect(() => {
        fetch('http://localhost:8080/maze/hard', {
        method: "GET"
        })
        .then(res => res.json())
        .then((data) => {
            console.log(data)
        })
    }, [])

    useEffect(() => {
        fetch('http://localhost:8080/maze/default', {
        method: "GET"
        })
        .then(res => res.json())
        .then((data) => {
            console.log(data)
        })
    }, [])

    return <Card className={"sideBorder"}>
        <h1 className={"scoreHeader"}>Student Scoreboard</h1>
        <Row className={"scoreSecondaryHeaders"}>
            <Col><h3>User:</h3></Col>
            <Col><h3>Score:</h3></Col>
        </Row>
        {
            users.length > 0 ?

            <>
                {
                    // loop through each student and build a JavaEduUser component inside the scoreboard for them.
                    users.map((user) => {
                        return <JavaEduUser
                        key={user.email}
                        email={user.email}
                        score={user.score}
                        ></JavaEduUser>
                    })
                }
            </>

            :
            
            <p>No student scores to display!</p>
        }
    </Card>
}