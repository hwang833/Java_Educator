import React from "react";
import { Col, Container, Row, Card, Button } from "react-bootstrap";

export default function JavaEduLearnMore(props) {
  return (
    <div className="JavaEduLearnMore" style={{ textAlign: 'center', padding: '20px' }}>
      <Container>
        <Row>
          <Col>
            <h1 style={{ fontSize: '3rem', marginBottom: '20px' }}>Learn More!</h1>
            <h2 style={{ marginBottom: '20px' }}>Rules for the Program:</h2>
            <Card style={{ textAlign: 'left', marginBottom: '20px' }}>
              <Card.Body>
                <ol style={{ fontWeight: 'bold' }}>
                  <li>The maze is a 2D grid.</li>
                  <li>Your robot has sensors allowing it to see the squares directly above, below, left, and right.</li>
                  <li>The robot can identify whether the square is the goal, an open space, or a wall.</li>
                  <li>The robot can rotate 90 degrees left or right and move to an open space ahead or behind.</li>
                  <li>Rotating or moving 1 square is considered 1 move.</li>
                </ol>
              </Card.Body>
            </Card>
            <h2 style={{ marginTop: '20px', marginBottom: '20px' }}>Methods You Can Use:</h2>
            <Card style={{ textAlign: 'left', marginBottom: '20px' }}>
              <Card.Body>
                <ul style={{ listStyleType: 'none' }}>
                  <li>
                    <h3>moveForward() - Moves the robot one step forward.</h3>
                  </li>
                  <li>
                    <h3>moveBackward() - Moves the robot one step backward.</h3>
                  </li>
                  <li>
                    <h3>rotateLeft() - Rotates the robot 90 degrees to the left.</h3>
                  </li>
                  <li>
                    <h3>rotateRight() - Rotates the robot 90 degrees to the right.</h3>
                  </li>
                  <li>
                    <h3>checkSensor(direction) - Check the specified direction ('UP', 'DOWN', 'LEFT', 'RIGHT') and returns the state of the cell.</h3>
                  </li>
                </ul>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
}
