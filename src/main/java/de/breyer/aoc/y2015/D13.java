package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_13")
public class D13 extends AbstractAocPuzzle {

    private final List<String> names = new ArrayList<>();
    private final Map<String, Integer> rules = new HashMap<>();

    @Override
    protected void partOne() {
        parseInput();
        List<List<String>> permutations = generatePermutations();
        int maxHappiness = findMaxHappiness(permutations);
        System.out.println("max happiness: " + maxHappiness);
    }

    private void parseInput() {
        names.clear();
        rules.clear();

        for (String line : lines) {
            String[] split = line.split(" ");
            String personOne = split[0];
            String personTwo = split[10].substring(0, split[10].length() - 1);
            int happinessDiff = Integer.parseInt(split[3]);
            if ("lose".equals(split[2])) {
                happinessDiff *= -1;
            }

            if (!names.contains(personOne)) {
                names.add(personOne);
            }
            rules.put(personOne + "_" + personTwo, happinessDiff);
        }
    }

    private List<List<String>> generatePermutations() {
        List<List<String>> permutations = new ArrayList<>();
        names.forEach(name -> permutations.add(List.of(name)));
        return addPermutations(permutations, 0);
    }

    private List<List<String>> addPermutations(List<List<String>> permutations, int depth) {
        List<List<String>> newPermutations = new ArrayList<>();
        for (List<String> permutation : permutations) {
            for (String name : names) {
                if (!permutation.contains(name)) {
                    List<String> newPermutation = new ArrayList<>(permutation);
                    newPermutation.add(name);
                    newPermutations.add(newPermutation);
                }
            }
        }

        return depth < names.size() - 2 ? addPermutations(newPermutations, ++depth) : newPermutations;
    }

    private int findMaxHappiness(List<List<String>> permutations) {
        int maxHappiness = 0;

        for (List<String> permutation : permutations) {
            int happiness = 0;

            for (int i = 0; i < permutation.size(); i++) {
                String personOne = permutation.get(i);
                String personTwo = permutation.get((i + 1) % permutation.size());

                happiness += rules.get(personOne + "_" + personTwo);
                happiness += rules.get(personTwo + "_" + personOne);
            }

            maxHappiness = Math.max(happiness, maxHappiness);
        }

        return maxHappiness;
    }

    @Override
    protected void partTwo() {
        addMe();
        List<List<String>> permutations = generatePermutations();
        int maxHappiness = findMaxHappiness(permutations);
        System.out.println("max happiness: " + maxHappiness);
    }

    private void addMe() {
        for (String name : names) {
            rules.put("me_" + name, 0);
            rules.put(name + "_me", 0);
        }
        names.add("me");
    }

}
