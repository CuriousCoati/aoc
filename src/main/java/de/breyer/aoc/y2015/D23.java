package de.breyer.aoc.y2015;

import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import lombok.Data;

@AocPuzzle("2015_23")
public class D23 extends AbstractAocPuzzle {

    private final Register registerA = new Register();
    private final Register registerB = new Register();
    private int position;

    @Override
    protected void partOne() {
        init();
        runProgram();
        System.out.printf("value in registers a %s, b %s%n", registerA, registerB);
    }

    private void init() {
        registerA.setValue(0);
        registerB.setValue(0);
        position = 0;
    }

    private void runProgram() {
        do {
            var instructionParts = lines.get(position).split(" ");

            switch (instructionParts[0]) {
                case "hlf" -> {
                    var r = instructionParts[1].equals("a") ? registerA : registerB;
                    half(r);
                    position++;
                }
                case "tpl" -> {
                    var r = instructionParts[1].equals("a") ? registerA : registerB;
                    triple(r);
                    position++;
                }
                case "inc" -> {
                    var r = instructionParts[1].equals("a") ? registerA : registerB;
                    increment(r);
                    position++;
                }
                case "jmp" -> {
                    var offset = Integer.parseInt(instructionParts[1]);
                    jump(offset);
                }
                case "jie" -> {
                    var r = instructionParts[1].equals("a,") ? registerA : registerB;
                    var offset = Integer.parseInt(instructionParts[2]);
                    jumpIfEven(r, offset);
                }
                case "jio" -> {
                    var r = instructionParts[1].equals("a,") ? registerA : registerB;
                    var offset = Integer.parseInt(instructionParts[2]);
                    jumpIfOne(r, offset);
                }
            }

        } while (position < lines.size());
    }

    private void half(Register register) {
        register.setValue(register.getValue() / 2);
    }

    private void triple(Register register) {
        register.setValue(register.getValue() * 3);
    }

    private void increment(Register register) {
        register.setValue(register.getValue() + 1);
    }

    private void jump(int offset) {
        position += offset;
    }

    private void jumpIfEven(Register register, int offset) {
        if (register.getValue() % 2 == 0) {
            jump(offset);
        } else {
            position++;
        }
    }

    private void jumpIfOne(Register register, int offset) {
        if (register.getValue() == 1) {
            jump(offset);
        } else {
            position++;
        }
    }

    @Override
    protected void partTwo() {
        init();
        registerA.setValue(1);
        runProgram();
        System.out.printf("value in registers a %s, b %s%n", registerA, registerB);
    }

    @Data
    private static class Register {

        private long value;

        @Override
        public String toString() {
            return "" + value;
        }
    }

}
