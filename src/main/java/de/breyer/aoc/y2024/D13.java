package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.LongCoordinate2D;
import lombok.Data;

@AocPuzzle("2024_13")
public class D13 extends AbstractAocPuzzle {

    private static final int TOKENS_BUTTON_A = 3;
    private static final int TOKENS_BUTTON_B = 1;

    @Override
    protected void partOne() {
        var clawMachines = parseInput(lines);
        var minimumTokens = calcToken(clawMachines);
        System.out.println("minimum tokens: " + minimumTokens);
    }

    private List<ClawMachine> parseInput(List<String> input) {
        var clawMachines = new ArrayList<ClawMachine>();

        var clawMachine = new ClawMachine();
        clawMachines.add(clawMachine);

        for (var line : input) {
            if (line.isEmpty()) {
                clawMachine = new ClawMachine();
                clawMachines.add(clawMachine);
            }

            if (line.startsWith("Button")) {
                var x = line.substring(line.indexOf("X+") + 2, line.indexOf(","));
                var y = line.substring(line.indexOf("Y+") + 2);

                final var button = new LongCoordinate2D(Integer.parseInt(x), Integer.parseInt(y));

                if (line.contains("A:")) {
                    clawMachine.setButtonA(button);
                } else {
                    clawMachine.setButtonB(button);
                }
            } else if (line.startsWith("Prize: ")) {
                var x = line.substring(line.indexOf("X=") + 2, line.indexOf(","));
                var y = line.substring(line.indexOf("Y=") + 2);
                clawMachine.setTarget(new LongCoordinate2D(Integer.parseInt(x), Integer.parseInt(y)));
            }
        }

        return clawMachines;
    }

    private Long calcToken(List<ClawMachine> clawMachines) {
        var minimumTokens = 0L;

        for (var clawMachine : clawMachines) {
            var aX = clawMachine.getButtonA().x();
            var aY = clawMachine.getButtonA().y();
            var bX = clawMachine.getButtonB().x();
            var bY = clawMachine.getButtonB().y();
            var targetX = clawMachine.getTarget().x();
            var targetY = clawMachine.getTarget().y();

            var determinant = aX * bY - aY * bX;

            if (determinant != 0) {
                var numeratorA = targetX * bY - targetY * bX;
                var numeratorB = targetY * aX - targetX * aY;

                if (numeratorA % determinant == 0 && numeratorB % determinant == 0) {
                    var a = numeratorA / determinant;
                    var b = numeratorB / determinant;

                    minimumTokens += a * TOKENS_BUTTON_A + b * TOKENS_BUTTON_B;
                }
            }

        }

        return minimumTokens;
    }

    @Override
    protected void partTwo() {
        var clawMachines = parseInput(lines);
        clawMachines.forEach(clawMachine -> clawMachine.setTarget(
                new LongCoordinate2D(clawMachine.getTarget().x() + 10000000000000L, clawMachine.getTarget().y() + 10000000000000L)));
        var minimumTokens = calcToken(clawMachines);
        System.out.println("minimum tokens: " + minimumTokens);
    }

    @Data
    private static class ClawMachine {

        private LongCoordinate2D target;
        private LongCoordinate2D buttonA;
        private LongCoordinate2D buttonB;
    }

}
