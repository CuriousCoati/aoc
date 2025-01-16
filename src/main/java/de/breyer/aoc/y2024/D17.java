package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2024_17")
public class D17 extends AbstractAocPuzzle {

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    @Override
    protected void partOne() {
        var computer = readComputer(lines);
        computer.runProgram();
        System.out.println(computer.getOutputDisplay());
    }

    private List<Integer> readProgram(List<String> input) {
        var program = new ArrayList<Integer>();
        var split = input.get(4).substring(9).split(",");
        Arrays.stream(split).map(Integer::parseInt).forEach(program::add);
        return program;
    }

    private ChronospatialComputer readComputer(List<String> input) {
        var program = readProgram(input);

        var registerA = Integer.parseInt(input.get(0).substring(12));
        return new ChronospatialComputer(program, registerA);
    }

    @Override
    protected void partTwo() {
        var program = readProgram(lines);
        findValuesForRegisterA(program);
    }

    public void findValuesForRegisterA(List<Integer> program) {
        var executor = Executors.newFixedThreadPool(NUM_THREADS);

        var min = 105734770000000L;
        var max = 105734780000000L;
        var range = Math.abs((max - min) / NUM_THREADS);
        for (var i = 0; i < NUM_THREADS; i++) {
            var start = min + i * range;
            var end = (i == NUM_THREADS - 1) ? max : start + range;

            executor.submit(() -> bruteForceRange(start, end, program));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void bruteForceRange(long start, long end, List<Integer> program) {
        for (var candidate = start; candidate < end; candidate++) {
            if (testRegisterA(candidate, program)) {
                System.out.println("found register A: " + candidate);
                break;
            }
        }
    }

    private boolean testRegisterA(long candidate, List<Integer> program) {
        var computer = new ChronospatialComputer(program, candidate);
        computer.runProgram();
        return computer.output.equals(program);
    }

    private static class ChronospatialComputer {

        private long registerA;
        private long registerB = 0;
        private long registerC = 0;
        private final List<Integer> program = new ArrayList<>();
        private final List<Integer> output = new ArrayList<>();
        private int instructionPointer = 0;

        public ChronospatialComputer(List<Integer> program, long registerA) {
            this.program.addAll(program);
            this.registerA = registerA;
        }

        public void runProgram() {
            output.clear();

            while (instructionPointer < program.size()) {
                var opCode = program.get(instructionPointer);
                var operand = program.get(instructionPointer + 1);

                var function = parseOpCode(opCode);
                function.accept(operand);

                if (opCode != 3) {
                    instructionPointer += 2;
                }
            }
        }

        private Consumer<Integer> parseOpCode(int opCode) {
            return switch (opCode) {
                case 0 -> this::adv;
                case 1 -> this::bxl;
                case 2 -> this::bst;
                case 3 -> this::jnz;
                case 4 -> this::bxc;
                case 5 -> this::out;
                case 6 -> this::bdv;
                case 7 -> this::cdv;
                default -> throw new IllegalArgumentException("Unexpected opCode value: " + opCode);
            };
        }

        private void adv(int operand) {
            registerA = (long) (registerA / Math.pow(2, interpretComboOperand(operand)));
        }

        private void bxl(int operand) {
            registerB = registerB ^ operand;
        }

        private void bst(int operand) {
            registerB = (interpretComboOperand(operand) % 8) & 7;
        }

        private void jnz(int operand) {
            if (0 != registerA) {
                instructionPointer = operand;
            } else {
                instructionPointer += 2;
            }
        }

        private void bxc(int operand) {
            registerB = registerB ^ registerC;
        }

        private void out(int operand) {
            output.add((int) (interpretComboOperand(operand) % 8));
        }

        private void bdv(int operand) {
            registerB = (long) (registerA / Math.pow(2, interpretComboOperand(operand)));
        }

        private void cdv(int operand) {
            registerC = (long) (registerA / Math.pow(2, interpretComboOperand(operand)));
        }

        private long interpretComboOperand(int operand) {
            return switch (operand) {
                case 0, 1, 2, 3 -> operand;
                case 4 -> registerA;
                case 5 -> registerB;
                case 6 -> registerC;
                case 7 -> throw new IllegalArgumentException("operand 7 is reserved and should not appear");
                default -> throw new IllegalArgumentException("Unexpected operand value: " + operand);
            };
        }

        public String getOutputDisplay() {
            return output.stream().map(Object::toString).collect(Collectors.joining(","));
        }
    }

}
