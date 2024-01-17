import { Col, Container, Row } from "react-bootstrap";

/*
    This is the home page component.
*/
export default function JavaEduLanding(props) {

    return <div>
        <h1>About Java Educator</h1>
        <br/>
        <Container fluid={true}>
            <Row>
                <Col xs={12} lg={4} xl={6}>
                    <p>Welcome to the Java Education Tool.</p>
                    <p>Interested in trying the tool? <a target="_blank" href="./sign-in">Sign in here!</a></p>
                    <h3>The Java Education Project...</h3>
                    <p>Java education tool is an interactive, web-based application that is meant to be used as a motivator for students that haven’t learned any coding. The main feature of the software is a maze challenge that has a robot. Students must use java code to help a simplified robot escape the maze programmatically. There will be a mode so students can practice how the robot works and a mode that allows them to test out their code. Students and teachers will be able to login to this tool to access separate features.</p>
                </Col>
                <Col xs={12} lg={8} xl={6}>
                    <iframe width="560" height="315" src="https://www.youtube.com/embed/l9AzO1FMgM8?si=LWVK_TykZh7NNRyW" title="YouTube video player" frameBorder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowFullScreen></iframe>
                </Col>
            </Row>
        </Container>
    </div>
}