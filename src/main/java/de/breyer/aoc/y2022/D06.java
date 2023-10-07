package de.breyer.aoc.y2022;

import java.util.HashSet;
import java.util.Set;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_06")
public class D06 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        int processedChars = findPacketMarker(4);
        System.out.println("chars process before first start-of-packet marker: " + processedChars);
    }

    private int findPacketMarker(int distinctCharCount) {
        String line = lines.get(0);
        for (int i = distinctCharCount - 1; i <= line.length(); i++) {
            Set<Character> distinctChars = new HashSet<>();

            for (int j = 0; j < distinctCharCount; j++) {
                distinctChars.add(line.charAt(i - j));
            }

            if (distinctChars.size() == distinctCharCount) {
                return i + 1;
            }
        }

        return -1;
    }

    @Override
    protected void partTwo() {
        int processedChars = findPacketMarker(14);
        System.out.println("chars process before first start-of-packet marker: " + processedChars);
    }

}
