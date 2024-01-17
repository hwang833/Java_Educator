package com.ztcollins.JavaEducator.controller;

// import org.junit.Assert;
// import org.junit.Before;
// import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztcollins.JavaEducator.model.Maze;
import com.ztcollins.JavaEducator.robotFunctions.ExecutionFeedback;
import com.ztcollins.JavaEducator.robotFunctions.RobotSimulation;
import com.ztcollins.JavaEducator.robotFunctions.Robot_test_impl;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;

/*
 * FileController class handles file upload requests. 
 * That is to say, accept the file uploaded by the front-end and store it in a local folder.
 */
@RestController
@CrossOrigin("http://localhost:5173")
public class FileController {

    // private Robot_test_impl robotTestImpl;

    @Autowired
    MazeController mazeInstance;

    // public FileController(Robot_test_impl robotTestImpl) {
    // this.robotTestImpl = robotTestImpl;
    // }
    @Autowired
    private Robot_test_impl robotTestImpl;

    // Enable Cross-Origin Resource Sharing (CORS) for requests from
    // http://localhost:5174

    /*
     * Handle HTTP POST requests to "/uploadfile"
     * 
     * @param the file uploaded from front-end
     * 
     * @return a response map with a message.
     */

    @PostMapping("/uploadfile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("difficulty") String difficulty) {
        try {
            System.out.println("Difficulty: " + difficulty);

            // get the maze
            Maze recievedMaze = mazeInstance.getMazeBackend(difficulty);

            ObjectMapper objectMapper = new ObjectMapper();
            char[][] mazeData = objectMapper.readValue(recievedMaze.getMazeData(), char[][].class);

            int x = recievedMaze.getX();
            int y = recievedMaze.getY();
            String direction = recievedMaze.getDirection();

            // Find current 'R' position and update the maze
            boolean foundR = false;
            for (int i = 0; i < mazeData.length; i++) {
                for (int j = 0; j < mazeData[i].length; j++) {
                    if (mazeData[i][j] == 'R') {
                        mazeData[i][j] = 'O'; // Change current 'R' to 'O'
                        foundR = true;
                        break;
                    }
                }
                if (foundR) {
                    break;
                }
            }

            // Set the new 'R' position
            mazeData[x][y] = 'R';
            // System.out.println("x: " + x);
            // System.out.println("y: " + y);
            // System.out.println("Direction: " + direction);

            // debug
            System.out.println(Arrays.deepToString(mazeData));
            // Specify the relative path for the "file" folder in your Spring Boot project
            String uploadDir = "file/";

            // Ensure the folder exists; create if it doesn't
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Get the original file name of the uploaded file
            String originalFileName = file.getOriginalFilename();

            // Build the target file path to save the file in the "file" folder
            Path targetPath = uploadPath.resolve(originalFileName);

            // Copy the uploaded file to the target file path
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File uploaded successfully: " + originalFileName);
            // // Return a success message with the file name
            // return ResponseEntity.ok("File uploaded successfully: " + originalFileName);

            // create new Robot_test_impl object
            // Robot_test_impl newRobotTestImpl = new Robot_test_impl();

            Robot_test_impl localRobotTestImpl = new Robot_test_impl(x, y, direction, mazeData);
            // run Robot_test_impl to handle newly uploaded file
            ExecutionFeedback feedback = localRobotTestImpl.submitRobotCode(targetPath.toFile());
            RobotSimulation robotSimulation = localRobotTestImpl.testRobotCode(targetPath.toFile());

            // Print the results
            int[][] moves = robotSimulation.getMoves();

            int totalMoves = robotSimulation.getTotalMoves();

            List<String> sequenceOfMoves = robotSimulation.getSequenceOfMoves();

            int mutiplier = Multiplier(difficulty);
            int score = (100 - totalMoves) * mutiplier;

            System.out.println("Moves: " + Arrays.deepToString(moves));
            System.out.println("Total Moves: " + totalMoves);
            System.out.println("Sequence of Moves: " + sequenceOfMoves);

            // assertTrue(feedback.isSuccess());
            // assertNotNull(feedback.getFeedbackMessage());
            // assertTrue(feedback.getNumberOfMoves() > 0);

            // if (feedback.isSuccess()) {
            // return ResponseEntity.ok("File uploaded and executed successfully. " +
            // feedback.getFeedbackMessage());
            // } else {
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            // .body("File uploaded successfully, but execution failed: " +
            // feedback.getFeedbackMessage());
            // }
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", feedback.isSuccess());
            responseMap.put("feedbackMessage", feedback.getFeedbackMessage());
            responseMap.put("moves", robotSimulation.getMoves());
            responseMap.put("totalMoves", robotSimulation.getTotalMoves());
            responseMap.put("sequenceOfMoves", robotSimulation.getSequenceOfMoves());
            responseMap.put("score", score);

            // translate map to JSON string type
            ObjectMapper reponse_objectMapper = new ObjectMapper();
            String jsonResponse = reponse_objectMapper.writeValueAsString(responseMap);

            return ResponseEntity.ok(jsonResponse);

        } catch (Exception e) {
            // If an exception occurs, return an error message
            return ResponseEntity.status(500).body("File upload and execution failed: " + e.getMessage());
        }
    }

    private int Multiplier(String difficulties) {
        int mutiplier;
        switch (difficulties) {
            case "Easy":
                mutiplier = 1;
                break;
            case "Hard":
                mutiplier = 2;
                break;
            default:
                mutiplier = 0;
                break;
        }
        return mutiplier;
    }
}
