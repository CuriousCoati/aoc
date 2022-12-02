package de.breyer.aoc.y2022;

import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2022_02")
public class D02 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        int totalScore = evaluateStrategyGuidePartOne();
        System.out.println("my total score: " + totalScore);
    }

    private int evaluateStrategyGuidePartOne() {
        int totalScore = 0;
        for (String line : lines) {
            int opponent = line.charAt(0);
            int me = line.charAt(2) - 23;

            GameResult gameResult = determineGameResult(me, opponent);
            totalScore += scoreForHand(me);
            totalScore += scoreForGameResult(gameResult);
        }
        return totalScore;
    }

    private GameResult determineGameResult(int me, int opponent) {
        if (me == opponent) {
            return GameResult.DRAW;
        } else if (opponent + 1 == me || opponent - 2 == me) {
            return GameResult.WIN;
        } else {
            return GameResult.LOSE;
        }
    }

    private int scoreForHand(int character) {
        return switch (character) {
            case 'A' -> 1;
            case 'B' -> 2;
            case 'C' -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + character);
        };
    }

    private int scoreForGameResult(GameResult gameResult) {
        return switch (gameResult) {
            case WIN -> 6;
            case DRAW -> 3;
            case LOSE -> 0;
        };
    }

    @Override
    protected void partTwo() {
        int totalScore = evaluateStrategyGuidePartTwo();
        System.out.println("my total score: " + totalScore);
    }

    private int evaluateStrategyGuidePartTwo() {
        int totalScore = 0;
        for (String line : lines) {
            int opponent = line.charAt(0);
            GameResult gameResult = GameResult.convert(line.charAt(2));

            int me = determineMyHand(opponent, gameResult);
            totalScore += scoreForHand(me);
            totalScore += scoreForGameResult(gameResult);
        }
        return totalScore;
    }

    private int determineMyHand(int opponent, GameResult gameResult) {
        if (gameResult == GameResult.DRAW) {
            return opponent;
        } else if (gameResult == GameResult.WIN) {
            return opponent == 'C' ? opponent - 2 : opponent + 1;
        } else {
            return opponent == 'A' ? opponent + 2 : opponent - 1;
        }
    }

}
