package com.ztcollins.JavaEducator.robotFunctions;

public interface Educator {

    enum OperatingMode {
        DISPLAY, // Or INSTRUCTION, based on what you want to call it
        PRACTICE,
        ROBOT_TEST,
        DEFAULT_MODE // Added for the robot test mode
        // Add other modes as necessary
    }

    //TODO: Might move to front end
    /**
     * Provides the basic rules and instructions of the software.
     * @return A string containing the instructions.
     */
    String getInstructions();

    //TODO: Change the input parameter
    /**
     * Validates the user's credentials.
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @return True if the credentials are valid; false otherwise.
     */
    boolean checkUser(String username, String password);

    /**
     * Sets the current operating mode.
     * @param mode The desired operating mode.
     * @return The set operating mode.
     */
    OperatingMode setOperatingMode(OperatingMode mode);

    // //TODO: Return a robot move instead of a whole Maze
    // /**
    //  * Starts the practice mode, where the user can manually navigate a robot through a maze.
    //  * @return The initial state of the randomly-selected maze and robot's position.
    //  */
    // MazeState practiceMode();

    // ... other methods
}
//TODO: 
// class MazeState {
//     public MazeState(char[][] initialMaze, int i, int j) {
//     }
//     private char[][] mazeLayout;  // 2D representation of the maze: 'W' for wall, 'O' for open, 'R' for robot, 'G' for goal.
//     private int robotX, robotY;  // The robot's current coordinates.
//     public char[][] getMazeLayout() {
//         return this.mazeLayout;
//     }

//     // Constructors, getters, setters, and other methods
// }