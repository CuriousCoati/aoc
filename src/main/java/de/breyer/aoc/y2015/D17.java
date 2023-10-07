package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_17")
public class D17 extends AbstractAocPuzzle {

    private static final int EGGNOG_VOLUME = 150;

    private List<Integer> containers;
    private List<Integer> usedContainers;

    @Override
    protected void partOne() {
        readContainerSizes();
        var combinations = countCombinations();
        System.out.println("combinations: " + combinations);
    }

    private void readContainerSizes() {
        containers = lines.stream().map(Integer::parseInt).toList();
    }

    private int countCombinations() {
        Map<String, Integer> cache = new HashMap<>();
        return countCombinationsRecursion(EGGNOG_VOLUME, containers, 0, cache);
    }

    private int countCombinationsRecursion(int remainingVolume, List<Integer> containers, int index, Map<String, Integer> cache) {
        if (remainingVolume == 0) {
            return 1;
        }

        if (index >= containers.size()) {
            return 0;
        }

        String cacheKey = remainingVolume + "-" + index;
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        int combinationsIfUsed = 0;
        if (remainingVolume >= containers.get(index)) {
            combinationsIfUsed = countCombinationsRecursion(remainingVolume - containers.get(index), containers, index + 1, cache);
        }

        int combinationsIfNotUsed = countCombinationsRecursion(remainingVolume, containers, index + 1, cache);

        int result = combinationsIfUsed + combinationsIfNotUsed;
        cache.put(cacheKey, result);
        return result;
    }

    @Override
    protected void partTwo() {
        var groupedAndCounted = countFewestCombinations();
        System.out.println("combinations with minimum container usage: " + groupedAndCounted);
    }

    private Map<Integer, Long> countFewestCombinations() {
        this.usedContainers = new ArrayList<>();
        countFewestCombinationsRecursion(EGGNOG_VOLUME, containers, 0, 0);
        return usedContainers.stream().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
    }

    private void countFewestCombinationsRecursion(int remainingVolume, List<Integer> containers, int index, int usedContainers) {
        if (remainingVolume == 0) {
            this.usedContainers.add(usedContainers);
            return;
        }

        if (index >= containers.size()) {
            return;
        }

        if (remainingVolume >= containers.get(index)) {
            countFewestCombinationsRecursion(remainingVolume - containers.get(index), containers, index + 1, usedContainers + 1);
        }

        countFewestCombinationsRecursion(remainingVolume, containers, index + 1, usedContainers);
    }

}
