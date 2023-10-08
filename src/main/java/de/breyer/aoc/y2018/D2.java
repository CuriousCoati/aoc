package de.breyer.aoc.y2018;

import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2022.Pair;

import java.util.HashMap;
import java.util.Optional;

@AocPuzzle("2018_02")
public class D2 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var checksum = calcChecksum();
        System.out.println("checksum is: " + checksum);
    }

    private long calcChecksum() {
        long countOfTwoAppearances = 0;
        long countOfThreeAppearances = 0;

        for (String line : lines) {
            var charCounts = new HashMap<Character, Integer>();
            for (char character : line.toCharArray()) {
                int count = charCounts.getOrDefault(character, 0);
                count++;
                charCounts.put(character, count);
            }

            boolean twoAppearances = false;
            boolean threeAppearances = false;

            for (var entry : charCounts.entrySet()) {
                if (entry.getValue() == 2) {
                    twoAppearances = true;
                    continue;
                } else if (entry.getValue() == 3) {
                    threeAppearances = true;
                    continue;
                }

                if (twoAppearances && threeAppearances) {
                    break;
                }
            }

            if (twoAppearances) {
                countOfTwoAppearances++;
            }

            if (threeAppearances) {
                countOfThreeAppearances++;
            }
        }

        return countOfTwoAppearances * countOfThreeAppearances;
    }

    @Override
    protected void partTwo() {
        var foundIds = findIdsDifferentByOneChar();
        if (foundIds.isPresent()) {
            var commonPart = filterCommonPart(foundIds.get());
            System.out.println("the common part is: " +  commonPart);
        } else {
            System.out.println("found no IDs which differ only by one char");
        }

    }

    private Optional<Pair<String, String>> findIdsDifferentByOneChar() {
        for (int i = 0; i < lines.size() - 1; i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                var lineOne = lines.get(i);
                var lineTwo = lines.get(j);
                if (differentByOneChar(lineOne, lineTwo)) {
                    return Optional.of(new Pair<>(lineOne, lineTwo));
                }
            }
        }

        return Optional.empty();
    }

    private boolean differentByOneChar(String idOne, String idTwo) {
        int differences = 0;

        for (int i = 0; i < idOne.length(); i++) {
            if (idOne.charAt(i) != idTwo.charAt(i)) {
                differences++;
                if (differences > 1) {
                    return false;
                }
            }
        }

        return differences == 1;
    }

    private String filterCommonPart(Pair<String, String> foundIds) {
        var builder = new StringBuilder();
        for (int i = 0; i < foundIds.getFirst().length(); i++) {
            if (foundIds.getFirst().charAt(i) == foundIds.getSecond().charAt(i)) {
                builder.append(foundIds.getFirst().charAt(i));
            }
        }
        return builder.toString();
    }

}
