
/**
 * The EducatorImpl class implements the Educator interface and serves as the core logic handler for the Maze Challenge game.
 * It encapsulates all the operations and states necessary for the maze navigation simulation.
 *
 * Fields:
 * - currentMode: An enum value that represents the current operating mode of the application (e.g., PRACTICE, CHALLENGE).
 * - robot: An instance of the Robot class that simulates the movements and actions of a robot navigating the maze.
 * - currentMazeState: An object representing the current state of the maze, including the robot's position and the maze's grid.
 * - keypressGUI: A GUI component that captures and handles keypress events.
 * - initialMaze: A 2D character array that holds the static layout of the maze.
 *
 * Constructor:
 * - EducatorImpl(): Initializes the maze state with the robot's starting position and sets the default operating mode.
 *
 **/
public class EducatorImpl implements Educator {
    
    private OperatingMode currentMode;
    private Robot robot;
    private MazeState currentMazeState;
    private Keypress keypressGUI;
    private char[][] initialMaze = {
            {'W', 'W', 'W', 'W', 'W'},
            {'W', 'O', 'O', 'O', 'W'},
            {'W', 'O', 'R', 'G', 'W'},
            {'W', 'O', 'O', 'O', 'W'},
            {'W', 'W', 'W', 'W', 'W'},
        };//exmaple Maze

    //TODO: initialize a user token
    /**
     * Constructor for EducatorImpl. Initializes the maze state and the robot's position,
     * and sets the operating mode to PRACTICE by default.
     */
    public EducatorImpl() {
        // Initialize the maze state with the initial maze and robot position
        MazeState mazeState = new MazeState(initialMaze, 2, 2); // Assuming the robot starts at position (2, 2)
        this.currentMazeState = mazeState;

        // Initialize the robot with the initial maze
        this.robot = new Robot(initialMaze);

        // Set a default operating mode, if applicable
        this.currentMode = OperatingMode.PRACTICE; 
        // Constructor, possibly initializing fields.
    }

    //this will move to front end
    /**
     * Provides the instructions for the maze challenge.
     * @return A string containing the rules and methods the user can use.
     */
    @Override
    public String getInstructions() {
        // Instructions are returned as a string and explain the rules of the maze and the actions available to the robot
        return "Welcome to the Maze Challenge!\n" +
               "RULES:\n" +
               "1. The maze is a 2D grid.\n" +
               "2. Your robot has sensors allowing it to see the squares directly above, below, left, and right.\n" +
               "3. The robot can identify whether the square is the goal, an open space, or a wall.\n" +
               "4. The robot can rotate 90 degrees left or right and move to an open space ahead or behind.\n" +
               "5. Rotating or moving 1 square is considered 1 move.\n\n" +
               "METHODS YOU CAN USE:\n" +
               "1. moveForward() - Moves the robot one step forward.\n" +
               "2. moveBackward() - Moves the robot one step backward.\n" +
               "3. rotateLeft() - Rotates the robot 90 degrees to the left.\n" +
               "4. rotateRight() - Rotates the robot 90 degrees to the right.\n" +
               "5. checkSensor(direction) - Check the specified direction ('UP', 'DOWN', 'LEFT', 'RIGHT') and returns the state of the cell.";
    }
    

    /**
     * Verifies if the provided username and password are correct.
     * @param username The username to check.
     * @param password The password to check.
     * @return true if the username and password are correct, false otherwise.
     */
    @Override
    public boolean checkUser(String username, String password) {
        // Implement user validation logic.
        // This might involve checking a database or an external system to verify credentials.
        return false; 
    }

    /**
     * Sets the operating mode of the application.
     * @param mode The new operating mode to set.
     * @return The newly set operating mode.
     */
    @Override
    public OperatingMode setOperatingMode(OperatingMode mode) {
        this.currentMode = mode;
        return this.currentMode; 
    }

     /**
     * Puts the application into practice mode, resetting the maze state.
     * @return The reset maze state for practice mode.
     */
    public MazeState practiceMode() {
        // Reset the maze state to its initial state for practice
        MazeState mazeState = new MazeState(initialMaze, 0, 0); // initial coordinates should be set properly
        this.robot = new Robot(initialMaze);//set robot position
        this.currentMazeState = mazeState;
        return mazeState;
    }

    //TODO: Let Front end handle the key
    /**
     * Handles the input commands in practice mode to control the robot.
     * @param input The input command to handle.
     */
    public void handlePracticeModeInput(String input) {
        // Process the input command and invoke the corresponding robot action
        switch (input) {
            case "moveForward":
                robot.moveForward();
                break;
            case "moveBackward":
                robot.moveBackward();
                break;
            case "rotateLeft":
                robot.rotateLeft();
                break;
            case "rotateRight":
                robot.rotateRight();
                break;
        }
       
        
        // Here, you might also want to update the state of the maze in your GUI, depending on how you've structured it.
    }

    /**
     * Gets the current state of the maze.
     * @return The current maze state.
     */
    public MazeState getCurrentMazeState() {
        return currentMazeState;
    }

    /**
     * Gets the current operating mode of the application.
     * @return The current operating mode.
     */
    public OperatingMode getCurrentMode() {
        return currentMode;
    }

    /**
     * Sets the GUI component for handling keypress events.
     * @param gui The Keypress GUI to set.
     */
    public void setGUI(Keypress gui) {
        this.keypressGUI = gui;
    }

    public Robot getRobot() {
        return this.robot;
    }



    // Implement any other methods that are required by the Educator interface.
}


