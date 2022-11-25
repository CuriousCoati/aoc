package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_21")
public class D21 extends AbstractAocPuzzle {

    private final Player[] players = new Player[2];
    private final Dice dice = new Dice();
    private int startPositionPlayerOne;
    private int startPositionPlayerTwo;
    private final List<Integer> quantumRolls = new ArrayList<>();
    private final Map<GameStateD21, Result> resultCache = new HashMap<>();

    @Override
    protected void partOne() {
        init();
        processInputs();
        playWithDeterministicDice();
    }

    @Override
    protected void partTwo() {
        playWithDiracDice();
    }

    private void init() {
        players[0] = new Player();
        players[1] = new Player();
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                for (int k = 1; k < 4; k++) {
                    quantumRolls.add(i + j + k);
                }
            }
        }
    }

    private void processInputs() {
        int idx = 0;
        for (String line : lines) {
            final int startPosition = Integer.parseInt(line.substring(line.length() - 1));
            players[idx].setPosition(startPosition);
            if (idx == 0) {
                startPositionPlayerOne = startPosition;
            } else {
                startPositionPlayerTwo = startPosition;
            }
            idx++;
        }
    }

    private void playWithDeterministicDice() {
        boolean firstPlayer = true;
        Player player;
        do {
            if (firstPlayer) {
                player = players[0];
            } else {
                player = players[1];
            }
            firstPlayer = !firstPlayer;

            int moves = 0;
            for (int i = 0; i < 3; i++) {
                moves += dice.rollDie();
            }

            player.move(moves);

        } while (!player.isFinished());

        Player loosingPlayer = firstPlayer ? players[0] : players[1];
        System.out.println("end score: " + (loosingPlayer.getScore() * dice.getRolls()));
    }

    private void playWithDiracDice() {
        GameStateD21 startState = new GameStateD21(0, startPositionPlayerOne, 0, startPositionPlayerTwo, true);
        Result result = calcDuracMove(startState);
        System.out.println("wins player: " + result.getWinsPlayerOne());
        System.out.println("wins player2: " + result.getWinsPlayerTwo());
    }

    private Result calcDuracMove(GameStateD21 gameStateD21) {
        if (resultCache.containsKey(gameStateD21)) {
            return resultCache.get(gameStateD21);
        }

        if (gameStateD21.getScorePlayerOne() >= 21) {
            Result result = new Result(1, 0);
            resultCache.put(gameStateD21, result);
            return result;
        } else if (gameStateD21.getScorePlayerTwo() >= 21) {
            Result result = new Result(0, 1);
            resultCache.put(gameStateD21, result);
            return result;
        }

        long winsPlayerOne = 0;
        long winsPlayerTwo = 0;

        for (int roll : quantumRolls) {
            Result result;
            if (gameStateD21.isPlayerOneTurn()) {
                int newPosition = move(gameStateD21.getPositionPlayerOne(), roll);
                result = calcDuracMove(new GameStateD21(gameStateD21.getScorePlayerOne() + newPosition, newPosition,
                        gameStateD21.getScorePlayerTwo(), gameStateD21.getPositionPlayerTwo(), !gameStateD21.isPlayerOneTurn()));
            } else {
                int newPosition = move(gameStateD21.getPositionPlayerTwo(), roll);
                result = calcDuracMove(new GameStateD21(gameStateD21.getScorePlayerOne(), gameStateD21.getPositionPlayerOne(),
                        gameStateD21.getScorePlayerTwo() + newPosition, newPosition, !gameStateD21.isPlayerOneTurn()));
            }

            winsPlayerOne += result.getWinsPlayerOne();
            winsPlayerTwo += result.getWinsPlayerTwo();
        }

        Result result = new Result(winsPlayerOne, winsPlayerTwo);
        resultCache.put(gameStateD21, result);
        return result;
    }

    public int move(int position, int moves) {
        position += moves;
        while (position > 10) {
            position = position - 10;
        }
        return position;
    }
}
