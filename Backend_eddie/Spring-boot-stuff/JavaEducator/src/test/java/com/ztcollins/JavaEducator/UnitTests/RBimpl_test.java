package com.ztcollins.JavaEducator.UnitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import com.ztcollins.JavaEducator.robotFunctions.ExecutionFeedback;
import com.ztcollins.JavaEducator.robotFunctions.Robot_test_impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;


public class RBimpl_test {

    
    private Robot_test_impl robotTestImpl;
    private char[][] testMaze = {
        {'W', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}, 
        {'W', 'O', 'O', 'W', 'W', 'W', 'W', 'O', 'O', 'O', 'W', 'O'},
        {'W', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'W', 'O', 'W', 'O'}, 
        {'W', 'W', 'O', 'O', 'O', 'O', 'O', 'O', 'W', 'O', 'O', 'O'}, 
        {'W', 'W', 'W', 'W', 'O', 'O', 'O', 'O', 'W', 'O', 'O', 'W'}, 
        {'O', 'O', 'O', 'O', 'W', 'O', 'W', 'W', 'W', 'W', 'G', 'O'}, 
        {'W', 'W', 'G', 'O', 'O', 'O', 'O', 'G', 'W', 'O', 'O', 'W'}, 
        {'W', 'W', 'W', 'O', 'O', 'W', 'R', 'O', 'O', 'W', 'O', 'O'}, 
        {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'W', 'O', 'O'}, 
        {'O', 'W', 'O', 'O', 'O', 'O', 'O', 'O', 'W', 'O', 'O', 'W'}, 
        {'O', 'O', 'O', 'O', 'W', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}
    };

    @Test
    public void testSubmitRobotCode() throws IOException {
        // Create a temporary Java file with robot code
        File tempJavaFile = createTempJavaFileWithRobotCode();

        // Submit the file to the robot test implementation
        robotTestImpl = new Robot_test_impl(1,1,"RIGHT",testMaze);
        ExecutionFeedback feedback =  robotTestImpl.submitRobotCode(tempJavaFile);
        System.out.println(feedback.getFeedbackMessage());
        // Assert that the feedback indicates a successful execution
        assertTrue(feedback.isSuccess());
        assertNotNull(feedback.getFeedbackMessage());
        assertTrue(feedback.getNumberOfMoves() > 0);

        // Clean up the temporary file
        tempJavaFile.delete();
    }

    private File createTempJavaFileWithRobotCode() throws IOException {
        // Create a temporary directory
        File tempDir = Files.createTempDirectory("tempDir").toFile();
        
        // Create a file named RobotCode.java in that directory
        File tempFile = new File(tempDir, "RobotCode.java");
    
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("public class RobotCode {\n");
            writer.write("    public String getNextMove(int x, int y) {\n");
            writer.write("        return \"moveForward\";\n"); // Simplified logic for testing
            writer.write("    }\n");
            writer.write("}\n");
        }
        return tempFile;
    }
}
