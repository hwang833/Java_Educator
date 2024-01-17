import { Container, Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";

import crest from '../../assets/team_logo_transparent.png'

/*
    This is the navbar at the top of every page. Contains links to all accessible pages.
*/
export default function JavaEduNavbar(props) {
    return <Navbar bg="light" variant="light" sticky="top" expand="sm" collapseOnSelect>
        <Container>
            <Navbar.Toggle aria-controls="responsive-navbar-nav" />
            <Navbar.Brand as={Link} to="/">
                <img
                    alt="Java Educator Logo"
                    src={crest}
                    width="30"
                    height="30"
                    className="d-inline-block align-top"
                />{' '}
                Java Educator
            </Navbar.Brand>
            <Navbar.Collapse id="responsive-navbar-nav" className="me-auto">
                <Nav>
                    <Nav.Link as={Link} to="/">Home</Nav.Link>
                    <Nav.Link as={Link} to="/instructions">Instructions</Nav.Link>
                    <Nav.Link as={Link} to="/learn-more">Learn More</Nav.Link>
                    <Nav.Link as={Link} to="/score">Scoreboard</Nav.Link>
                    {/*<Nav.Link as={Link} to="/mazedemo">Maze Demo</Nav.Link>*/}
                    <Nav.Link as={Link} to="/mazeprogram">Maze Program</Nav.Link>
                    <Nav.Link as={Link} to="/mazekeyboard">Maze Sandbox</Nav.Link>
                    <Nav.Link as={Link} to="/mazeeditor">Maze Editor</Nav.Link>
                    <Nav.Link as={Link} to="/sign-out">Sign Out</Nav.Link>
                </Nav>
            </Navbar.Collapse>
        </Container>
    </Navbar>
}
