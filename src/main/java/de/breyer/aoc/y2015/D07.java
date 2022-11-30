package de.breyer.aoc.y2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2015_07")
public class D07 extends AbstractAocPuzzle {

    private final Map<String, Integer> wires = new HashMap<>();

    @Override
    protected void partOne() {
        List<AssemblingInstruction> instructions = convertInput();
        followInstructions(instructions);
        System.out.println("wire a has value " + wires.get("a"));
    }

    private List<AssemblingInstruction> convertInput() {
        return lines.stream().map(AssemblingInstruction::new).collect(Collectors.toList());
    }

    private void followInstructions(List<AssemblingInstruction> instructions) {
        int idx = 0;
        do {
            AssemblingInstruction instruction = instructions.get(idx);

            Integer inputOne = getInputValue(instruction.getInputOne());
            Integer inputTwo = getInputValue(instruction.getInputTwo());

            if (null != inputOne && (!instruction.needsBothInputs() || null != inputTwo)) {
                processCommand(inputOne, inputTwo, instruction);
                instructions.remove(instruction);
            } else {
                idx++;
            }

            idx = idx == instructions.size() ? 0 : idx;
        } while (!instructions.isEmpty());
    }

    private void processCommand(Integer inputOne, Integer inputTwo, AssemblingInstruction instruction) {
        int value;
        if ("RSHIFT".equals(instruction.getCommand())) {
            value = inputOne >> inputTwo;
        } else if ("LSHIFT".equals(instruction.getCommand())) {
            value = inputOne << inputTwo;
        } else if ("AND".equals(instruction.getCommand())) {
            value = inputOne & inputTwo;
        } else if ("OR".equals(instruction.getCommand())) {
            value = inputOne | inputTwo;
        } else if ("NOT".equals(instruction.getCommand())) {
            value = ~inputOne;
        } else {
            value = inputOne;
        }

        wires.putIfAbsent(instruction.getOutput(), value);
    }

    private Integer getInputValue(String input) {
        if (null == input) {
            return null;
        } else if (isNumber(input)) {
            return Integer.parseInt(input);
        } else {
            return wires.getOrDefault(input, null);
        }
    }

    private boolean isNumber(String input) {
        boolean isNumber = true;
        for (char character : input.toCharArray()) {
            if (!Character.isDigit(character)) {
                isNumber = false;
                break;
            }
        }
        return isNumber;
    }

    @Override
    protected void partTwo() {
        int a = wires.get("a");
        wires.clear();
        wires.put("b", a);
        List<AssemblingInstruction> instructions = convertInput();
        followInstructions(instructions);
        System.out.println("wire a has value " + wires.get("a"));
    }

}
