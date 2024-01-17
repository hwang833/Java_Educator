import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;


public class Robot_test_impl implements Robot_test {

    private EducatorImpl educator;
    private int numberOfMoves = 0;
    private static final int MAX_MOVES = 100; // Limit to prevent infinite loops
    
    public Robot_test_impl() {
        this.educator = new EducatorImpl();
    }

    private boolean compileJavaFile(File javaFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        return compiler.run(null, null, null, javaFile.getPath()) == 0;
    }

    private String extractClassName(File javaFile) {
        String fileName = javaFile.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileName = fileName.substring(0, dotIndex);
        }
        return fileName;
    }
    

    @Override
    public ExecutionFeedback submitRobotCode(File codeFile) {
        // Compile the user's Java file
        boolean compilationSuccess = compileJavaFile(codeFile);
        if (!compilationSuccess) {
            return new ExecutionFeedback(false, "Compilation failed.",getNumberOfMoves());
        }

        // Assuming the user's class name is known or can be determined
        String userClassName = extractClassName(codeFile);

        // Test the robot code
        RobotSimulation simulation = testRobotCode(codeFile);
        // Process the results and provide feedback
        // ...

        return new ExecutionFeedback(true, "Execution successful. Moves: " + getNumberOfMoves(), getNumberOfMoves());
    }



    @Override
    public RobotSimulation testRobotCode(File codeFile) {
        // Compile the user's Java file
        if (!compileJavaFile(codeFile)) {
            // Handle compilation failure
            return null;
        }
        int[][] moves = new int[MAX_MOVES][3]; // Array to store moves
        try {
            // Dynamically load the user's class
            URL classUrl = codeFile.getParentFile().toURI().toURL();
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
            String className = extractClassName(codeFile); // Extract class name from file
            Class<?> userClass = Class.forName(className, true, classLoader);
            Object userInstance = userClass.getDeclaredConstructor().newInstance();
            Method getNextMove = userClass.getMethod("getNextMove", int.class, int.class);

            while (!isGoalReached() && numberOfMoves < MAX_MOVES) {
                // Get the robot's current position
                int x = educator.getCurrentMazeState().getRobotX();
                int y = educator.getCurrentMazeState().getRobotY();

                // Call user's getNextMove method
                String move = (String) getNextMove.invoke(userInstance, x, y);

                // Execute the move on the robot
                executeRobotMove(move);
                moves[numberOfMoves][0] = x;
                moves[numberOfMoves][1] = y;
                int currentDirection = educator.getRobot().getDirections();
                moves[numberOfMoves][2] = currentDirection;
                numberOfMoves++;
                if (numberOfMoves > MAX_MOVES) {
                    break; // Prevent infinite loop
                }
            }

            return new RobotSimulation(moves, numberOfMoves);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isGoalReached() {
        // Implement logic to check if the robot has reached the goal
        // Check the robot's position against the goal position in the maze
        int robotX = educator.getCurrentMazeState().getRobotX();
        int robotY = educator.getCurrentMazeState().getRobotY();
        char currentCell = educator.getCurrentMazeState().getMazeLayout()[robotX][robotY];
        return currentCell == 'G'; // Assuming 'G' represents the goal
    }

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


