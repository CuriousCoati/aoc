package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_01")
public class D01 extends AbstractAocPuzzle {

    private final List<Elf> elves = new ArrayList<>();

    @Override
    protected void partOne() {
        procssInput();
        elves.sort((a, b) -> Integer.compare(b.calcTotalCarlories(), a.calcTotalCarlories()));
        System.out.println("most calories: " + elves.get(0).calcTotalCarlories());
    }

    private void procssInput() {
        Elf elf = new Elf();
        elves.add(elf);
        for (String line : lines) {
            if (line.isEmpty()) {
                elf = new Elf();
                elves.add(elf);
            } else {
                elf.addCalorieItem(Integer.parseInt(line));
            }
        }
    }

    @Override
    protected void partTwo() {
        System.out.println("most calories: " + elves.stream().limit(3).map(Elf::calcTotalCarlories).reduce(0, Integer::sum));
    }

}
