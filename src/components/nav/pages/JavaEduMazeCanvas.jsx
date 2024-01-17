import { useEffect, useRef } from "react";

/**
 * Maze GUI component utilizing React Canvas
 * @param {Ref} mazeDataRef  the Ref where maze layout data will be provided
 * @param {String} operationMode  a string that determines the operation mode, either "KeyboardControl" or "ProgramControl"
 * @param {Ref} robotPathDataRef  the Ref where robot move data will be provided
 * @param {Ref} callbackRef  the Ref storing the callback function for external input methods
 * @param  {...any} rest 
 * @returns 
 */
export default function Canvas(mazeDataRef, operationMode, robotPathDataRef, callbackRef,  ...rest) {

    const canvasRef = useRef(null)
    let canvas //canvas object
    let ci //canvas 2d context
    let width, height //dimentions of maze in tiles

    /**
     * canvas initiallization once it is mounted
     */
    useEffect(() => {
        canvas = canvasRef.current
        canvas.setAttribute("width", 500) //values can be set as needed
        canvas.setAttribute("height", 500)
        ci = canvas.getContext('2d');

        let animID = window.requestAnimationFrame(update);
        document.addEventListener("keydown", keyListener);
        callbackRef.current = keyListener;

        return () => {
            window.cancelAnimationFrame(animID);
            document.removeEventListener("keydown", keyListener);
            callbackRef.current = null;
        }
    }, [])

    let currentState = "";
    /**
     * Update loop
     * @param {int} t  time since page initialization in ms 
     */
    let mazeData; //2d array of maze layout
    let robotParams = null; //robot position and orientation data
    let robotPathData; //array of robotParams states along movement path
    let lastUpdate = 0; //stores the timestamp of the most recent update
    function update(t) {
        if (mazeDataRef.current != null) { //reset
            console.log(mazeDataRef.current)
            mazeData = eval(mazeDataRef.current.mazeData);
            robotParams = [parseInt(mazeDataRef.current.x), parseInt(mazeDataRef.current.y), (["UP", "RIGHT", "DOWN", "LEFT"]).indexOf(mazeDataRef.current.direction)];
            mazeDataRef.current = null;
            robotPathData = null;
            animStartT = -maxAnimDT;
            animTo = null;
            height = mazeData.length
            width = mazeData[0].length
            if (operationMode == "KeyboardControl") {
                currentState = "KeyboardControlOperation";
            } else if (operationMode == "ProgramControl") {
                currentState = "ProgramControlDataWait";
            }
        }

        switch (currentState) {
            case "ProgramControlDataWait":
                if (robotPathDataRef.current != null) {
                    robotPathData = robotPathDataRef.current;
                    robotPathDataRef.current = null;
                    robotPathData.unshift(robotParams); //robotPathData[0] is assumed to be the position after the first move
                    index = moveSpeed = lastUpdate = 0;
                    currentState = "ProgramControlOperation";
                }
                break;
            case "KeyboardControlOperation":

                break;
            case "ProgramControlOperation":
                codeControlUpdate(t);
				break;
            default:

        }
        render(t);
        window.requestAnimationFrame(update);
    }

    /**
     * Accepts user input directly from keyboard and via HTML Buttons
     * @param {KeyboardEvent} e
     */
    let index = 0; //where in the dataBuf array the current playback is
    let moveSpeed = 0; //how many indexes to advance per second
    function keyListener(e) {
        switch (currentState) {
            case "KeyboardControlOperation":
                keyboardControlListener(e)
                break;
            case "ProgramControlOperation":
                codeControlListener(e);
                break;
            default: 

        }
    }


    /**
     * Accepts user input to control code playback
     * @param {KeyboardEvent} e  An object with a .key parameter
     */
    function codeControlListener(e) {
        switch (e.key) {
            case "1":
                index = 0;
                moveSpeed = 0;
                animStartT = -maxAnimDT;
                break;
            case "2":
                moveSpeed = -10;
                break;
            case "3":
                index--;
                moveSpeed = 0;
                break;
            case "4":
                if (moveSpeed == 0) {
                    moveSpeed = 2; //play-pause dual function
                } else {
                    moveSpeed = 0;
                }
                break;
            case "5":
                index++;
                moveSpeed = 0;
                break;
            case "6":
                moveSpeed = 10;
                break;
            case "7":
                index = robotPathData.length - 1;
                moveSpeed = 0;
                animStartT = -maxAnimDT;
                break;
            
        }
    }

    /**
     * Update loop for code playback
     * @param {int} t  time since page initialization in ms 
     */
    function codeControlUpdate(t) {
        if (moveSpeed != 0 && t - lastUpdate >= 1000 / Math.abs(moveSpeed)) { //check elapsed time 
            lastUpdate = t;
            index += Math.sign(moveSpeed);
        }
        if (index < 0 || index >= robotPathData.length) { //index bounds check
            moveSpeed = 0; 
            if (index == -1) { 
                index++; 
            } else { 
                index--; 
            }
        }
        robotParams = robotPathData[index];

    }

    /**
     * Accepts user input to move the robot directly
     * @param {KeyboardEvent} e  An object with a .key parameter
     */
    function keyboardControlListener(e) {
        let targTileX = robotParams[0]; //used to hold new position pre-validation
        let targTileY = robotParams[1];
        switch (e.key) { //tank controls
            case "ArrowUp":
            case "w":
                if (robotParams[2] % 2 == 0) { //adjust position based on rotation w/math
                    targTileY += robotParams[2] - 1;
                } else {
                    targTileX -= robotParams[2] - 2;
                }
                break;
            case "ArrowDown":
            case "s":
                if (robotParams[2] % 2 == 0) {
                    targTileY -= robotParams[2] - 1;
                } else {
                    targTileX += robotParams[2] - 2;
                }
                break;
            case "ArrowLeft":
            case "a":
                robotParams[2] = robotParams[2] == 0 ? 3 : robotParams[2] - 1;
                break;
            case "ArrowRight":
            case "d":
                robotParams[2]++;
                robotParams[2] %= 4;
                break;
        }
        if (0 <= targTileX && targTileX < width && 
                    0 <= targTileY && targTileY < height && 
                    mazeData[targTileY][targTileX] != "W") { //validate target and update if valid
            robotParams[0] = targTileX;
            robotParams[1] = targTileY;
        }
    }

    /**
     * handles all graphical rendering tasks, should be run every frame
     */
    const maxAnimDT = 3000
    let animDT = 500; //time in ms for animation to complete
    let animStartT = -maxAnimDT;
    let animFrom;
    let animTo = null;
    let animParams;
    function render(t) {
        clearCanvas();
        if (mazeData == null) {
            drawWaiting();
            return;
        }
        if (currentState == "ProgramControlDataWait") {
            drawDataWait(t);
            return;
        }
        drawMaze();
        if (robotParams == null) { return; } //shouldn't happen unless mazeData has no starting point
        if (robotParams[0] < 0) {
            drawError();
            return;
        }

        if (animTo == null) { //initialize
            animTo = [...robotParams];
            if (operationMode == "KeyboardControl") {
                animDT = 200;
            }
        }
        if (Math.abs(moveSpeed) * animDT > 1000) { //skip animations on high movespeeds
            animStartT = -maxAnimDT;
        }
        if (JSON.stringify(robotParams) == JSON.stringify(animTo)) { 
            if (t - animStartT >= animDT) {
                animParams = animTo; //animation is finished
            } else {
                for (let i = 0; i < 3; i++) {
                    animParams[i] = animFrom[i] + ((animTo[i] - animFrom[i]) * (t - animStartT) / animDT); //LERP
                }
            }
        } else { //set up new animation
            animFrom = [...animTo]; //must be a deep copy bc ref is given to animParams
            animTo = [...robotParams];
            animStartT = t;
            if (Math.abs(animFrom[2] - animTo[2]) == 3) { //offset animFrom to prevent strange animations due to modulo behaviour
                animFrom[2] = animFrom[2] == 3 ? -1 : 4; 
            }
        }
        
        ci.save()
        ci.scale(canvas.width/width, canvas.height/height);
        ci.translate(animParams[0] + 0.5, animParams[1] + 0.5);
        ci.rotate(animParams[2] * Math.PI / 2);
        ci.translate(-0.5, -0.5);
        drawRobot();
        ci.restore();
    }


    //should have no transforms
    function clearCanvas() {
        ci.reset();
        ci.beginPath();
        ci.fillStyle = "white";
        ci.fillRect(0, 0, canvas.width, canvas.height); //clear
        ci.lineWidth = 2;
        ci.strokeStyle = "black"; 
        ci.strokeRect(0, 0, canvas.width, canvas.height); //border
    }
    function drawWaiting() {
        ci.lineWidth = 4;
        ci.strokeStyle = "black";
        ci.strokeRect(0, 0, canvas.width, canvas.height);
        ci.scale(canvas.width, canvas.height);
        ci.font = "0.05px Consolas";
        ci.fillStyle = "black";
        ci.fillText("Waiting for maze data...", 0.05, 0.1);
    }
    function drawDataWait(t) {
        ci.save()
        ci.globalAlpha = 0.6;
        drawMaze();
        ci.globalAlpha = 1;
        ci.scale(canvas.width, canvas.height);
        ci.font = "0.05px Consolas";
        ci.fillStyle = "black";
        ci.fillText("Waiting for program to finish...", 0.05, 0.1);
        ci.translate(0.5, 0.5);
        ci.scale(2/width, 2/height);
        ci.rotate(t / 100); //tweakable to control load spinner speed
        ci.translate(-0.5, -0.5);
        drawInactiveRobot();
        ci.restore();
    }
    function drawMaze() {
        ci.save()
        ci.scale(canvas.width/width, canvas.height/height);
        for (let y = 0; y < height; y++) {
            for (let x = 0; x < width; x++) {
                ci.save();
                ci.translate(x, y);
                switch (mazeData[y][x]) {
                    case "W":
                        drawWall();
                        break;
                    case "G":
                        drawGoal();
                        break;
                    case "R":
                        if (!robotParams) {
                            robotParams = [x, y, 0];
                        }
                    case "O":
                        drawEmpty();
                        break;
                }
                ci.restore();
            }
        }
        ci.restore()
    }
    function drawError() {
        ci.save();
        ci.scale(canvas.width, canvas.height);
        ci.globalAlpha = 0.7;
        ci.lineWidth = 0.01;
        ci.strokeStyle = 'rgb(255,32,32)';
        ci.fillStyle = 'rgb(224,192,192)';
        ci.fillRect(0.1, 0.2, 0.8, 0.6);
        ci.strokeRect(0.1, 0.2, 0.8, 0.6);
        ci.beginPath();
        ci.font = "0.04px Consolas";
        ci.fillStyle = "black";
        const errorMessages = ["Program Runtime Error:", "Program Took Too Long!", "Program Exceeds Move Limit!",]
        ci.fillText(errorMessages[-1 - robotParams[0]], 0.15, 0.3);
        ci.font = "0.03px Consolas"; 
        let tempText = robotParams[1].toString().split(/\r\n|\r|\n/);
        for (let i = 0; i < tempText.length; i++) {
            ci.fillText(tempText[i], 0.2, i * 0.04 + 0.35); 
        }
        ci.restore();
    }

    //can be modified as needed | target area is (0,0) - (1,1) | pretransform as needed!
    /** -- This header pertains to all the following functions! --
     * draws on the area (0,0) to (1,1), may mutate settings
     */
    function drawWall() {
        ci.beginPath();
        ci.fillStyle = `rgb(200,200,200)`;
        ci.fillRect(0.1, 0.1, 0.8, 0.8);
        ci.lineWidth = 0.05;
        ci.strokeStyle = `rgb(255,255,255)`;
        ci.beginPath();
        ci.strokeRect(0.2, 0.2, 0.6, 0.6);
    }
    function drawGoal() {
        drawEmpty();
        ci.beginPath();
        ci.strokeStyle = `rgb(0,0,0)`;
        ci.fillStyle = `rgb(255,0,0)`;
        ci.lineWidth = 0.05;
        ci.moveTo(0.4, 0.8);
        ci.lineTo(0.4, 0.2);
        ci.stroke();
        ci.beginPath();
        ci.moveTo(0.4, 0.2);
        ci.lineTo(0.4, 0.4);
        ci.lineTo(0.7, 0.3);
        ci.closePath();
        ci.fill();
    }
    function drawEmpty() {
        ci.beginPath();
        ci.fillStyle = `rgb(255,255,255)`;
        ci.fillRect(0.05, 0.05, 0.9, 0.9);
    }
    function drawRobot() { //should be facing north (towards negative Y)
        ci.beginPath();
        ci.fillStyle = `rgb(0,0,255)`;
        ci.moveTo(0.15, 0.8);
        ci.lineTo(0.5, 0.1);
        ci.lineTo(0.85, 0.8);
        ci.closePath();
        ci.fill();
    }
    function drawInactiveRobot() {
        ci.beginPath();
        ci.lineWidth = 0.05;
        ci.strokeStyle = `rgb(63,63,63)`;
        ci.moveTo(0.15, 0.8);
        ci.lineTo(0.5, 0.1);
        ci.lineTo(0.85, 0.8);
        ci.closePath();
        ci.stroke();
    }


    return <canvas ref={canvasRef}/>
}
