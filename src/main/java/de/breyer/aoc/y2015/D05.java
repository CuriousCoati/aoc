package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_05")
public class D05 extends AbstractAocPuzzle {

    private static final List<Character> VOWELS = new ArrayList<>();
    private static final List<String> BLACKLIST = new ArrayList<>();

    static {
        VOWELS.add('a');
        VOWELS.add('e');
        VOWELS.add('i');
        VOWELS.add('o');
        VOWELS.add('u');

        BLACKLIST.add("ab");
        BLACKLIST.add("cd");
        BLACKLIST.add("pq");
        BLACKLIST.add("xy");
    }

    @Override
    protected void partOne() {
        long count = countNiceLines(false);
        System.out.println(count + " lines are nice");
    }

    private long countNiceLines(boolean useNewRules) {
        return lines.stream().filter(line -> useNewRules ? isNiceNew(line) : isNiceOld(line)).count();
    }

    private boolean isNiceOld(String line) {
        int vowelCount = 0;
        boolean doubleLetter = false;

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (VOWELS.contains(character)) {
                vowelCount++;
            }

            if (i > 0 && line.charAt(i - 1) == character) {
                doubleLetter = true;
            }
        }

        boolean blacklistHit = BLACKLIST.stream().anyMatch(line::contains);

        return vowelCount >= 3 && doubleLetter && !blacklistHit;
    }

    @Override
    protected void partTwo() {
        long count = countNiceLines(true);
        System.out.println(count + " lines are nice");
    }

    private boolean isNiceNew(String line) {
        boolean doublePair = false;
        boolean doubleLetter = false;

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (i > 1 && line.charAt(i - 2) == character) {
                doubleLetter = true;
            }

            if (i > 0 && line.indexOf(line.substring(i - 1, i + 1), i + 1) != -1) {
                doublePair = true;
            }
        }

        return doublePair && doubleLetter;
    }

}
