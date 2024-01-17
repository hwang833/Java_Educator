/**
 * The Robot class simulates the movements and sensor readings of a robot navigating a maze.
 * It maintains the current position of the robot, the direction it is facing, and the layout of the maze.
 * The robot can move forward, move backward (by turning and moving forward), and rotate left or right.
 * It uses a simple character-based representation for the maze ('O' for open, 'W' for wall, and 'G' for goal).
 */
public class Robot {

    // Enumerations for the direction the robot is facing.
    protected enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private char[][] maze;  // The maze layout.
    private int x, y;       // Robot's current position.
    private Direction facingDirection = Direction.UP;  // Initial direction the robot is facing.

    // Enumeration for the state of a cell within the maze.
    public enum CellState {
        WALL,      // The cell is a wall
        OPEN,      // The cell is an open space
        GOAL       // The cell is the goal/end point of the maze
    }
    
    /**
     * Initializes the Robot within the maze and sets its position at the 'R' character.
     *
     * @param maze The 2D array representing the maze the robot will navigate.
     */
    public Robot(char[][] maze) {
        this.maze = maze;
        // Loop through the maze, search for the robot's initial position marked by 'R'.
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'R') {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
    }

    /**
     * Moves the robot forward in the direction it is currently facing.
     * The robot can only move if the space is open, and it updates the maze to reflect the new position.
     *
     * @return true if the robot moves forward; false if it hits a wall or the edge of the maze.
     */
    public boolean moveForward() {
        // Handle movement based on the direction the robot is facing.
        switch (facingDirection) {
            case UP:
                //check whether the robot is moving into a wall
                if (x > 0 && maze[x-1][y] != 'W') {
                    //maze[x][y] = 'O';  // Current position becomes open
                    x--;  // Move up
                    //maze[x][y] = 'R';  // New position becomes robot
                    return true;
                }
                break;
            //all following cases are the same logic as the first one
            case DOWN:
                if (x < maze.length - 1 && maze[x+1][y] != 'W') {
                    //maze[x][y] = 'O';
                    x++;
                    //aze[x][y] = 'R';
                    return true;
                }
                break;
            case LEFT:
                if (y > 0 && maze[x][y-1] != 'W') {
                    //maze[x][y] = 'O';
                    y--;
                    //maze[x][y] = 'R';
                    return true;
                }
                break;
            case RIGHT:
                if (y < maze[x].length - 1 && maze[x][y+1] != 'W') {
                    //maze[x][y] = 'O';
                    y++;
                    //maze[x][y] = 'R';
                    return true;
                }
                break;
        }
        return false;
    }

     /**
     * Moves the robot backward by first changing its facing direction to the opposite
     * and then attempting to move forward in that new direction.
     *
     * @return true if the move is successful; false otherwise.
     */
    public boolean moveBackward() {
        // Change facing direction to the opposite and attempt to move forward.
        switch (facingDirection) {
            //instead of doing the whole process, change the facing direction to oppsite and call moveForward
            case UP:
                facingDirection = Direction.DOWN;
                break;
            case DOWN:
                facingDirection = Direction.UP;
                break;
            case LEFT:
                facingDirection = Direction.RIGHT;
                break;
            case RIGHT:
                facingDirection = Direction.LEFT;
                break;
        }
        return moveForward();
    }

    /**
     * Rotates the robot to the left without changing its position in the maze.
     */
    public void rotateLeft() {
        //Change facing directions based on key pressed
        switch (facingDirection) {
            case UP:
                facingDirection = Direction.LEFT;
                break;
            case DOWN:
                facingDirection = Direction.RIGHT;
                break;
            case LEFT:
                facingDirection = Direction.DOWN;
                break;
            case RIGHT:
                facingDirection = Direction.UP;
                break;
        }
    }

    /**
     * Rotates the robot to the right without changing its position in the maze.
     */
    public void rotateRight() {
        //Change facing directions based on key pressed
        switch (facingDirection) {
            case UP:
                facingDirection = Direction.RIGHT;
                break;
            case DOWN:
                facingDirection = Direction.LEFT;
                break;
            case LEFT:
                facingDirection = Direction.UP;
                break;
            case RIGHT:
                facingDirection = Direction.DOWN;
                break;
        }
    }

    /**
     * Checks the sensor in the given direction relative to the robot's current position
     * and returns the state of the cell in that direction.
     *
     * @param directionStr The direction to check, expressed as a string corresponding to the Direction enum.
     * @return The CellState (WALL, OPEN, or GOAL) of the cell in the specified direction.
     */
    public CellState checkSensor(String directionStr) {
        // Convert the string to a Direction enum and check the cell state in that direction.
        Direction direction = Direction.valueOf(directionStr);
        switch (direction) {
            case UP:
                if (x > 0) return getCellState(maze[x-1][y]);
                break;
            case DOWN:
                if (x < maze.length - 1) return getCellState(maze[x+1][y]);
                break;
            case LEFT:
                if (y > 0) return getCellState(maze[x][y-1]);
                break;
            case RIGHT:
                if (y < maze[x].length - 1) return getCellState(maze[x][y+1]);
                break;
        }
        return CellState.WALL;  // Default to WALL if out of bounds
    }

    /**
     * Helper method to determine the state of a cell given its character representation.
     *
     * @param cell The character in the maze array representing a cell.
     * @return The corresponding CellState of the cell.
     */
    private CellState getCellState(char cell) {
         // Map the cell character to its corresponding CellState.
        switch (cell) {
            case 'O':
                return CellState.OPEN;
            case 'W':
                return CellState.WALL;
            case 'G':
                return CellState.GOAL;
            default:
                throw new IllegalArgumentException("Invalid cell character");
        }
    }

    public int getDirections() {
        switch (facingDirection) {
            case UP: return 0;
            case RIGHT: return 1;
            case DOWN: return 2;
            case LEFT: return 3;
            default: return -1; // Error case
        }
    }
}
