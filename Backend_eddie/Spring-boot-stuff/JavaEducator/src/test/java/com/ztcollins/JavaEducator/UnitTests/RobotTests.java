package com.ztcollins.JavaEducator.UnitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ztcollins.JavaEducator.robotFunctions.Robot;

/*
 * This class uses JUnit to test EducatorImpl.java
 */
public class RobotTests {

    Robot myRobot;

    /*
     * Setup the maze before each test:
     * 
     * Initial maze:
     * 
     * W W W W W
     * W O W O W
     * W R W G W
     * W O O O W
     * W W W W W
     * 
     * R = Robot
     * W = Wall
     * G = Goal
     */
    @Before
    public void setupBeforeEach() {

        char[][] initialMaze = {
            {'W', 'W', 'W', 'W', 'W'},
            {'W', 'O', 'W', 'O', 'W'},
            {'W', 'R', 'W', 'G', 'W'},
            {'W', 'O', 'O', 'O', 'W'},
            {'W', 'W', 'W', 'W', 'W'},
        };

        //starts facing up (hardcoded in Robot.java)
        myRobot = new Robot(initialMaze,2,2,"UP");
    }

    /*
     * Moves forward twice. First move should work since there is an open space.
     * Second move shouldn't work since there will be a wall after the first move.
     * 
     * Maze after first move:
     * W W W W W
     * W R W O W
     * W O W G W
     * W O O O W
     * W W W W W
     * 
     */
    @Test
    public void testValidAndInvalidMoveForward() {
        Assert.assertEquals(myRobot.moveForward(), true);
        Assert.assertEquals(myRobot.moveForward(), false);
    }

    /*
     * Moves forward twice. First move should work since there is an open space.
     * Second move shouldn't work since there will be a wall after the first move.
     * 
     * Maze after first move:
     * W W W W W
     * W O W O W
     * W O W G W
     * W R O O W
     * W W W W W
     * 
     */
    @Test
    public void testValidAndInvalidMoveBackward() {
        Assert.assertEquals(myRobot.moveBackward(), true);
        Assert.assertEquals(myRobot.moveForward(), false);
    }

    /*
     * The robot's starting direction is UP. The robot rotates right twice to turn around completely.
     * The robot will then have a successful move forward since they are facing in the DOWN direction.
     * The next move will fail since there is a wall.
     * 
     * Maze after first move forward:
     * W W W W W
     * W O W O W
     * W O W G W
     * W R O O W
     * W W W W W
     * 
     */
    @Test
    public void testRotateRight() {
        myRobot.rotateRight();
        myRobot.rotateRight();
        Assert.assertEquals(myRobot.moveForward(), true);
        Assert.assertEquals(myRobot.moveForward(), false);
    }

    /*
     * The robot's starting direction is UP. The robot rotates right once to face RIGHT.
     * The robot will then have an unsuccessful move forward since they are facing toward a wall.
     * 
     * Maze after first move forward:
     * W W W W W
     * W O W O W
     * W R W G W
     * W O O O W
     * W W W W W
     * 
     */
    @Test
    public void testRotateRightIntoWall() {
        myRobot.rotateRight();
        Assert.assertEquals(myRobot.moveForward(), false);
    }

    /*
     * The robot's starting direction is UP. The robot rotates left twice to turn around completely.
     * The robot will then have a successful move forward since they are facing in the DOWN direction.
     * The next move will fail since there is a wall.
     * 
     * Maze after first move forward:
     * W W W W W
     * W O W O W
     * W O W G W
     * W R O O W
     * W W W W W
     * 
     */
    @Test
    public void testRotateLeft() {
        myRobot.rotateLeft();
        myRobot.rotateLeft();
        Assert.assertEquals(myRobot.moveForward(), true);
        Assert.assertEquals(myRobot.moveForward(), false);
    }

    /*
     * The robot's starting direction is UP. The robot rotates left once to face LEFT.
     * The robot will then have an unsuccessful move forward since they are facing toward a wall.
     * 
     * Maze after first move forward:
     * W W W W W
     * W O W O W
     * W R W G W
     * W O O O W
     * W W W W W
     * 
     */
    @Test
    public void testRotateLeftIntoWall() {
        myRobot.rotateLeft();
        Assert.assertEquals(myRobot.moveForward(), false);
    }

    /*
     * The robot is checking the UP sensor... expect UP sensor to have OPEN
     * 
     * Maze before checking sensor:
     * W W W W W
     * W O W O W
     * W R W G W
     * W O O O W
     * W W W W W
     * 
     */
    @Test
    public void testSensorInitialUp() {
        Assert.assertEquals(myRobot.checkSensor("UP"), Robot.CellState.OPEN);
    }

    /*
     * The robot is checking the DOWN sensor... expect DOWN sensor to have OPEN
     * 
     * Maze before checking sensor:
     * W W W W W
     * W O W O W
     * W R W G W
     * W O O O W
     * W W W W W
     * 
     */
    @Test
    public void testSensorInitialDown() {
        Assert.assertEquals(myRobot.checkSensor("DOWN"), Robot.CellState.OPEN);
    }

    /*
     * The robot is checking the RIGHT sensor... expect RIGHT sensor to have WALL
     * 
     * Maze before checking sensor:
     * W W W W W
     * W O W O W
     * W R W G W
     * W O O O W
     * W W W W W
     * 
     */
    @Test
    public void testSensorInitialRight() {
        Assert.assertEquals(myRobot.checkSensor("RIGHT"), Robot.CellState.WALL);
    }

    /*
     * The robot is checking the LEFT sensor... expect LEFT sensor to have WALL
     * 
     * Maze before checking sensor:
     * W W W W W
     * W O W O W
     * W R W G W
     * W O O O W
     * W W W W W
     * 
     */
    @Test
    public void testSensorInitialLeft() {
        Assert.assertEquals(myRobot.checkSensor("LEFT"), Robot.CellState.WALL);
    }

    /*
     * This test expects the robot to perform movement and make sure the sensors show the correct data
     * 
     * Initial maze:
     * W W W W W
     * W O W O W
     * W R W G W
     * W O O O W
     * W W W W W
     * 
     * first move + rotate left:
     * W W W W W
     * W O W O W
     * W O W G W
     * W R O O W
     * W W W W W
     * 
     * second move:
     * W W W W W
     * W O W O W
     * W O W G W
     * W O R O W
     * W W W W W
     * 
     * third move + rotate left:
     * W W W W W
     * W O W O W
     * W O W G W
     * W O O R W
     * W W W W W
     * 
     * Robot is now facing the GOAL... check sensors
     */
    @Test
    public void testSensorAndMovement() {

        //first move (also check that you cant move forward into wall)
        Assert.assertEquals(myRobot.moveBackward(), true);
        Assert.assertEquals(myRobot.moveForward(), false);
        myRobot.rotateLeft();

        //second move
        Assert.assertEquals(myRobot.moveForward(), true);

        //third move (also check that you cant move forward into wall)
        Assert.assertEquals(myRobot.moveForward(), true);
        Assert.assertEquals(myRobot.moveForward(), false);
        myRobot.rotateLeft();

        //check sensors
        Assert.assertEquals(myRobot.checkSensor("LEFT"), Robot.CellState.OPEN);
        Assert.assertEquals(myRobot.checkSensor("RIGHT"), Robot.CellState.WALL);
        Assert.assertEquals(myRobot.checkSensor("DOWN"), Robot.CellState.WALL);
        Assert.assertEquals(myRobot.checkSensor("UP"), Robot.CellState.GOAL);
    }

}
