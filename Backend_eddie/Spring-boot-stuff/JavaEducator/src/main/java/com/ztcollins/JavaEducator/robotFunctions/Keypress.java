package com.ztcollins.JavaEducator.robotFunctions;

import javax.swing.JFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The Keypress class extends JFrame and implements the KeyListener interface
 * to handle key events for a maze navigation application.
 * This class is responsible for setting up the GUI for the maze panel and 
 * processing keyboard inputs that correspond to navigation commands within the maze.
 * It works in conjunction with an EducatorImpl instance to handle the actual
 * navigation logic based on the key presses.
 */
class Keypress extends JFrame implements KeyListener {

    private EducatorImpl educator;
    private MazePanel mazePanel;
    
    /**
     * Sets up the necessary components for handling key presses.
     *
     * @param educator The EducatorImpl instance that will handle navigation logic.
     */
    public void press(EducatorImpl educator) {
        this.educator = educator;
        mazePanel = new MazePanel(educator.getCurrentMazeState().getMazeLayout());  //get the maze data from the educator object that is passed in
        this.add(mazePanel);
        this.addKeyListener(this);
    }

    // Other GUI methods ...

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for our implementation
    }

    public MazePanel getMazePanel() {
        return mazePanel;
    }

    /**
     * When mode is set to practice, this method will invoke the method that moves the 
     * robot with input automatically matched to the key pressed
     * After that update and refresh the maze
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Checks if the application is in PRACTICE mode before processing key presses.
        if (educator.getCurrentMode() == Educator.OperatingMode.PRACTICE) {
            // Maps the arrow keys to corresponding navigation methods in EducatorImpl.
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    educator.handlePracticeModeInput("moveForward");
                    break;
                case KeyEvent.VK_DOWN:
                    educator.handlePracticeModeInput("moveBackward");
                    break;
                case KeyEvent.VK_LEFT:
                    educator.handlePracticeModeInput("rotateLeft");
                    break;
                case KeyEvent.VK_RIGHT:
                    educator.handlePracticeModeInput("rotateRight");
                    break;
            }
            // Retrieves the updated maze layout after the move has been made.
            MazeState mazeState = educator.getCurrentMazeState();
            char[][] mazeLayout = mazeState.getMazeLayout();
            // Updates the MazePanel with the new maze layout.
            mazePanel.updateMaze(mazeLayout);
            // After handling the input,probably update the maze displayed to the user
            // refreshMazeDisplay();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for our implementation
    }

   
}
