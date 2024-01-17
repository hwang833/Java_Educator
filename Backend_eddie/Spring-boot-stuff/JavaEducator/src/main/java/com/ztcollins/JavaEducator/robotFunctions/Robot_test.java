package com.ztcollins.JavaEducator.robotFunctions;

import java.io.File;

public interface Robot_test {

    /**
     * Allows the user to submit their robot code for testing.
     * @param code The robot code provided by the user.
     * @return Feedback after running the code.
     */
    ExecutionFeedback submitRobotCode(File codeFile);

    /**
     * Allows the user to test their robot code and observe the robot's behavior in the maze.
     * This might involve some visualization or simulation of the robot's movements.
     * @param code The robot code provided by the user.
     * @return A visualization or representation of the robot's movements.
     */
    RobotSimulation testRobotCode(File codeFile);

    /**
     * Retrieves the number of moves the robot made during its attempt.
     * This can either be a part of ExecutionFeedback or be fetched separately.
     * @return The number of moves made by the robot.
     */
    int getNumberOfMoves();

    // ... any other related methods as needed.
}

// This can be a complex class representing the visualization, movements, current position, etc.
// Depending on the implementation details, you might want this to return more structured data, 
// or handle visualization differently.

