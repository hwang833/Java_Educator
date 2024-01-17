import { Link } from "react-router-dom";
import { useEffect, useRef,useState } from "react";
import Canvas from "./JavaEduMazeCanvas";
import { Col, Row, Card, Button, Form } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowUp, faArrowDown, faArrowRight, faArrowLeft } from "@fortawesome/free-solid-svg-icons";


export default function JavaEduMazeSandbox(props) {

    const [mazeArray, setMazeArray] = useState([]);
    const [selectedDifficulty, setSelectedDifficulty] = useState("");


    
    let mazeDataRef = useRef(null);
    let dataArrayRef = useRef(null);
    let controlMode = "KeyboardControl"; // KeyboardControl, ProgramControl
    let callbackRef = useRef(null);




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
                    setMazeArray(data);
                    mazeDataRef.current = data;
                })
                .catch(error => {
                    console.error('Error fetching maze:', error);
                });

        }
    }, [selectedDifficulty]);
    

    const handleMazeChange = (selectedOption) => {
        console.log(selectedOption);
        setSelectedDifficulty(selectedOption);
    };
    
    const handleButtonClick = (direction) => {
       if (callbackRef.current == null) {return;}

        switch (direction) {
            case 'forward':
                callbackRef.current ({key: "w"});

                break;
            case 'backward':
                callbackRef.current ({key: "s"});
               
                break;
            case 'right':
                callbackRef.current ({key: "d"});
                
                break;
            case 'left':
                callbackRef.current ({key: "a"});
                
                break;
            default:
                break;
        }
    };
    

    return (
        <div>
            <h2>Maze manual control</h2> 
            <p>Use wasd or arrow keys</p>
            <p>
                <Link to="/">Back to home page.</Link>
            </p>
            <p>
                <Link to="/mazeprogram">Go to Program Mode</Link>
            </p>
            <div>
                <label>Select Maze Difficulty: </label>
                <select defaultValue={'DEFAULT'} onChange={(e) => handleMazeChange(e.target.value)}>
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
                <Button variant="primary" style={{ marginRight: '5px' }} onClick={() => handleButtonClick('forward')}>
                    <FontAwesomeIcon icon={faArrowUp} /> Forward
                </Button>
                <Button variant="primary" style={{ marginRight: '5px' }} onClick={() => handleButtonClick('backward')}>
                    <FontAwesomeIcon icon={faArrowDown} /> Backward
                </Button>
                <Button variant="primary" style={{ marginRight: '5px' }} onClick={() => handleButtonClick('right')}>
                    <FontAwesomeIcon icon={faArrowRight} /> Right
                </Button>
                <Button variant="primary" onClick={() => handleButtonClick('left')}>
                    <FontAwesomeIcon icon={faArrowLeft} /> Left
                </Button>
            </div>
        </div>
    );
}