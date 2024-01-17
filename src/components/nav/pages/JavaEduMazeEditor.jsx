import { Link } from "react-router-dom";
import { useEffect, useRef,useState } from "react";
import CanvasEditor from "./JavaEduMazeCanvasEditor";
import { Col, Row, Card, Button, Form } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowUp, faArrowDown, faArrowRight, faArrowLeft } from "@fortawesome/free-solid-svg-icons";


export default function JavaEduMazeSandbox(props) {

    const [mazeArray, setMazeArray] = useState([]);

    let callbackRef = useRef(null);

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
            <h2>Maze Editor</h2> 
            <p>Use wasd or arrow keys and Enter, or Mouse</p>
            <p>
                <Link to="/">Back to home page.</Link>
            </p>
            <p>
                <Link to="/mazeprogram">Go to Program Mode</Link>
            </p>
            <p>
                <Link to="/mazesandbox">Go to Sandbox Mode</Link>
            </p>
            
            
            {CanvasEditor({"width":20, "height":20}, callbackRef)}


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