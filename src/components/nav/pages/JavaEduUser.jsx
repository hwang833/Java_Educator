import { Card, Row, Col } from "react-bootstrap";

/*
    This component is a child component of JavaEduScore. It makes up each user entry of the list.
*/
export default function JavaEduUser(props) {

    return (
        <div className={"user"}>
            <Row>
                <Col><p>{props.email}</p></Col>
                <Col><p>{props.score}</p></Col>
            </Row>
        </div>
    );
}
