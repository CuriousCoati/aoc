package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;
import de.breyer.aoc.y2022.Pair;

@AocPuzzle("2015_19")
public class D19 extends AbstractAocPuzzle {

    private List<Pair<String, String>> replacements;
    private String medicine;

    @Override
    protected void partOne() {
        parseInput();
        var molecules = doReplacements(medicine);
        var distinctMolecules = molecules.stream().distinct().count();
        System.out.println("found molecules " + molecules.size() + " distinct " + distinctMolecules);
    }

    private void parseInput() {
        replacements = new ArrayList<>();

        for (int i = 0; i < lines.size() - 2; i++) {
            var split = lines.get(i).split(" => ");
            replacements.add(new Pair<>(split[0], split[1]));
        }

        medicine = lines.get(lines.size() - 1);
    }

    private List<String> doReplacements(String input) {
        List<String> molecules = new ArrayList<>();

        for (Pair<String, String> replacement : replacements) {
            String search = replacement.getFirst();
            int searchLength = search.length();

            for (int i = 0; i <= input.length() - searchLength; i++) {
                String substring = input.substring(i, i + searchLength);
                if (substring.equals(search)) {
                    StringBuilder builder = new StringBuilder();

                    if (i > 0) {
                        builder.append(input, 0, i);
                    }
                    builder.append(replacement.getSecond());
                    if (i + searchLength < input.length()) {
                        builder.append(input, i + searchLength, input.length());
                    }

                    molecules.add(builder.toString());
                }
            }

        }

        return molecules;
    }

    @Override
    protected void partTwo() {
        var steps = findMedicine();
        System.out.println("fewest steps to medicine: " + steps);
    }

    private int findMedicine() {
        int step = 0;
        boolean exit;
        String molecule = medicine;

        do {
            molecule = doReverseReplacement(molecule);
            exit = molecule.equals("e");

            step++;
        } while (!exit);

        return step;
    }

    private String doReverseReplacement(String input) {
        for (Pair<String, String> replacement : replacements) {
            String search = replacement.getSecond();
            int searchLength = search.length();

            for (int i = 0; i <= input.length() - searchLength; i++) {
                String substring = input.substring(i, i + searchLength);
                if (substring.equals(search)) {
                    StringBuilder builder = new StringBuilder();

                    if (i > 0) {
                        builder.append(input, 0, i);
                    }
                    builder.append(replacement.getFirst());
                    if (i + searchLength < input.length()) {
                        builder.append(input, i + searchLength, input.length());
                    }

                    return builder.toString();
                }
            }

        }

        throw new IllegalStateException("no replacement found '" + input + "'");
    }

}
