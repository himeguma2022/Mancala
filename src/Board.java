public class Board {
    private int[] contents;
    private int p1Score;
    private int p2Score;
    private boolean reachedEndConditon;
    private int turnsTaken;
    private Results stats;

    public static void main(String[] args) {
        int player1Wins = 0;
        int player2Wins = 0;
        int ties = 0;
        int turnsTakenSum = 0;
        int scoreDifferenceSum = 0;
        int gamesSimulating = 1000000;
        int x = 1;
        for (int i = 0; i < gamesSimulating; ++i) {
            Board demo = new Board();
            demo.testPlay();
            switch (demo.stats.getWinner()) {
                case 1:
                    ++player1Wins;
                    scoreDifferenceSum = scoreDifferenceSum + demo.p1Score - demo.p2Score;
                    break;
                case 0:
                    ++ties;
                    break;
                case -1:
                    ++player2Wins;
                    scoreDifferenceSum = scoreDifferenceSum + demo.p2Score - demo.p1Score;
                    break;
            }
            turnsTakenSum = turnsTakenSum + demo.stats.getTurnsTaken();
            if(i == x*gamesSimulating / 100){
                System.out.println(x+"% done!");
                ++x;
            }
        }
        System.out.println("Player 1 wins " + player1Wins + " games.");
        double p1WinRate = (double)player1Wins * 100 / gamesSimulating;
        System.out.println("A rate of "+p1WinRate +" %");
        System.out.println("Player 2 wins " + player2Wins + " games.");
        double p2WinRate = (double)player2Wins * 100 / gamesSimulating;
        System.out.println("A rate of "+p2WinRate +" %");
        System.out.println("A tie occurs in " + ties + " games.");
        double tieRate = (double)ties * 100 / gamesSimulating;
        System.out.println("A rate of "+tieRate +" %");
        System.out.println("Average game takes " + (double)turnsTakenSum / gamesSimulating + " turns");
        System.out.println("Average score difference is " + (double)scoreDifferenceSum / gamesSimulating + " marbles");


    }

    private void testPlay() {
        int activePlayer = 1;
        while (!reachedEndConditon) {
            int target = ChooseRandomPosition();
            moveAllPiecesIn(target, activePlayer);
            activePlayer = -activePlayer;
            ++turnsTaken;
        }
        stats.Update(this);
    }

    private void PrintBoardConsole() {
        System.out.println("[\t\t" + contents[0] + "\t\t]");
        System.out.println("[\t" + contents[1] + "\t][\t" + contents[13] + "\t]");
        System.out.println("[\t" + contents[2] + "\t][\t" + contents[12] + "\t]");
        System.out.println("[\t" + contents[3] + "\t][\t" + contents[11] + "\t]");
        System.out.println("[\t" + contents[4] + "\t][\t" + contents[10] + "\t]");
        System.out.println("[\t" + contents[5] + "\t][\t" + contents[9] + "\t]");
        System.out.println("[\t" + contents[6] + "\t][\t" + contents[8] + "\t]");
        System.out.println("[\t\t" + contents[7] + "\t\t]\n");
    }

    public Board() {
        contents = new int[14];
        for (int i = 0; i < contents.length; ++i) {
            contents[i] = 4;
        }
        contents[0] = 0;
        contents[7] = 0;
        p1Score = 0;
        p2Score = 0;
        reachedEndConditon = false;
        turnsTaken = 0;
        stats = new Results();
    }

    public void moveAllPiecesIn(int pit, int playerTurn) {
        if ((pit > 0 && pit != 7) && pit < 14) {
            int skipedHoleFactor = 1;
            for (int i = 0; i < contents[pit]; ++i) {
                int insertIntoPos = pit + i + skipedHoleFactor;
                switch (insertIntoPos % 14) {
                    case 0:
                        if (playerTurn == 1) {
                            ++contents[0];
                        } else {
                            ++contents[1];
                            ++skipedHoleFactor;
                        }
                        break;
                    case 7:
                        if (playerTurn == -1) {
                            ++contents[7];
                        } else {
                            ++contents[8];
                            ++skipedHoleFactor;
                        }
                        break;
                    default:
                        ++contents[insertIntoPos % 14];
                        break;
                }
            }
            contents[pit] = 0;
        }
        p1Score = contents[0];
        p2Score = contents[7];
        ++turnsTaken;
        CheckEndCondition();
        if (reachedEndConditon) {
            for (int i = 1; i < 7; ++i) {
                p2Score = p2Score + contents[i];
            }
            for (int i = 8; i < 14; ++i) {
                p1Score = p1Score + contents[i];
            }

        }
    }

    private void CheckEndCondition() {
        boolean reached = true;
        for (int i = 1; i < 7; ++i) {
            if (contents[i] != 0) {
                reached = false;
            }
        }
        if (reached) {
            reachedEndConditon = true;
        } else {
            reached = true;
            for (int i = 8; i < 14; ++i) {
                if (contents[i] != 0) {
                    reached = false;
                }
            }
            if (reached) {
                reachedEndConditon = true;
            }
        }
    }

    public int ChooseRandomPosition() {
        int magicNumber = (int) Math.floor(Math.random() * (12)) + 1;
        if (magicNumber > 6) {
            ++magicNumber;
        }
        return magicNumber;
    }

    public boolean validGame() {
        int piecesSum = 0;
        for (int i = 0; i < contents.length; ++i) {
            piecesSum = piecesSum + contents[i];
        }
        if (piecesSum != 48) {
            return false;
        }
        return true;
    }

    public int getP2Score() {
        return p2Score;
    }

    public int getP1Score() {
        return p1Score;
    }

    public boolean getReachedEndConditon() {
        return reachedEndConditon;
    }

    public int getTurnsTaken() {
        return turnsTaken;
    }
}
