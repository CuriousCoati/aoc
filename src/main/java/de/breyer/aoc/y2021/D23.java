package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_23")
public class D23 extends AbstractAocPuzzle {

    private GameStateD23 initialGameState;
    private long minEnergy;

    @Override
    protected void partOne() {
        processInput(false);
        minEnergy = -1;
        tryMoves(initialGameState);
        System.out.println("min energy: " + minEnergy);
    }

    @Override
    protected void partTwo() {
        processInput(true);
        minEnergy = -1;
        tryMoves(initialGameState);
        System.out.println("min energy: " + minEnergy);
    }

    private void processInput(boolean fullDepth) {
        if (fullDepth) {
            lines = new ArrayList<>(lines);
            lines.add(3, "  #D#C#B#A#  ");
            lines.add(4, "  #D#B#A#C#  ");
        }
        initialGameState = new GameStateD23(fullDepth ? 4 : 2);

        int idx = 0;
        for (String line : lines) {
            if (1 < idx && idx < 6) {
                int room = 0;
                for (char character : line.toCharArray()) {
                    if (character != '#' && character != ' ') {
                        initialGameState.addAmphipod(room, idx - 2, character);
                        room++;
                    }
                }
            }
            idx++;
        }
    }

    private void tryMoves(GameStateD23 gameState) {
        List<GameStateD23> possibleMoves = gameState.calculatePossibleMoves();
        for (GameStateD23 possibleMove : possibleMoves) {
            if (possibleMove.isFinished()) {
                if (minEnergy == -1 || minEnergy > possibleMove.getUsedEnergy()) {
                    minEnergy = possibleMove.getUsedEnergy();
                }
            } else {
                tryMoves(possibleMove);
            }
        }
    }
}
