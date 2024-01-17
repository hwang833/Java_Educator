package com.ztcollins.JavaEducator.robotFunctions;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * MazePanel is a custom JPanel that represents the visual component of a maze.
 * It is responsible for drawing the maze grid and updating it as the user navigates through the maze.
 * Each cell in the maze is represented by a character that determines its color when drawn.
 * 'W' represents a wall, 'R' represents the robot, 'G' represents the goal, and any other character
 * represents open space within the maze.
 */
public class MazePanel extends JPanel {
    private char[][] maze;
    
    /**
     * Constructs a MazePanel with the initial layout of the maze.
     *
     * @param maze A 2D array of characters representing the maze structure.
     */
    public MazePanel(char[][] maze) {
        this.maze = maze;
    }

     /**
     * Overrides the paintComponent method to draw the maze.
     * Each character in the maze array corresponds to a color-coded square in the panel.
     *
     * @param g The Graphics object used for drawing operations.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Here, you draw your maze using the Graphics object 'g'.
        // For simplicity, let's assume each cell in the maze is a 20x20 pixel square.
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                // Chooses the color based on the type of maze cell.
                if (maze[i][j] == 'W') { // Wall
                    g.setColor(Color.BLACK);
                } else if (maze[i][j] == 'R') { // Robot
                    g.setColor(Color.RED);
                } else if (maze[i][j] == 'G') { // Goal
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.WHITE); // Open space
                }
                 // Draws the cell as a 20x20 pixel square.
                g.fillRect(j * 20, i * 20, 20, 20);
            }
        }
    }

     /**
     * Updates the maze with a new layout and triggers a repaint of the panel.
     *
     * @param newMaze The new maze layout to be drawn.
     */
    public void updateMaze(char[][] newMaze) {
        this.maze = newMaze;
        repaint(); // Redraw the panel with the new maze layout
    }
}
