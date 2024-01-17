package com.ztcollins.JavaEducator.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ztcollins.JavaEducator.model.Maze;
import com.ztcollins.JavaEducator.repository.MazeRepository;

import aj.org.objectweb.asm.TypeReference;

@RestController
public class MazeController {

    @Autowired
    private MazeRepository mazeRepository;

    /**
     * Endpoint to retrieve a maze of a specified difficulty.
     * 
     * @param difficulty The difficulty level of the maze to retrieve.
     * @return ResponseEntity containing the maze data or an error if the difficulty is invalid.
     */
    @CrossOrigin("http://localhost:5173")
    @GetMapping("/maze/{difficulty}")
    public ResponseEntity<Map<String,String>> getMaze(@PathVariable String difficulty) {


        Maze mazeFromDB = mazeRepository.findByDifficulty(difficulty);
        HashMap<String, String> map = new HashMap<>();

        try {
            map.put("mazeData", mazeFromDB.getMazeData());
            map.put("x", Integer.toString(mazeFromDB.getX()));
            map.put("y", Integer.toString(mazeFromDB.getY()));
            map.put("direction", mazeFromDB.getDirection());
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            System.out.println(e);
            map.put("error", "An error occurred converting maze data to JSON");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    /**
     * This method retrieves corresponding Maze based on difficulty
     * @param difficulty unique identifier for each maze
     * @return maze associated with specified difficulty
     * @throws JsonProcessingException if difficulty cannot be processed
     */
    public Maze getMazeBackend(String difficulty)  throws JsonProcessingException {   
        return mazeRepository.findByDifficulty(difficulty);
    }


    /**
     * This method updates an existing maze in the database with new details.
     * This method finds an existing maze by ID and updates its data and difficulty level
     * with the provided mazeDetails. If the maze is not found, it returns a not found response.
     *
     * @param id the unique identifier of the maze to be updated.
     * @param mazeDetails Maze object containing the updated details.
     * @return ResponseEntity with the updated Maze object or a not found response.
     */
    @PutMapping("/update/{difficulty}")
    public ResponseEntity<Maze> updateMaze(@PathVariable String difficulty, @RequestBody Maze mazeDetails) {
        Maze mazeToUpdate = mazeRepository.findByDifficulty(difficulty);
        return mazeRepository.findById(mazeToUpdate.getId())
                .map(maze -> {
                    maze.setMazeData(mazeDetails.getMazeData());
                    maze.setDifficulty(mazeDetails.getDifficulty()); // Assuming you have a difficulty field
                    maze.setDirection(mazeDetails.getDirection());
                    maze.setX(mazeDetails.getX());
                    maze.setY(mazeDetails.getY());
                    Maze updatedMaze = mazeRepository.save(maze);
                    return ResponseEntity.ok(updatedMaze);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * This method creates maze to be saved to database
     * @param mazeDetails maze to be created and saved
     * @return String that signifies maze was created
     */
    @PostMapping("/createmaze")
    public String createMaze(@RequestBody Maze mazeDetails) {
        mazeRepository.save(mazeDetails);
        return "Maze Created!";
    }

    /**
     * This method deletes a maze from the database by its ID.
     * This method finds a maze by its ID and deletes it. If the maze is not found,
     * it returns a not found response.
     *
     * @param id the unique identifier of the maze to be deleted.
     * @return ResponseEntity indicating the result of the deletion operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaze(@PathVariable Long id) {
        
        return mazeRepository.findById(id)
                .map(maze -> {
                    mazeRepository.delete(maze);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * This method Retrieves all mazes stored in the database.
     * This method finds and returns all mazes stored in the database.
     *
     * @return ResponseEntity containing a list of all Maze objects stored in the database.
     */
    @GetMapping
    public ResponseEntity<List<Maze>> getAllMazes() {
        List<Maze> mazes = mazeRepository.findAll();
        return ResponseEntity.ok(mazes);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + exception.getMessage());
    }


}
