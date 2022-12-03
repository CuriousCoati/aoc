package de.breyer.aoc.y2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;
import de.breyer.aoc.ListUtil;

@AocPuzzle("2022_03")
public class D03 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        int sumPriority = lines.stream().map(this::splitInHalf).map(this::calcPriority).reduce(0, Integer::sum);
        System.out.println("sum of priorities: " + sumPriority);
    }

    private String[] splitInHalf(String line) {
        final int mid = line.length() / 2;
        return new String[]{line.substring(0, mid), line.substring(mid)};
    }

    private int calcPriority(String[] halves) {
        Set<Character> wrongCharacters = new HashSet<>();
        for (char character : halves[0].toCharArray()) {
            if (-1 != halves[1].indexOf(character)) {
                wrongCharacters.add(character);
            }
        }
        return wrongCharacters.stream().map(this::getPriority).reduce(0, Integer::sum);
    }

    private int getPriority(char character) {
        return Character.isUpperCase(character) ? character - 38 : character - 96;
    }

    @Override
    protected void partTwo() {
        int sumPriority = ListUtil.subGroups(lines, 3).map(this::findBadgePriority).reduce(0, Integer::sum);
        System.out.println("sum of badge priorities: " + sumPriority);
    }

    private int findBadgePriority(List<String> subGroup) {
        for (char character : subGroup.get(0).toCharArray()) {
            if (-1 != subGroup.get(1).indexOf(character) && -1 != subGroup.get(2).indexOf(character)) {
                return getPriority(character);
            }
        }
        return 0;
    }

}
