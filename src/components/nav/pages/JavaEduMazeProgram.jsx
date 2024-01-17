import { Link } from "react-router-dom";
import { useEffect, useRef,useState } from "react";
import Canvas from "./JavaEduMazeCanvas";
import { Col, Row, Card, Button, Form ,Spinner} from "react-bootstrap";
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBackwardFast, faBackwardStep, faBackward, faPlay, faPause, faForward, faForwardFast, faForwardStep } from "@fortawesome/free-solid-svg-icons";

export default function JavaEduMazeProgram(props) {

    const [inputText, setInputText] = useState(""); 
    const [file, setFile] = useState(null);
    const [compilerOutput, setCompilerOutput] = useState("");

    const [selectedDifficulty, setSelectedDifficulty] = useState("");

    const [sequenceOfMoves, setSequenceOfMoves] = useState([]); 
    const [isLoading, setIsLoading] = useState(false);
    
    const updateCompilerOutput = (feedbackMessage, totalMovesValue, score) => {
        
        const outputString = `Compilation result: ${feedbackMessage}\nTotal Moves: ${totalMovesValue}\nScore: ${score}\nPlease press the buttons to display the robot's movement.`;
        setCompilerOutput(outputString);
        
        console.log(outputString);
    
        
    };
    const updateSequenceOfMoves = (steps) => { // 新增这一行
        setSequenceOfMoves(steps);
        dataArrayRef.current = steps;
    };
    

    useEffect(() => {
        if (selectedDifficulty) {
            fetch(`http://localhost:8080/maze/${selectedDifficulty}`,
                {
                    method: "GET"
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log(data);
                    mazeDataRef.current = data;
                })
                .catch(error => {
                    console.error('Error fetching maze:', error);
                });

        }
    }, [selectedDifficulty]);
    
    let sampleMoves = [[6, 8, 1], [6, 8, 2], [6, 9, 2], [6, 10, 2], [6, 11, 2], [6, 11, 1], [7, 11, 1], [8, 11, 1], [9, 11, 1], [10, 11, 1], [11, 11, 1], [11, 11, 0], [11, 11, 3], [10, 11, 3], [9, 11, 3], [9, 11, 0], [9, 10, 0], [9, 10, 1], [10, 10, 1], [10, 10, 2], [10, 11, 2], [10, 11, 3], [9, 11, 3], [8, 11, 3], [7, 11, 3], [6, 11, 3], [6, 11, 0], [6, 10, 0], [6, 9, 0], [6, 8, 0], [6, 7, 0], [6, 6, 0], [6, 6, 3], [5, 6, 3], [5, 6, 0], [5, 5, 0], [5, 4, 0], [5, 4, 1], [6, 4, 1], [6, 4, 0], [6, 3, 0], [6, 2, 0], [6, 2, 3], [5, 2, 3], [4, 2, 3], [3, 2, 3], [2, 2, 3], [1, 2, 3], [1, 2, 2], [1, 2, 1], [2, 2, 1], [2, 2, 0], [2, 1, 0], [2, 1, 3], [1, 1, 3], [1, 1, 0], [1, 0, 0], [1, 0, 1], [2, 0, 1], [3, 0, 1], [3, 0, 2], [3, 0, 3], [2, 0, 3], [2, 0, 2], [2, 1, 2], [2, 2, 2], [2, 2, 1], [3, 2, 1], [4, 2, 1], [5, 2, 1], [6, 2, 1], [7, 2, 1], [7, 2, 0], [7, 1, 0], [7, 1, 1], [7, 1, 2], [7, 2, 2], [7, 2, 1], [7, 2, 0], [7, 1, 0], [7, 1, 1], [8, 1, 1], [9, 1, 1], [9, 1, 2], [9, 2, 2], [9, 2, 1], [9, 2, 0], [9, 1, 0], [9, 0, 0], [9, 0, 1], [10, 0, 1], [11, 0, 1], [11, 0, 2], [11, 1, 2], [11, 2, 2], [11, 3, 2], [11, 3, 3], [10, 3, 3], [10, 3, 2], [10, 4, 2], [10, 5, 2]];
    let sampleMovesWithError = [[6, 8, 1], [6, 8, 2], [6, 9, 2], [6, 10, 2], [6, 11, 2], [6, 11, 1], [7, 11, 1], [8, 11, 1], [9, 11, 1], [10, 11, 1], [11, 11, 1], [11, 11, 0], [11, 11, 3], [10, 11, 3], [9, 11, 3], [9, 11, 0], [9, 10, 0], [9, 10, 1], [10, 10, 1], [10, 10, 2], [10, 11, 2], [10, 11, 3], [9, 11, 3], [8, 11, 3], [7, 11, 3], [6, 11, 3], [6, 11, 0], [6, 10, 0], [6, 9, 0], [6, 8, 0], [6, 7, 0], [6, 6, 0], [6, 6, 3], [5, 6, 3], [5, 6, 0], [5, 5, 0], [5, 4, 0], [5, 4, 1], [6, 4, 1], [6, 4, 0], [6, 3, 0], [6, 2, 0], [6, 2, 3], [5, 2, 3], [4, 2, 3], [3, 2, 3], [2, 2, 3], [1, 2, 3], [1, 2, 2], [1, 2, 1], [2, 2, 1], [2, 2, 0], [2, 1, 0], [2, 1, 3], [1, 1, 3], [1, 1, 0], [1, 0, 0], [1, 0, 1], [2, 0, 1], [3, 0, 1], [3, 0, 2], [3, 0, 3], [2, 0, 3], [2, 0, 2], [2, 1, 2], [2, 2, 2], [2, 2, 1], [3, 2, 1], [4, 2, 1], [5, 2, 1], [6, 2, 1], [7, 2, 1], [7, 2, 0], [7, 1, 0], [7, 1, 1], [7, 1, 2], [7, 2, 2], [7, 2, 1], [7, 2, 0], [7, 1, 0], [7, 1, 1], [8, 1, 1], [9, 1, 1], 
        [-1, "error message\nerrorXYZ\n123", -1]];
    let mazeDataRef = useRef(null);
    let dataArrayRef = useRef(null);
    let controlMode = "ProgramControl"; // KeyboardControl, ProgramControl
    let callbackRef = useRef(null);

    // useEffect(() => { //temporary, simulates a return from backend
    //     const timeout = setTimeout(() => {
    //         mazeDataRef.current = mazeArray;
    //     }, 1000)
    //     return () => clearTimeout(timeout)
    // }, [])

    const handleMazeChange = (selectedOption) => {

        const optionString = String(selectedOption);
        console.log(optionString);
        //dataArrayRef.current = [];

        if (mazeDataRef.current != null) {
            console.log("MazeData not cleared");
            return;
        }
        switch (optionString) {
            case "easy":
                setSelectedDifficulty("easy");
                break;
            case "hard":
                setSelectedDifficulty("hard");
                break;
            case "something else": 

                break;
        }
        console.log(mazeDataRef.current);
    };

    // useEffect(() => { //temporary, simulates a return from backend
    //     const timeout = setInterval(() => {
    //         dataArrayRef.current = [...sampleMovesWithError];
    //     }, 3000)
    //     return () => clearInterval(timeout)
    // }, [])

    // Handle the submission of text and post to the back-end
    const handleTextSubmit = () => {
        setIsLoading(true); //start loading
        const selectedDifficulty = document.getElementById("difficultySelect").value; 

        const formData = new FormData();
        formData.append("text", inputText);
        formData.append("difficulty", selectedDifficulty);

        fetch("http://localhost:8080/uploadtext", {
            method: "POST",
            body: formData
        })
        // .then((response) => {
        //     if (!response.ok) {
        //         throw new Error(`HTTP error! Status: ${response.status}`);
        //     }
        //     // Check if the response has a JSON content type
        //     const contentType = response.headers.get("content-type");
        //     if (contentType && contentType.includes("application/json")) {
        //         return response.json();
        //     } else {
        //         // If not JSON, return the response text
        //         return response.text();
        //     }
        // })
        .then((response) => response.json()) 
        .then((data) => {
            console.log(data);
            
            const successValue = data.success;
            const feedbackMessageValue = data.feedbackMessage;
            const movesValue = data.moves;
            const totalMovesValue = data.totalMoves;
            const sequenceOfMovesValue = data.sequenceOfMoves;
            const scoreValue = data.score;

           
            console.log("Success:", successValue);
            console.log("Feedback Message:", feedbackMessageValue);
            console.log("Moves:", movesValue);
            console.log("Total Moves:", totalMovesValue);
            console.log("Sequence of Moves:", sequenceOfMovesValue);
            console.log("Score:", data.score);

            setIsLoading(false); //complete loading
            updateCompilerOutput(data.feedbackMessage, data.totalMoves, data.score);
            //console.log(data.moves);
            dataArrayRef.current = data.moves;



            // Update sequenceOfMoves
            if (data.sequenceOfMoves) {
                updateSequenceOfMoves(data.sequenceOfMoves);
            }
        })
        .catch((error) => {
            console.error(error);
            setIsLoading(false); //complete loading
            updateCompilerOutput("Error occurred during compilation.");
        });

    };

    // Handle the change of the file input
    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    // Handle the submission of the file and post to the back-end
    const handleFileSubmit = () => {

        const selectedDifficulty = document.getElementById("difficultySelect").value;
        setIsLoading(true); //start loading
        // event.preventDefault();
        
        
        // Create FormData and append the file to it
        const formData = new FormData();
        formData.append("file", file);
        formData.append("difficulty", selectedDifficulty);
        console.log(formData);

        // Send a POST request to upload the file
        fetch("http://localhost:8080/uploadfile", {
            method: "POST",
            body: formData
        })
            // .then((response) => {
            //     if (!response.ok) {
            //         throw new Error(`HTTP error! Status: ${response.status}`);
            //     }
            //     // Check if the response has a JSON content type
            //     const contentType = response.headers.get("content-type");
            //     if (contentType && contentType.includes("application/json")) {
            //         return response.json();
            //     } else {
            //         // If not JSON, return the response text
            //         return response.text();
            //     }
            // })
            .then((response) => response.json()) 
            .then((data) => {
                console.log(data);
                
                const successValue = data.success;
                const feedbackMessageValue = data.feedbackMessage;
                const movesValue = data.moves;
                const totalMovesValue = data.totalMoves;
                const sequenceOfMovesValue = data.sequenceOfMoves;
                const scoreValue = data.score;

               
                console.log("Success:", successValue);
                console.log("Feedback Message:", feedbackMessageValue);
                console.log("Moves:", movesValue);
                console.log("Total Moves:", totalMovesValue);
                console.log("Sequence of Moves:", sequenceOfMovesValue);
                console.log("Score:", data.score);

                setIsLoading(false); //complete loading
                updateCompilerOutput(data.feedbackMessage, data.totalMoves, data.score);
                //console.log(data.moves);
                dataArrayRef.current = data.moves;



                // Update sequenceOfMoves
                if (data.sequenceOfMoves) {
                    updateSequenceOfMoves(data.sequenceOfMoves);
                }
            })
            .catch((error) => {
                console.error(error);
                setIsLoading(false); //complete loading
                updateCompilerOutput("Error occurred during compilation.");
            });

    };

    const handleButtonClick = (direction) => {
        if (callbackRef.current == null) {return;}

        switch (direction) {
            case 'toStart':
                callbackRef.current ({key: "1"});

                break;
            case 'rewind':
                callbackRef.current ({key: "2"});
               
                break;
            case 'stepBack':
                callbackRef.current ({key: "3"});
                
                break;
            case 'playPause':
                callbackRef.current ({key: "4"});
                
                break;
            case 'stepForward':
                callbackRef.current ({key: "5"});
                
                break;
            case 'forward':
                callbackRef.current ({key: "6"});
                
                break;
            case 'toEnd':
                callbackRef.current ({key: "7"});
                
                break;
            default:
                break;
        }
    };


    return (
        <div style={{ display: "flex", height: "100%" }}>
            <div style={{ flex: 1 }}>
                <h2 style={{ fontSize: "2vw" }}>Maze program control</h2>
                <p>Use number keys 1-7</p>
                <p>
                    <Link to="/">Back to home page.</Link>
                </p>
                <p>
                    <Link to="/mazekeyboard">Go to Sandbox Mode</Link>
                </p>
  
                <div>
                    <label>Select Maze Difficulty: </label>
                    <select id="difficultySelect" defaultValue={'DEFAULT'} onChange={(e) => handleMazeChange(e.target.value)}>
                        <option value="DEFAULT" disabled>
                            Select Difficulty
                        </option>
                        <option value="easy">Easy</option>
                        <option value="hard">Hard</option>
                        <option value="something else">Something Else</option>
                    </select>
                </div>
                {Canvas(mazeDataRef, controlMode, dataArrayRef, callbackRef)}
                <div style={{ marginTop: '10px' }}>
                    <Button variant="primary" style={{ marginRight: '5px' }} onClick={() => handleButtonClick('toStart')}>
                        <FontAwesomeIcon icon={faBackwardFast} />
                    </Button>
                    <Button variant="primary" style={{ marginRight: '5px' }} onClick={() => handleButtonClick('rewind')}>
                        <FontAwesomeIcon icon={faBackward} />
                    </Button>
                    <Button variant="primary" style={{ marginRight: '5px' }} onClick={() => handleButtonClick('stepBack')}>
                        <FontAwesomeIcon icon={faBackwardStep} />
                    </Button>
                    <Button variant="primary" style={{ marginRight: '5px' }} onClick={() => handleButtonClick('playPause')}>
                        <FontAwesomeIcon icon={faPlay} /><FontAwesomeIcon icon={faPause} />
                    </Button>
                    <Button variant="primary" style={{ marginRight: '5px' }} onClick={() => handleButtonClick('stepForward')}>
                        <FontAwesomeIcon icon={faForwardStep} />
                    </Button>
                    <Button variant="primary" style={{ marginRight: '5px' }} onClick={() => handleButtonClick('forward')}>
                        <FontAwesomeIcon icon={faForward} />
                    </Button>
                    <Button variant="primary" onClick={() => handleButtonClick('toEnd')}>
                        <FontAwesomeIcon icon={faForwardFast} />
                    </Button>
                </div>
            </div>
      
            <div style={{ position: "absolute", top: "12%", left: "40%", width: "50%", height: "50%", borderLeft: "0.3px solid rgb(169, 169, 169)", borderBottom: "0.3px solid rgb(169, 169, 169)", borderRight: "0.3px solid rgb(169, 169, 169)" ,borderRadius: "10px" }}>
                <Tabs
                    defaultActiveKey="File Upload"
                    id="uncontrolled-tab-example"
                    className="mb-3"
                    // style={{ borderTop: 'none', borderBottom: '1px solid black' }}
                >
                    <Tab eventKey="File Upload" title="File Upload">
                        <div style={{ position: "absolute", top: "20%", left: "4%", width: "80%", height: "75%" }}>
                            {/* File input and submit button for file */}
                            <input type="file" onChange={handleFileChange} />
                            <Button style={{ position: "absolute", top: "80%", left: "0%", width: "40%", height: "15%" }} onClick={handleFileSubmit}>
                                Upload File and Compile
                            </Button>
                        </div>
                    </Tab>
                    <Tab eventKey="Text Entry" title="Text Entry">
                        <div style={{ position: "absolute", top: "15%", left: "4%", width: "90%", height: "80%"  }}>
                            {/* Multi-line text input */}
                            <textarea
                                rows="8"
                                placeholder="Enter Java code here"
                                value={inputText}
                                style={{ position: "absolute", top: "0%", left: "0%", width: "100%", height: "75%" }} 
                                onChange={(e) => setInputText(e.target.value)}
                            />
                            {/* Submit button for text */}
                            <Button style={{ position: "absolute", top: "80%", left: "0%", width: "40%", height: "15%" }} onClick={handleTextSubmit}>
                                Submit Text and Compile
                            </Button>
                            
                        </div>
                    </Tab>
                </Tabs>
            </div>

            <div style={{ 
    position: "absolute", 
    top: "70%", 
    left: "40%", 
    width: "50%", 
    height: "20%", 
    border: "0.3px solid rgb(169, 169, 169)", 
    borderRadius: "10px",
    padding: "15px", 
    whiteSpace: "pre-line",
    fontWeight: "bold", 
    fontSize: "15px", 
   
}}>
    {isLoading && (
        <div>
            <div>Successfully uploaded code</div>
            <div>Compiling...</div>
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    )}
    {!isLoading && compilerOutput}
</div>


        
        </div>
    );
}  
