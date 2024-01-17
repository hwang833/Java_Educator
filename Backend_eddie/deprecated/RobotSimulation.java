public class RobotSimulation {
    private int[][] moves;
    private int totalMoves;

    public RobotSimulation(int[][] moves, int totalMoves) {
        this.moves = moves;
        this.totalMoves = totalMoves;
    }

    // Methods to get the moves and totalMoves
    public int[][] getMoves() {
        return moves;
    }

    public int getTotalMoves() {
        return totalMoves;
    }
}