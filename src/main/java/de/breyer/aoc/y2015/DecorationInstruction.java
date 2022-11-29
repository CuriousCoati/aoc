package de.breyer.aoc.y2015;

import de.breyer.aoc.data.Point2D;

public class DecorationInstruction {

    private final String command;
    private final Point2D start;
    private final Point2D end;

    public DecorationInstruction(String command, String start, String end) {
        String[] startSplit = start.split(",");
        String[] endSplit = end.split(",");

        this.command = command;
        this.start = new Point2D(parse(startSplit[0]), parse(startSplit[1]));
        this.end = new Point2D(parse(endSplit[0]), parse(endSplit[1]));
    }

    public String getCommand() {
        return command;
    }

    public Point2D getStart() {
        return start;
    }

    public Point2D getEnd() {
        return end;
    }

    private int parse(String value) {
        return Integer.parseInt(value);
    }
}
