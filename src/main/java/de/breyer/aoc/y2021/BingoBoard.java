package de.breyer.aoc.y2021;

public class BingoBoard {

    private final BingoField[][] fields = new BingoField[5][5];
    private final int boardNumber;
    private int lastMove;

    public BingoBoard(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public void addField(int x, int y, int number) {
        fields[x][y] = new BingoField(number);
    }

    public void processPlayMove(int number) {
        lastMove = number;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (fields[x][y].checkNumber(number)) {
                    fields[x][y].mark();
                    return;
                }
            }
        }
    }

    public boolean checkFieldsAreMarked(boolean linewise) {
        for (int i = 0; i < 5; i++) {
            boolean lineMarked = true;
            for (int j = 0; j < 5; j++) {
                BingoField field = linewise ? fields[i][j] : fields[j][i];
                if (!field.isMarked()) {
                    lineMarked = false;
                    break;
                }
            }

            if (lineMarked) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoard() {
        return checkFieldsAreMarked(true) || checkFieldsAreMarked(false);
    }

    public int sumUnmarkedFields() {
        int sum = 0;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (!fields[x][y].isMarked()) {
                    sum += fields[x][y].getNumber();
                }
            }
        }
        return sum;
    }

    public int getBoardNumber() {
        return boardNumber;
    }

    @Override
    public String toString() {
        return "Board [" + getBoardNumber() + "] Score: " + getScore();
    }

    private int getScore() {
        return sumUnmarkedFields() * lastMove;
    }
}
