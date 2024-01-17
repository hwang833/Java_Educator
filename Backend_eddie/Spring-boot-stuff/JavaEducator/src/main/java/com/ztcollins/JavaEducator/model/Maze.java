package com.ztcollins.JavaEducator.model;


import java.util.Optional;

import jakarta.persistence.*;

/**
 * The Maze entity represents a maze with a specific difficulty level and layout.
 * It is used to store maze data in the database.
 */
@Entity
public class Maze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String difficulty;

    private int x;
    private int y;
    
    private String direction;

    @Column(length = 8000)
    private String mazeData; // Maze data stored as a JSON string or serialized form

    /**
     * Gets the unique identifier for the maze.
     *
     * @return the unique identifier of this maze
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the maze.
     *
     * @param id the new unique identifier for this maze
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the difficulty level of the maze.
     *
     * @return the difficulty level of the maze
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of the maze.
     *
     * @param difficulty the new difficulty level for this maze
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the maze data as a string. The data is usually stored in a serialized
     * format such as JSON and needs to be deserialized for use.
     *
     * @return the string representation of the maze data
     */
    public String getMazeData() {
        return mazeData;
    }

    /**
     * Sets the maze data. The data should be in a serialized format such as JSON.
     *
     * @param mazeData the new maze data in a serialized format
     */
    public void setMazeData(String mazeData) {
        this.mazeData = mazeData;
    }

    /**
     * TODOS
     *
     * @return the  level of the maze
     */
    public String getDirection() {
        return direction;
    }

    /**
     * TODO
     *
     * @param difficulty the new difficulty level for this maze
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

        /**
     * Gets the difficulty level of the maze.
     *
     * @return the difficulty level of the maze
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the difficulty level of the maze.
     *
     * @param difficulty the new difficulty level for this maze
     */
    public void setX(int x) {
        this.x = x;
    }

            /**
     * Gets the difficulty level of the maze.
     *
     * @return the difficulty level of the maze
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the difficulty level of the maze.
     *
     * @param difficulty the new difficulty level for this maze
     */
    public void setY(int y) {
        this.y = y;
    }
}