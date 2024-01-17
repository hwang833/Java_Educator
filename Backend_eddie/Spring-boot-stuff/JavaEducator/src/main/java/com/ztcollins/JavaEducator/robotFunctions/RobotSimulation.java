package com.ztcollins.JavaEducator.robotFunctions;

import java.util.List;

public class RobotSimulation {
    private int[][] moves;
    private int totalMoves;
    private String errorTrace; // Field to store error trace
    private List<String> sequenceOfMoves;
    
    public RobotSimulation(int[][] moves, int totalMoves, List<String> sequenceOfMoves) {
        this.moves = moves;
        this.totalMoves = totalMoves;
        this.sequenceOfMoves = sequenceOfMoves;
    }

    // Constructor that includes error trace
    public RobotSimulation(int[][] moves, int totalMoves, String errorTrace) {
        this.moves = moves;
        this.totalMoves = totalMoves;
        this.errorTrace = errorTrace;
    }

    // Getter methods
    public int[][] getMoves() {
        return moves;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public String getErrorTrace() {
        return errorTrace;
    }

    public List<String> getSequenceOfMoves() {
        return sequenceOfMoves;
    }
}