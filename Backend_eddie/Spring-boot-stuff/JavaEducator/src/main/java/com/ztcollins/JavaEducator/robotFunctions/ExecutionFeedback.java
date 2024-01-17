package com.ztcollins.JavaEducator.robotFunctions;

public class ExecutionFeedback {
    private boolean success; // Whether the robot code executed successfully
    private String feedbackMessage; // Detailed feedback, could be a success message or error details
    private int numberOfMoves; // Number of moves the robot made (if applicable)

    // Constructor
    // public ExecutionFeedback(boolean success, String feedbackMessage, int
    // numberOfMoves) {
    public ExecutionFeedback(boolean success, String feedbackMessage) {
        this.success = success;
        this.feedbackMessage = feedbackMessage;
        // this.numberOfMoves = numberOfMoves;
    }

    // Getters and setters for each field...

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }
}
