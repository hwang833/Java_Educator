package com.ztcollins.JavaEducator.UnitTests;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;

import com.ztcollins.JavaEducator.robotFunctions.RobotSimulation;
import com.ztcollins.JavaEducator.robotFunctions.Robot_test_impl;

public class TestrobotCodeU {

    private Robot_test_impl robotTestImpl;
    private char[][] testMaze = {
        {'W', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}, 
        {'W', 'R', 'O', 'W', 'W', 'W', 'W', 'O', 'O', 'O', 'W', 'O'},
        {'W', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'W', 'O', 'W', 'O'}, 
        {'W', 'W', 'O', 'O', 'O', 'O', 'O', 'O', 'W', 'O', 'O', 'O'}, 
        {'W', 'W', 'W', 'W', 'O', 'O', 'O', 'O', 'W', 'O', 'O', 'W'}, 
        {'O', 'O', 'O', 'O', 'W', 'O', 'W', 'W', 'W', 'W', 'G', 'O'}, 
        {'W', 'W', 'G', 'O', 'O', 'O', 'O', 'G', 'W', 'O', 'O', 'W'}, 
        {'W', 'W', 'W', 'O', 'O', 'W', '0', 'O', 'O', 'W', 'O', 'O'}, 
        {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'W', 'O', 'O'}, 
        {'O', 'W', 'O', 'O', 'O', 'O', 'O', 'O', 'W', 'O', 'O', 'W'}, 
        {'O', 'O', 'O', 'O', 'W', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}
    };
    @Before
    public void setUp() {
        robotTestImpl = new Robot_test_impl(1,1,"RIGHT",testMaze);
        // Set up your mocks here if necessary
    }

    @Test
    public void testTestRobotCode() throws IOException {
        File testJavaFile = createTestJavaFile();
        robotTestImpl = new Robot_test_impl(1,1,"RIGHT",testMaze);
        // Test the robot code
        RobotSimulation simulation = robotTestImpl.testRobotCode(testJavaFile);
        assertNotNull("Simulation should not be null", simulation);
        assertNotNull("Sequence of moves should not be null", simulation.getSequenceOfMoves());
        assertFalse("Sequence of moves should not be empty", simulation.getSequenceOfMoves().isEmpty());
        assertEquals("First move should be 'moveForward'", "moveForward", simulation.getSequenceOfMoves().get(1));
        simulation.getSequenceOfMoves().forEach(System.out::println);
        //TODO: Add test for x and y
        // Add more assertions based on expected behavior

        // Clean up
        // Get the moves from the simulation
        int[][] moves = simulation.getMoves();
        //System.out.println(moves);

    // Assuming the initial position is (0,0) and moveForward increments y
        int initialX = 2;
        int initialY = 2;
        assertEquals("Should be 100 moves", 100, simulation.getTotalMoves());
        for (int i = 1; i < simulation.getTotalMoves(); i++) {
        // Check if x remains the same and y is incremented
            //System.out.println(i);
            assertEquals("X position should remain constant", 2, moves[i][0]);
            assertEquals("Y position should be incremented 1 then stop", 1, moves[i][1]);
        }
        testJavaFile.delete();
    }

    private File createTestJavaFile() throws IOException {
        // Create a temporary directory
        File tempDir = Files.createTempDirectory("tempDir").toFile();
        // Create a file named RobotCode.java in that directory
        File tempFile = new File(tempDir, "RobotCode.java");
        // write the temp file
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("public class RobotCode {\n");
            writer.write("    public String getNextMove(int x, int y) {\n");
            writer.write("        String move = \"moveForward\";\n"); 
            writer.write("        return move;\n"); // Simplified logic for testing
            writer.write("    }\n");
            writer.write("}\n");
        }
        return tempFile;
    }
}