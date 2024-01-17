package com.ztcollins.JavaEducator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztcollins.JavaEducator.model.Maze;
import com.ztcollins.JavaEducator.robotFunctions.ExecutionFeedback;
import com.ztcollins.JavaEducator.robotFunctions.RobotSimulation;
import com.ztcollins.JavaEducator.robotFunctions.Robot_test_impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import java.io.FileWriter;
import java.io.IOException;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class TextController {

    // create new Robot_test_impl onbject
    @Autowired
    private Robot_test_impl robotTestImpl;

    @Autowired
    MazeController mazeInstance;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/uploadtext")

    public ResponseEntity<String> processText(@RequestParam("text") String text,
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

            // debug
            System.out.println(Arrays.deepToString(mazeData));

            System.out.println("Received and processed text: " + text);

            // response.put("message", "Received and processed text: " + text);
            // // Return an HTTP response with the response map in the body
            // return ResponseEntity.ok(response);

            String uploadDir = "file/";

            // Ensure the folder exists; create if it doesn't
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique file name (you may use a different approach)
            // String fileName = "GeneratedFile" + System.currentTimeMillis() + ".java";
            String fileName = "RobotCode.java";

            // Build the target file path to save the file in the "file" folder
            Path targetPath = uploadPath.resolve(fileName);

            // Create Java file and write the text content
            createJavaFile(targetPath.toFile(), text);

            // // create new Robot_test_impl object
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

            System.out.println("Execution Feedback: " + feedback.getFeedbackMessage());

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", feedback.isSuccess());
            responseMap.put("feedbackMessage", feedback.getFeedbackMessage());
            responseMap.put("moves", robotSimulation.getMoves());
            responseMap.put("totalMoves", robotSimulation.getTotalMoves());
            responseMap.put("sequenceOfMoves", robotSimulation.getSequenceOfMoves());
            responseMap.put("score", score);

            // Translate map to JSON string type
            ObjectMapper responseObjectMapper = new ObjectMapper();
            String jsonResponse = responseObjectMapper.writeValueAsString(responseMap);

            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            // If an exception occurs, return an error message
            return ResponseEntity.status(500).body("Text submitted and execution failed: " + e.getMessage());
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

    // Utility method to create a Java file with given content
    private void createJavaFile(File file, String content) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            // change \n to real lineSeparator
            content = content.replace("\n", System.lineSeparator());
            writer.write(content);
        }
    }
}