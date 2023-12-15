package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2023_15")
public class D15 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var steps = lines.get(0).split(",");
        var sum = Arrays.stream(steps).mapToInt(this::runHASHAlgo).sum();
        System.out.println("sum of HASH algorithm results: " + sum);
    }

    private int runHASHAlgo(String s) {
        var number = 0;

        for (char c : s.toCharArray()) {
            number += c;
            number *= 17;
            number = number % 256;
        }

        return number;
    }

    @Override
    protected void partTwo() {
        var boxes = runHASHMAP();
        var focusingPower = boxes.entrySet().stream().mapToLong(this::calcFocusingPower).sum();
        System.out.println("focusing power: " + focusingPower);
    }

    private Map<Integer, List<Lens>> runHASHMAP() {
        var boxes = new HashMap<Integer, List<Lens>>();

        for (var step : lines.get(0).split(",")) {
            if (step.contains("=")) {
                performEqualOperation(step, boxes);
            } else {
                performDashOperation(step, boxes);
            }
        }

        return boxes;
    }

    private void performEqualOperation(String step, Map<Integer, List<Lens>> boxes) {
        var split = step.split("=");
        var focalLength = Integer.parseInt(split[1]);
        var lens = new Lens(split[0], focalLength);

        var boxId = runHASHAlgo(split[0]);
        var box = getOrCreateBox(boxId, boxes);

        if (box.contains(lens)) {
            var index = box.indexOf(lens);
            box.remove(index);
            box.add(index, lens);
        } else {
            box.add(lens);
        }
    }

    private void performDashOperation(String step, Map<Integer, List<Lens>> boxes) {
        var label = step.substring(0, step.length() - 1);
        var boxId = runHASHAlgo(label);
        var box = getOrCreateBox(boxId, boxes);
        var lens = new Lens(label, 0);
        box.remove(lens);
    }

    private List<Lens> getOrCreateBox(int boxId, Map<Integer, List<Lens>> boxes) {
        if (!boxes.containsKey(boxId)) {
            boxes.put(boxId, new ArrayList<>());
        }
        return boxes.get(boxId);
    }

    private long calcFocusingPower(Entry<Integer, List<Lens>> box) {
        var boxId = box.getKey();
        var sum = 0L;

        for (var lens : box.getValue()) {
            sum += (1L + boxId) * (box.getValue().indexOf(lens) + 1) * lens.focalLength;
        }

        return sum;
    }

    private record Lens(String label, int focalLength) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Lens lens = (Lens) o;
            return label.equals(lens.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }
    }

}
