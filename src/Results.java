public class Results {
    private int winner;
    private int p1Score;
    private int p2Score;
    private int turnsTaken;
    private int lastPlayerMoved;

    public Results() {
        winner = 0;
        p1Score = 0;
        p2Score = 0;
        turnsTaken = 0;
        lastPlayerMoved = 0;
    }

    public void Update(Board using) {
        p1Score = using.getP1Score();
        p2Score = using.getP2Score();
        if (p1Score > p2Score) {
            winner = 1;
        } else if (p1Score < p2Score) {
            winner = -1;
        }
        turnsTaken = using.getTurnsTaken();
        if (turnsTaken % 2 == 0) {
            lastPlayerMoved = 1;
        } else {
            lastPlayerMoved = -1;
        }
    }

    public int getWinner() {
        return winner;
    }

    public int getLastPlayerMoved() {
        return lastPlayerMoved;
    }

    public int getTurnsTaken() {
        return turnsTaken;
    }
}
