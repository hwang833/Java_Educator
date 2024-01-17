package com.ztcollins.JavaEducator.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.ztcollins.JavaEducator.model.Maze;

/**
 * This class contains unit tests for the Maze class, testing its getter and setter methods.
 */
class MazeTest {

    private Maze maze;

    /**
     * Sets up a fresh instance of Maze before each test.
     */
    @BeforeEach
    void setUp() {
        maze = new Maze();
    }

    /**
     * Tests whether the ID value set using setId is correctly retrieved by getId.
     */
    @Test
    void testId() {
        Long expectedId = 1L;
        maze.setId(expectedId);
        assertEquals(expectedId, maze.getId());
    }

    /**
     * Tests whether the difficulty value set using setDifficulty is correctly retrieved by getDifficulty.
     */
    @Test
    void testDifficulty() {
        String expectedDifficulty = "medium";
        maze.setDifficulty(expectedDifficulty);
        assertEquals(expectedDifficulty, maze.getDifficulty());
    }

    /**
     * Tests whether the maze data string set using setMazeData is correctly retrieved by getMazeData.
     * The maze data is assumed to be in a serialized string format.
     */
    @Test
    void testMazeData() {
        String expectedMazeData = "[['W', 'O'], ['O', 'W']]"; // Example serialized maze data
        maze.setMazeData(expectedMazeData);
        assertEquals(expectedMazeData, maze.getMazeData());
    }
}
