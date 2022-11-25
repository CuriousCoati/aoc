package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alu {

    private final Map<String, Integer> variables = new HashMap<>();
    private final List<String> instructions = new ArrayList<>();

    public Alu() {
        variables.put("w", 0);
        variables.put("x", 0);
        variables.put("y", 0);
        variables.put("z", 0);
    }

    public void addInstruction(String instruction) {
        instructions.add(instruction);
    }

    private void reset() {
        variables.put("w", 0);
        variables.put("x", 0);
        variables.put("y", 0);
        variables.put("z", 0);
    }

    public boolean runMonad(int[] input) {
        reset();
        int inputCounter = 0;
        for (String instruction : instructions) {
            String[] split = instruction.split(" ");
            int value = 0;

            if ("inp".equals(split[0])) {
                value = input[inputCounter];
                inputCounter++;
            } else if ("add".equals(split[0])) {
                int summandOne = variables.get(split[1]);
                int summandTwo = parseOrGetVar(split[2]);
                value = summandOne + summandTwo;
            } else if ("mul".equals(split[0])) {
                int factorOne = variables.get(split[1]);
                int factorTwo = parseOrGetVar(split[2]);
                value = factorOne * factorTwo;
            } else if ("div".equals(split[0])) {
                int dividend = variables.get(split[1]);
                int divisor = parseOrGetVar(split[2]);
                value = dividend / divisor;
            } else if ("mod".equals(split[0])) {
                int dividend = variables.get(split[1]);
                int divisor = parseOrGetVar(split[2]);
                value = dividend % divisor;
            } else if ("eql".equals(split[0])) {
                int digitOne = variables.get(split[1]);
                int digitTwo = parseOrGetVar(split[2]);
                value = digitOne == digitTwo ? 1 : 0;
            }

            variables.put(split[1], value);
        }
        return variables.get("z") == 0;
    }

    public int parseOrGetVar(String input) {
        if ("w".equals(input) || "x".equals(input) || "y".equals(input) || "z".equals(input)) {
            return variables.get(input);
        } else {
            return Integer.parseInt(input);
        }
    }

}
