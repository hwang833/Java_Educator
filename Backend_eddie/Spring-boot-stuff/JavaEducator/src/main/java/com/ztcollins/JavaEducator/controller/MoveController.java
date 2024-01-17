package com.ztcollins.JavaEducator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class MoveController {
    private char[][] mazeMatrix = {
            {'W', 'W', 'W', 'W', 'W'},
            {'W', 'O', 'O', 'O', 'W'},
            {'W', 'O', 'R', 'G', 'W'},
            {'W', 'O', 'O', 'O', 'W'},
            {'W', 'W', 'W', 'W', 'W'}
    };

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/move")
    public ResponseEntity<char[][]> move(@RequestParam String direction) {
        int currentX = -1;
        int currentY = -1;

        // Find the current position of the robot
        for (int i = 0; i < mazeMatrix.length; i++) {
            for (int j = 0; j < mazeMatrix[i].length; j++) {
                if (mazeMatrix[i][j] == 'R') {
                    currentX = i;
                    currentY = j;
                }
            }
        }

        System.out.println("Received request for direction: " + direction);

        // Perform the corresponding action based on the direction
        // Update the robot's position
        if ("up".equals(direction) && currentX > 0 && mazeMatrix[currentX - 1][currentY] == 'O') {
            mazeMatrix[currentX][currentY] = 'O';
            mazeMatrix[currentX - 1][currentY] = 'R';
        } else if ("down".equals(direction) && currentX < mazeMatrix.length - 1 && mazeMatrix[currentX + 1][currentY] == 'O') {
            mazeMatrix[currentX][currentY] = 'O';
            mazeMatrix[currentX + 1][currentY] = 'R';
        } else if ("left".equals(direction) && currentY > 0 && mazeMatrix[currentX][currentY - 1] == 'O') {
            mazeMatrix[currentX][currentY] = 'O';
            mazeMatrix[currentX][currentY - 1] = 'R';
        } else if ("right".equals(direction) && currentY < mazeMatrix[currentX].length - 1 && mazeMatrix[currentX][currentY + 1] == 'O') {
            mazeMatrix[currentX][currentY] = 'O';
            mazeMatrix[currentX][currentY + 1] = 'R';
        }

        return ResponseEntity.ok(mazeMatrix);
    }
}

