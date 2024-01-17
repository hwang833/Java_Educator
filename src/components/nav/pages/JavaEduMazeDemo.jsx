import { Col, Row, Card, Button, Form } from "react-bootstrap";
import { useState, useEffect } from "react";

export default function JavaEduTesting(props) {
    // usable variables
    const [mazeMatrix, setMazeMatrix] = useState([]);
    const [inputText, setInputText] = useState("");
    const [file, setFile] = useState(null);

    // Fetch maze matrix when the component loads
    useEffect(() => {
        fetchMazeMatrix();
    }, []);

    // Fetch maze matrix 
    const fetchMazeMatrix = () => {
        fetch("http://localhost:8080/move?direction=none", {
            method: "GET"
        })
        .then(res => res.json())
        .then(data => {
            setMazeMatrix(data);
        })
        .catch(error => {
            console.error(error);
        });
    };

    // Send a request to move in a specified direction
    const sendRequest = (direction) => {
        fetch(`http://localhost:8080/move?direction=${direction}`, {
            method: "GET"
        })
        .then(res => res.json())
        .then(data => {
            setMazeMatrix(data);
        })
        .catch(error => {
            console.error(error);
        });
    };

    // Handle the submission of text and post to the back-end
    const handleTextSubmit = () => {
        fetch("http://localhost:8080/uploadtext", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ text: inputText })
        })
        .then(response => response.json())
        .then(data => {
            console.log(data.message);
        })
        .catch(error => {
            console.error(error);
        });
    };

    // Handle the change of the file input 
    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    // Handle the submission of the file and post to the back-end
    const handleFileSubmit = () => {
        // event.preventDefault();
        
        // Create FormData and append the file to it
        const formData = new FormData();
        formData.append("file", file);
        console.log(formData);

        // Send a POST request to upload the file
        fetch("http://localhost:8080/uploadfile", {
            method: "POST",
            body: formData
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            // Check if the response has a JSON content type
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                return response.json();
            } else {
                // If not JSON, return the response text
                return response.text();
            }
        })
        .then((data) => {
            console.log(data);
        })
        .catch((error) => {
            console.error(error);
        });

    };

    // create the webpage
    return (
        <div className={"sign-in-container"}>
            <Row>
                <Col>
                    {/* Display the maze matrix */}
                    <div className="maze-container" style={{ border: "1px solid black", width: "500px", height: "400px", overflow: "auto", padding: "20px" }}>
                        <pre style={{ fontSize: "30px" }}>{JSON.stringify(mazeMatrix, null, 2)}</pre>
                    </div>
                </Col>
                <Col style={{ border: "1px solid black", width: "500px" }}>
                    <div style={{ display: "flex", flexDirection: "column", height: "400px" }}>
                        {/* Multi-line text input */}
                        <textarea
                            rows="20"
                            placeholder="Enter Java code here"
                            value={inputText}
                            onChange={(e) => setInputText(e.target.value)}
                        />
                        {/* Submit button for text */}
                        <Button onClick={handleTextSubmit}>Submit</Button>
                        <div style={{ marginTop: "10px" }}>
                            {/* File input and submit button for file */}
                            <input type="file" onChange={handleFileChange} />
                            <Button onClick={handleFileSubmit}>Upload File</Button>
                        </div>
                    </div>
                </Col>
            </Row>
            <Row>
                <Col>
                    {/* Button container for directional buttons */}
                    <div className="button-container" style={{ display: "flex", justifyContent: "left" }}>
                        <Button onClick={() => sendRequest('up')}>Up</Button>
                        <Button onClick={() => sendRequest('down')}>Down</Button>
                        <Button onClick={() => sendRequest('left')}>Left</Button>
                        <Button onClick={() => sendRequest('right')}>Right</Button>
                    </div>
                </Col>
            </Row>
        </div>
    );
}
