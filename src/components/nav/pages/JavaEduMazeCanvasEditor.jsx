import { useEffect, useRef } from "react";

/**
 * Maze GUI component utilizing React Canvas
 * @param {Ref} mazeDataRef  the Ref where maze layout data will be provided
 * @param {Ref} callbackRef  the Ref storing the callback function for external input methods
 * @param  {...any} rest 
 * @returns 
 */
export default function CanvasEditor(mazeDataParams, callbackRef,  ...rest) {

    const canvasRef = useRef(null)
    let canvas //canvas object
    let ci //canvas 2d context
    let width = mazeDataParams.width;
    let height = mazeDataParams.height;
    let robotParams;
    
    /**
     * canvas initiallization once it is mounted
     */
    useEffect(() => {
        canvas = canvasRef.current
        canvas.setAttribute("width", Math.max(400, 500 * width / height)) //values can be set as needed
        canvas.setAttribute("height", 600)
        ci = canvas.getContext('2d');

        mazeData = Array(height).fill([]);
        for (let i = 0; i < width; i++) {
            mazeData[i] = Array(width).fill("O");
        }

        let animID = window.requestAnimationFrame(update);
        document.addEventListener("keydown", keyListener);
        document.addEventListener("mousedown", mouseDown);
        document.addEventListener("mousemove", mouseListener);
        document.addEventListener("mouseup", mouseUp);
        callbackRef.current = keyListener;

        return () => {
            window.cancelAnimationFrame(animID);
            document.removeEventListener("mousedown", mouseDown);
            document.removeEventListener("mousemove", mouseListener);
            document.removeEventListener("mouseup", mouseUp);
            callbackRef.current = null;
        }
    }, [])

    /**
     * Update loop
     * @param {int} t  time since page initialization in ms 
     */
    let mazeData; //2d array of maze layout
    function update(t) {
        render(t);
        window.requestAnimationFrame(update);
    }

    let mousedown = false;
    function mouseDown(e) {
        if (e.button == 0) {
            mousedown = true;
            let bounds = canvas.getBoundingClientRect();
            let mouseX = e.pageX - bounds.left - scrollX;
            let mouseY = e.pageY - bounds.top - scrollY;
            if (mouseX < 0 || mouseY < 0 || mouseX > canvas.width || mouseY > canvas.height) { 
                return;
            }
            if (mouseY < 100) {
                if (0 <= mouseX && mouseX < 400) {
                    selected = Math.floor(mouseX / 100);
                }
            } else {
                mouseY -= 100;
                mouseX /= canvas.width;
                mouseY /= (canvas.height - 100);
                mouseX = Math.min(Math.floor(mouseX * width), width);
                mouseY = Math.min(Math.floor(mouseY * height), height);
                cursor.x = mouseX;
                cursor.y = mouseY;
                mazeEdit(mouseX, mouseY);
            }
        }
    }
    function mouseUp(e) {
        if (e.button == 0) {
            mousedown = false;
        }
    }

    function mouseListener(e) {
        let bounds = canvas.getBoundingClientRect();
        let mouseX = e.pageX - bounds.left - scrollX;
        let mouseY = e.pageY - bounds.top - scrollY;
        if (mouseX < 0 || mouseY < 0 || mouseX > canvas.width || mouseY > canvas.height) { 
            return;
        }
        if (!mousedown) {
            return;
        }
        if (mouseY >= 100 && selected != 2) {
            mouseY -= 100;
            mouseX /= canvas.width;
            mouseY /= (canvas.height - 100);
            mouseX = Math.min(Math.floor(mouseX * width), width);
            mouseY = Math.min(Math.floor(mouseY * height), height);
            cursor.x = mouseX;
            cursor.y = mouseY;
            mazeEdit(mouseX, mouseY);
        }
    }

    /**
     * Accepts user input directly from keyboard and via HTML Buttons
     * @param {KeyboardEvent} e
     */
    let selected = 0; // 0 empty 1 wall 2 robot 3 goal
    let cursor = {"x": 0, "y": 0};
    function keyListener(e) {
        switch (e.key) {
            case "1":
            case "2":
            case "3":
            case "4":
                selected = parseInt(e.key) - 1;
                break;
            case "ArrowUp":
            case "w":
                cursor.y = Math.max(0, cursor.y - 1);
                break;
            case "ArrowDown":
            case "s": 
                cursor.y = Math.min(height - 1, cursor.y + 1);
                break;
            case "ArrowLeft":
            case "a":
                cursor.x = Math.max(0, cursor.x - 1);
                break;
            case "ArrowRight":
            case "d":
                cursor.x = Math.min(width - 1, cursor.x + 1);
                break;
            case "Enter":
            case " ":
                mazeEdit(cursor.x, cursor.y);
                break;
            case "p":
                let out = "{\"mazeData\": \"[";
                for (let r of mazeData) {
                    out += "\n["
                    for (let c of r) {
                        out += "\\\"" + c + "\\\",";
                    }
                    out = out.slice(0,-1);
                    out += "],"
                }
                out = out.slice(0, -1);
                out += "]\"\n"
                out += "\"x\":\"" + robotParams[0] + "\",\n"
                out += "\"y\":\"" + robotParams[1] + "\",\n"
                out += "\"direction\":\"" + (["UP", "RIGHT", "DOWN", "LEFT"])[robotParams[2]] + "\",\n"
                out += "\"difficulty\":\"" + "someName" + "\"\n}"
                console.log(out);
                break;
        }
    }

    function mazeEdit(x, y) {
        mazeData[y][x] = (["O", "W", "O", "G"])[selected];
        if (selected == 2) {
            if (robotParams != null && robotParams[0] == x && robotParams[1] == y) {
                robotParams[2] = (robotParams[2] + 1) % 4;
            } else {
                robotParams = [x, y, 0];
            }
        } else {
            if (robotParams != null && robotParams[0] == x && robotParams[1] == y) {
                robotParams = null;
            }
        }
    }

    /**
     * handles all graphical rendering tasks, should be run every frame
     */
    function render(t) {
        clearCanvas();
        drawHotbar();
        ci.save()
        ci.translate(0, 100);
        drawMaze();
        if (robotParams == null) { return; } 
        ci.save()
        ci.scale(canvas.width/width, (canvas.height - 100)/height);
        ci.translate(robotParams[0] + 0.5, robotParams[1] + 0.5);
        ci.rotate(robotParams[2] * Math.PI / 2);
        ci.translate(-0.5, -0.5);
        drawRobot();
        ci.restore();
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
    function drawHotbar() {
        ci.lineWidth = 2;
        ci.strokeStyle = `rgb(0,0,0)`
        ci.strokeRect(1, 1, canvas.width - 2, 98);
        for (let i = 0; i < 4; i++) {
            ci.save();
            ci.translate(15 + 100 * i, 15);
            ci.scale(70,70);
            ci.lineWidth = 0.01 + (selected == i ? 0.04 : 0);
            ci.strokeRect(0, 0, 1, 1);
            switch(i) {
                case 0:
                    drawEmpty();
                    break;
                case 1:
                    drawWall();
                    break;
                case 2: 
                    drawRobot();
                    break;
                case 3:
                    drawGoal();
                    break;
            }
            ci.restore();
        }
    }
    function drawMaze() {
        ci.save()
        ci.scale(canvas.width/width, (canvas.height - 100)/height);
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
                    case "O":
                        drawEmpty();
                        break;
                }
                if (cursor.x == x && cursor.y == y) {
                    ci.strokeStyle = `rgb(0,0,0)`;
                    ci.lineWidth = 0.03; 
                    ci.strokeRect(0,0,1,1);
                }
                ci.restore();
            }
        }
        ci.restore()
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
        ci.fillStyle = `rgb(240,240,240)`;
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


    return <canvas ref={canvasRef}/>
}
