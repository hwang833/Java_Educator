package com.ztcollins.JavaEducator.robotFunctions;

/**
 * The MazeState class encapsulates the state of a maze and the position of a robot within it.
 * It holds a 2D array representing the maze layout where walls, open spaces, and other items are marked.
 * It also tracks the X and Y coordinates of the robot within the maze, providing a snapshot of the
 * current state that can be used to update the maze or to check the status of the game.
 */
public class MazeState {

    private char[][] mazeLayout; // 2D array representing the maze structure.
    private int robotX;          // X-coordinate of the robot's position in the maze.
    private int robotY;          // Y-coordinate of the robot's position in the maze.

    /**
     * Constructs a MazeState with a given maze layout and the robot's initial position.
     *
     * @param mazeLayout 2D character array representing the maze where each character represents a cell.
     * @param robotX The initial X-coordinate of the robot in the maze.
     * @param robotY The initial Y-coordinate of the robot in the maze.
     */
    public MazeState(char[][] mazeLayout, int robotX, int robotY) {
        this.mazeLayout = mazeLayout;
        this.robotX = robotX;
        this.robotY = robotY;
    }

    /**
     * Retrieves the current maze layout.
     *
     * @return A 2D character array representing the current state of the maze.
     */
    public char[][] getMazeLayout() {
        return this.mazeLayout;
    }

    /**
     * Retrieves the current X-coordinate of the robot's position in the maze.
     *
     * @return An integer representing the robot's current X-coordinate.
     */
    public int getRobotX() {
        return this.robotX;
    }

    /**
     * Retrieves the current Y-coordinate of the robot's position in the maze.
     *
     * @return An integer representing the robot's current Y-coordinate.
     */
    public int getRobotY() {
        return this.robotY;
    }

    // Optionally, include setters here if the robot's position or the maze layout needs to be changed after creation.
}
