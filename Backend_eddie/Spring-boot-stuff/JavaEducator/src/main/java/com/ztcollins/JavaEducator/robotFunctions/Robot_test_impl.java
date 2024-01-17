package com.ztcollins.JavaEducator.robotFunctions;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Implementation of Robot_test interface, providing functionalities to compile,
 * load, and test robot movement code in a simulated environment. This class
 * supports dynamic compilation of user-submitted Java files and executes the
 * robot's movement logic within a maze simulation.
 */
@Service
public class Robot_test_impl implements Robot_test {

    private EducatorImpl educator;
    private int numberOfMoves = 0;
    private static final int MAX_MOVES = 100; // Limit to prevent infinite loops

    @Autowired
    public Robot_test_impl(
            @Value("${default.x}") int x,
            @Value("${default.y}") int y,
            @Value("${default.direction}") String direction,
            @Value("${default.mazeData}") char[][] mazeData) {

        this.educator = new EducatorImpl(x, y, direction, mazeData);

    }

    /**
     * Compiles a Java file using the system Java compiler.
     *
     * @param javaFile The Java file to be compiled.
     * @return true if compilation is successful, false otherwise.
     */
    private boolean compileJavaFile(File javaFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        return compiler.run(null, System.out, System.err, javaFile.getPath()) == 0;
    }

    /**
     * Extracts the class name from a Java file by removing the file extension.
     *
     * @param javaFile The Java file from which to extract the class name.
     * @return The class name without the .java extension.
     */
    private String extractClassName(File javaFile) {
        String fileName = javaFile.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        return fileName;
    }

    /**
     * Compiles and tests the robot movement code submitted by the user.
     * Provides feedback on execution success and the number of moves performed.
     *
     * @param codeFile The Java file containing the robot code.
     * @return ExecutionFeedback containing the result of the execution.
     */
    @Override
    public ExecutionFeedback submitRobotCode(File codeFile) {
        // Compile the user's Java file
        boolean compilationSuccess = compileJavaFile(codeFile);
        if (!compilationSuccess) {
            // return new ExecutionFeedback(false, "Compilation failed.",
            // getNumberOfMoves());
            return new ExecutionFeedback(false, "Compilation failed.");
        }

        // Assuming the user's class name is known or can be determined
        String userClassName = extractClassName(codeFile);

        // Test the robot code
        // RobotSimulation simulation = testRobotCode(codeFile);
        // Process the results and provide feedback
        // ...
        // return new ExecutionFeedback(true, "Execution successful. Moves: " +
        // getNumberOfMoves(), getNumberOfMoves());
        return new ExecutionFeedback(true, "Execution successful.");
    }

    /**
     * Tests the robot code by dynamically loading and executing it.
     * Tracks the robot's movements in a simulated environment.
     *
     * @param codeFile The Java file containing the robot code.
     * @return RobotSimulation with the results of the test.
     */
    @Override
    public RobotSimulation testRobotCode(File codeFile) {
        // Compile the user's Java file
        if (!compileJavaFile(codeFile)) {
            // Handle compilation failure
            return null;
        }
        long startTime = System.currentTimeMillis(); // Start time for timeout check
        int[][] moves = new int[MAX_MOVES + 1][3]; // Array to store moves
        List<String> sequenceOfMoves = new ArrayList<>();
        try {
            // Dynamically load the user's class
            URL classUrl = codeFile.getParentFile().toURI().toURL();
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { classUrl });
            String className = extractClassName(codeFile); // Extract class name from file
            Class<?> userClass = Class.forName(className, true, classLoader);
            Object userInstance = userClass.getDeclaredConstructor().newInstance();
            Method getNextMove = userClass.getMethod("getNextMove", int.class, int.class);
            int x = educator.getCurrentMazeState().getRobotX();
            int y = educator.getCurrentMazeState().getRobotY();
            while (!isGoalReached() && numberOfMoves < MAX_MOVES) {
                // TODO: Add total time out for movement
                if ((System.currentTimeMillis() - startTime) > 5000) {
                    moves[numberOfMoves][0] = -2; // Special marker for timeout
                    moves[numberOfMoves][1] = -2;
                    moves[numberOfMoves][2] = numberOfMoves;
                    return new RobotSimulation(moves, numberOfMoves, sequenceOfMoves);
                }
                // Get the robot's current position
                x = educator.getRobot().getRobotX();
                y = educator.getRobot().getRobotY();
                // Call user's getNextMove method
                String move = (String) getNextMove.invoke(userInstance, x, y);
                sequenceOfMoves.add(move);

                // Execute the move on the robot
                executeRobotMove(move);
                // Store the robot position and robot facing direction
                moves[numberOfMoves][0] = y;
                moves[numberOfMoves][1] = x;
                int currentDirection = educator.getRobot().getDirections();
                moves[numberOfMoves][2] = currentDirection;
                numberOfMoves++;
                if (numberOfMoves >= MAX_MOVES) {
                    moves[MAX_MOVES][0] = -3; // Special marker for max moves
                    moves[MAX_MOVES][1] = -3;
                    moves[MAX_MOVES][2] = MAX_MOVES;
                    return new RobotSimulation(moves, MAX_MOVES, sequenceOfMoves); // Prevent infinite loop
                }
            }
            // TODO: Add a score calculation

            // TODO: Add prompt for reach max move
            return new RobotSimulation(moves, numberOfMoves, sequenceOfMoves);

        } catch (Exception e) {
            moves[numberOfMoves][0] = -1; // Marker for runtime error
            moves[numberOfMoves][1] = -1;
            moves[numberOfMoves][2] = numberOfMoves;
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String errorTrace = sw.toString();

            // Return the moves made so far with the error trace
            return new RobotSimulation(moves, numberOfMoves, errorTrace);
        }
    }

    /**
     * 
     * @return
     */
    private boolean isGoalReached() {
        // Implement logic to check if the robot has reached the goal
        // Check the robot's position against the goal position in the maze
        int robotX = educator.getCurrentMazeState().getRobotX();
        int robotY = educator.getCurrentMazeState().getRobotY();
        char currentCell = educator.getCurrentMazeState().getMazeLayout()[robotX][robotY];
        return currentCell == 'G'; // Assuming 'G' represents the goal
    }

    /**
     * 
     * @param move
     */
    private void executeRobotMove(String move) {
        switch (move) {
            case "moveForward":
                educator.getRobot().moveForward();
                break;
            case "moveBackward":
                educator.getRobot().moveBackward();
                break;
            case "rotateLeft":
                educator.getRobot().rotateLeft();
                break;
            case "rotateRight":
                educator.getRobot().rotateRight();
                break;
            // Add more cases if needed
        }
    }

    @Override
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    // ... include methods for compileJavaFile, extractClassName, etc. ...
}
