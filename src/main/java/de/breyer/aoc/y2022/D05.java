package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;
import de.breyer.aoc.TriConsumer;

@AocPuzzle("2022_05")
public class D05 extends AbstractAocPuzzle {

    private List<Stack<Character>> ship;

    @Override
    protected void partOne() {
        prepare();
        readInput();
        followInstructions(this::createMover9000);
        printTopCrates();
    }

    private void prepare() {
        ship = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ship.add(new Stack<>());
        }
    }

    private void readInput() {
        for (int i = 7; i >= 0; i--) {
            String line = lines.get(i);
            for (int j = 1; j <= 33; j += 4) {
                if (line.length() < j) {
                    break;
                }
                char character = line.charAt(j);
                if (' ' != character) {
                    ship.get((j - 1) / 4).push(character);
                }
            }
        }
    }

    private void followInstructions(TriConsumer<Integer, Integer, Integer> consumer) {
        for (int i = 10; i < lines.size(); i++) {
            String[] split = lines.get(i).split(" ");
            int moves = Integer.parseInt(split[1]);
            int startStack = Integer.parseInt(split[3]) - 1;
            int endStack = Integer.parseInt(split[5]) - 1;

            consumer.accept(moves, startStack, endStack);
        }
    }

    private void printTopCrates() {
        ship.forEach(stack -> System.out.print(stack.peek()));
        System.out.println();
    }

    private void createMover9000(int moves, int startStack, int endStack) {
        for (int j = 0; j < moves; j++) {
            ship.get(endStack).push(ship.get(startStack).pop());
        }
    }

    private void createMover9001(int moves, int startStack, int endStack) {
        Stack<Character> tmp = new Stack<>();
        for (int j = 0; j < moves; j++) {
            tmp.push(ship.get(startStack).pop());
        }
        while (!tmp.isEmpty()) {
            ship.get(endStack).push(tmp.pop());
        }
    }

    @Override
    protected void partTwo() {
        prepare();
        readInput();
        followInstructions(this::createMover9001);
        printTopCrates();
    }

}
