package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_06")
public class D06 extends AbstractAocPuzzle {

    private final boolean[][] gridBool = new boolean[1000][1000];
    private final int[][] gridInt = new int[1000][1000];
    private final List<DecorationInstruction> instructions = new ArrayList<>();

    @Override
    protected void partOne() {
        processInput();
        instructions.forEach(this::followInstructionOne);
        int count = countLights();
        System.out.println(count + " light are lit");
    }

    private void processInput() {
        for (String line : lines) {
            String[] split = line.split(" ");

            DecorationInstruction instruction;
            if ("#".equals(split[0])) {
                break;
            } else if ("turn".equals(split[0])) {
                instruction = new DecorationInstruction(split[0] + " " + split[1], split[2], split[4]);
            } else {
                instruction = new DecorationInstruction(split[0], split[1], split[3]);
            }
            instructions.add(instruction);
        }
    }

    private void followInstructionOne(DecorationInstruction instruction) {
        for (int x = instruction.getStart().getX(); x <= instruction.getEnd().getX(); x++) {
            for (int y = instruction.getStart().getY(); y <= instruction.getEnd().getY(); y++) {
                if ("turn on".equals(instruction.getCommand())) {
                    gridBool[x][y] = true;
                } else if ("turn off".equals(instruction.getCommand())) {
                    gridBool[x][y] = false;
                } else {
                    gridBool[x][y] = !gridBool[x][y];
                }
            }
        }
    }

    private int countLights() {
        int count = 0;
        for (boolean[] column : gridBool) {
            for (boolean lit : column) {
                if (lit) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    protected void partTwo() {
        instructions.forEach(this::followInstructionTwo);
        int totalBrightness = countBrightness();
        System.out.println("total brightness: " + totalBrightness);
    }

    private void followInstructionTwo(DecorationInstruction instruction) {
        for (int x = instruction.getStart().getX(); x <= instruction.getEnd().getX(); x++) {
            for (int y = instruction.getStart().getY(); y <= instruction.getEnd().getY(); y++) {
                int brightness = gridInt[x][y];
                if ("turn on".equals(instruction.getCommand())) {
                    brightness++;
                } else if ("turn off".equals(instruction.getCommand())) {
                    brightness = brightness == 0 ? 0 : brightness - 1;
                } else {
                    brightness += 2;
                }
                gridInt[x][y] = brightness;
            }
        }
    }

    private int countBrightness() {
        int totalBrightness = 0;
        for (int[] column : gridInt) {
            for (int brightness : column) {
                totalBrightness += brightness;
            }
        }
        return totalBrightness;
    }

}
