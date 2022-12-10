package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2022_10")
public class D10 extends AbstractAocPuzzle {

    private List<Integer> state;
    private int xRegister;

    @Override
    protected void partOne() {
        simulate();
        int sum = sumSignalStrengths();
        System.out.println("sum of signal strengths " + sum);
    }

    public void simulate() {
        xRegister = 1;
        state = new ArrayList<>();
        lines.forEach(this::execute);
    }

    public void execute(String command) {
        if (command.startsWith("noop")) {
            state.add(xRegister);
        } else if (command.startsWith("addx")) {
            int value = Integer.parseInt(command.substring(5));
            state.add(xRegister);
            state.add(xRegister);
            xRegister += value;
        }
    }

    private int sumSignalStrengths() {
        int sum = 0;
        for (int i = 20; i < 221; i += 40) {
            sum += state.get(i - 1) * i;
        }
        return sum;
    }

    @Override
    protected void partTwo() {
        for (int i = 1; i < 241; i++) {
            int value = state.get(i - 1);
            int rest = i % 40;
            int position = rest - 1;
            System.out.print(position >= value - 1 && position <= value + 1 ? "#" : '.');

            if (rest == 0) {
                System.out.println();
            }
        }
    }

}
