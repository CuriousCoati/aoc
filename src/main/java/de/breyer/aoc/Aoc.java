package de.breyer.aoc;

import java.util.Scanner;

public class Aoc {

    public static void main(String[] args) {
        Aoc aoc = new Aoc();
        aoc.start();
    }

    private boolean exit = false;

    private void start() {
        Scanner in = new Scanner(System.in);

        do {
            System.out.println();
            System.out.println("Command:");
            String input = in.nextLine();
            processInput(input);
        } while (!exit);
    }

    private void processInput(String input) {
        if (input.equalsIgnoreCase("exit")) {
            exit = true;
        } else if (input.equalsIgnoreCase("registry")) {
            PuzzleRegistry.getInstance().printRegistry();
        } else {
            AbstractAocPuzzle puzzle = PuzzleRegistry.getInstance().getPuzzle(input);
            if (null != puzzle) {
                puzzle.run();
            } else {
                System.out.println("not found");
            }
        }
    }

}
