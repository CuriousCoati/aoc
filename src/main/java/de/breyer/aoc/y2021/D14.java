package de.breyer.aoc.y2021;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_14")
public class D14 extends AbstractAocPuzzle {

    private final Map<String, String> pairInsertionRules = new HashMap<>();
    private Map<String, Long> pairCount;
    private String polymerTemplate;

    @Override
    protected void partOne() {
        processInput();
        convertTemplateToPairs();
        processRules(10);
        calculateResult();
    }

    @Override
    protected void partTwo() {
        processRules(30);
        calculateResult();
    }

    private void processInput() {
        int counter = 0;

        for (String line : lines) {
            if (counter == 0) {
                polymerTemplate = line;
            } else if (counter > 1) {
                String[] ruleSplit = line.split(" -> ");
                pairInsertionRules.put(ruleSplit[0], ruleSplit[1]);
            }
            counter++;
        }
    }

    private void convertTemplateToPairs() {
        pairCount = new HashMap<>();
        for (int idx = 0; idx < polymerTemplate.length() -1; idx++) {
            String pair = polymerTemplate.substring(idx, idx + 2);
            createOrIncrease(pairCount, pair, 1L);
        }
    }

    private void processRules(int steps) {
        for (int step = 0; step < steps; step++) {
            final Map<String, Long> pairCountNextStep = new HashMap<>();
            for (Entry<String, Long> entry : pairCount.entrySet()) {
                if (pairInsertionRules.containsKey(entry.getKey())) {
                    String insert = pairInsertionRules.get(entry.getKey());

                    createOrIncrease(pairCountNextStep, entry.getKey().charAt(0) + insert, entry.getValue());
                    createOrIncrease(pairCountNextStep, insert + entry.getKey().charAt(1), entry.getValue());
                } else {
                    pairCountNextStep.put(entry.getKey(), entry.getValue());
                }
            }

            pairCount = pairCountNextStep;
        }
    }

    private void createOrIncrease(Map<String, Long> map, String key, long increment) {
        long count = map.getOrDefault(key, 0L);
        count += increment;
        map.put(key, count);
    }

    private void calculateResult() {
        Map<String, Long> elementCount = new HashMap<>();
        for (Entry<String, Long> entry : pairCount.entrySet()) {
            createOrIncrease(elementCount, "" + entry.getKey().charAt(0), entry.getValue());
        }

        createOrIncrease(elementCount, "" + polymerTemplate.charAt(polymerTemplate.length() - 1), 1L);

        long mostCommon = -1, leastCommon = -1;

        for (Long count : elementCount.values()) {
            if (-1 == mostCommon && -1 == leastCommon) {
                mostCommon = count;
                leastCommon = count;
            } else {
                if (mostCommon < count) {
                    mostCommon = count;
                }
                if (leastCommon > count) {
                    leastCommon = count;
                }
            }
        }

        System.out.println("Result is: " + (mostCommon - leastCommon));
    }
}
