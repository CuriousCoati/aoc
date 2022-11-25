package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_04")
public class D04 extends AbstractAocPuzzle {

    private final List<BingoBoard> boards = new ArrayList<>();
    private final List<BingoBoard> completedBoards = new ArrayList<>();

    @Override
    protected void partOne() {
        convertInput();
        playBingo();
        System.out.println(completedBoards.get(0));
    }

    private void convertInput() {
        BingoBoard board = new BingoBoard(boards.size() + 1);
        int x = 0;

        for (int idx = 2; idx < lines.size(); idx++) {
            String line = lines.get(idx);
            if (line.isBlank()) {
                boards.add(board);
                board = new BingoBoard(boards.size() + 1);
                x = 0;
            } else {
                String[] numbers = line.split(" ");
                int y = 0;
                for (String number : numbers) {
                    if (!number.isBlank()) {
                        board.addField(x, y, Integer.parseInt(number));
                        y++;
                    }
                }
                x++;
            }
        }
    }

    private void playBingo() {
        String[] playMoves = lines.get(0).split(",");
        int moveCounter = 0;
        for (String playMove: playMoves) {
            int number = Integer.parseInt(playMove);

            List<BingoBoard> finishedBoards = new ArrayList<>();

            for (BingoBoard board : boards) {
                board.processPlayMove(number);
                if (moveCounter >= 5 && board.checkBoard()) {
                    finishedBoards.add(board);
                }
            }

            boards.removeAll(finishedBoards);
            completedBoards.addAll(finishedBoards);

            moveCounter++;
        }
    }

    @Override
    protected void partTwo() {
        System.out.println(completedBoards.get(completedBoards.size() - 1));
    }
}
