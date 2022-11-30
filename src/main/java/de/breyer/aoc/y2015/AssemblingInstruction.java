package de.breyer.aoc.y2015;

public class AssemblingInstruction {

    private final String inputOne;
    private final String output;
    private String command;
    private String inputTwo;

    public AssemblingInstruction(String instruction) {
        String[] split = instruction.split(" ");

        if (5 == split.length) {
            this.command = split[1];
            this.inputOne = split[0];
            this.inputTwo = split[2];
            this.output = split[4];
        } else if (4 == split.length) {
            this.command = split[0];
            this.inputOne = split[1];
            this.output = split[3];
        } else if (3 == split.length) {
            this.inputOne = split[0];
            this.output = split[2];
        } else {
            throw new RuntimeException("cannon parse: " + instruction);
        }
    }

    public String getCommand() {
        return command;
    }

    public String getInputOne() {
        return inputOne;
    }

    public String getInputTwo() {
        return inputTwo;
    }

    public String getOutput() {
        return output;
    }

    private int parse(String value) {
        return Integer.parseInt(value);
    }

    public boolean needsBothInputs() {
        return null != inputTwo;
    }
}
