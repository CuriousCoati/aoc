package de.breyer.aoc.y2021;

import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2021_02")
public class D02 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        SubmarineOne submarine = new SubmarineOne();
        for (String command : lines) {
            submarine.executeCommand(command);
        }
        System.out.println(submarine.getPosition());
    }

    @Override
    protected void partTwo() {
        SubmarineTwo submarine = new SubmarineTwo();
        for (String command : lines) {
            submarine.executeCommand(command);
        }
        System.out.println(submarine.getPosition());
    }

}
