import React from "react";
import { Col, Container, Row } from "react-bootstrap";

export default function JavaEduInstructions(props) {
  return (
    <div className="JavaEduInstructions" style={{ fontFamily: 'Roboto, sans-serif', border: '1px solid #ddd', borderRadius: '8px', padding: '20px', textAlign: 'center' }}>
      <Container>
        <Row>
          <Col>
            <h1 className="mb-4">Getting Started</h1>
            <p>
              Welcome to the Java Education tool. Follow these steps to get started:
            </p>
            <ol className="mb-4">
              <li>
                <h3>
                  <a href="./sign-in">Sign in</a>
                </h3>
              </li>
              <li>
                <h3>Enter your name and email</h3>
              </li>
              <li>
                <h3>Set up your local development environment</h3>
                <ul>
                  <li>
                    <p>
                      We recommend using a code editor such as{" "}
                      <a href="https://code.visualstudio.com/">Visual Studio Code</a> or{" "}
                      <a href="https://notepad-plus-plus.org/">Notepad++</a>.
                    </p>
                  </li>
                  <li>
                    <p>
                      For a more challenging experience, consider using an Integrated
                      Development Environment (IDE) like{" "}
                      <a href="https://www.eclipse.org/">Eclipse IDE</a> or{" "}
                      <a href="https://www.jetbrains.com/idea/">IntelliJ IDEA</a>.
                    </p>
                  </li>
                </ul>
              </li>
              <li>
                <h3>Start coding</h3>
              </li>
              <li>
                <h3>Upload your code</h3>
              </li>
            </ol>
          </Col>
          <Col>
            <div className="embed-responsive embed-responsive-16by9" style={{ border: '1px solid #ddd', borderRadius: '8px', overflow: 'hidden' }}>
              <iframe
                className="embed-responsive-item"
                src="https://www.youtube.com/embed/fbyobdxDQno?si=EsdNDth04W01wYB_"
                title="YouTube video player"
                frameBorder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                allowFullScreen
                style={{ width: '100%', height: '400px' }} // Adjust width and height as needed
              ></iframe>
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
}