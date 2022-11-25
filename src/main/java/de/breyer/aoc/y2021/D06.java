package de.breyer.aoc.y2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_06")
public class D06 extends AbstractAocPuzzle {

    private final List<Integer> fishes = new ArrayList<>();
    private final BigInteger[] increasePerDay = new BigInteger[7];
    private final BigInteger[] growth = new BigInteger[7];
    private BigInteger fishPopulation;
    private BigInteger day80;
    private BigInteger day256;

    @Override
    protected void partOne() {
        parseInput();
        prepareSimulation();
        startSimulation();
        System.out.println("day 80: " + day80);
    }

    @Override
    protected void partTwo() {
        System.out.println("day 256: " + day256);
    }

    private void parseInput() {
        String[] split = lines.get(0).split(",");
        Arrays.stream(split).forEach(character -> fishes.add(Integer.parseInt(character)));
    }

    private void prepareSimulation() {
        fishPopulation = BigInteger.valueOf(fishes.size());
        for (int i = 0; i < 7; i++) {
            increasePerDay[i] = BigInteger.ZERO;
            growth[i] = BigInteger.ZERO;
        }
        for (int fish : fishes) {
            increasePerDay[fish] = increasePerDay[fish].add(BigInteger.ONE);
        }
    }

    private void startSimulation() {
        int counter = 0;
        for (int day = 0; day < 256; day++) {
            if (day == 80) {
                day80 = fishPopulation;
            }

            BigInteger newFishes = increasePerDay[counter];
            fishPopulation = fishPopulation.add(newFishes);

            int idxChildsGiveBirth = counter+2;
            if (idxChildsGiveBirth > 6) {
                idxChildsGiveBirth -= 7;
            }
            growth[idxChildsGiveBirth] = newFishes;

            increasePerDay[counter] = increasePerDay[counter].add(growth[counter]);

            counter = counter == 6 ? 0 : counter + 1;
        }
        day256 = fishPopulation;
    }
}
