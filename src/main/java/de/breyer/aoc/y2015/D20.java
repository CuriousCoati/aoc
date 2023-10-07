package de.breyer.aoc.y2015;

import java.util.HashMap;
import java.util.function.BiPredicate;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_20")
public class D20 extends AbstractAocPuzzle {

    private static final int TARGET = 34000000;

    @Override
    protected void partOne() {
        var lowestHouseNumber = findLowestHouseNumberToReachTwo(10, (i, j) -> i * j <= 786240);
        System.out.println("lowest house number: " + lowestHouseNumber);
    }

    @Override
    protected void partTwo() {
        var lowestHouseNumber = findLowestHouseNumberToReachTwo(11, (i, j) -> i <= 50);
        System.out.println("lowest house number: " + lowestHouseNumber);
    }

    private int findLowestHouseNumberToReachTwo(int presentMultiplier, BiPredicate<Integer, Integer> stopCondition) {
        var cache = new HashMap<Integer, Integer>();
        var houseNumber = 0;
        var presents = 0;

        do {
            houseNumber++;

            for (int i = 1; stopCondition.test(i, houseNumber); i++) {
                var curHouseNumber = houseNumber * i;
                var curPresentsAtHouse = cache.getOrDefault(curHouseNumber, 0);
                curPresentsAtHouse += houseNumber * presentMultiplier;
                cache.put(curHouseNumber, curPresentsAtHouse);
            }

            presents = cache.get(houseNumber);
            cache.remove(houseNumber);
        } while (presents < TARGET);

        return houseNumber;
    }

}
