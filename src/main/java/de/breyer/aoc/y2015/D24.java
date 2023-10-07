package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import lombok.Data;
import lombok.Getter;

@AocPuzzle("2015_24")
public class D24 extends AbstractAocPuzzle {

    private final List<Integer> packages = new ArrayList<>();
    private int maxWeight;

    @Override
    protected void partOne() {
        parseInput(3);
        var idealConfiguration = findIdealConfiguration();
        System.out.println(idealConfiguration);
    }

    private void parseInput(int groupCount) {
        packages.clear();
        lines.stream().map(Integer::parseInt).forEach(packages::add);
        Collections.reverse(packages);
        maxWeight = packages.stream().mapToInt(Integer::intValue).sum() / groupCount;
    }

    private SleightState findIdealConfiguration() {
        var states = new Stack<SleightState>();
        states.add(new SleightState());

        SleightState idealState = null;

        do {
            var state = states.pop();

            if (state.getPackageIndex() < packages.size()) {
                var nextStates = calcNextState(state);

                for (var nextState : nextStates) {
                    if (null == idealState) {
                        states.add(nextState);
                    } else {
                        var idealGroupOneSize = idealState.getGroup().getSize();
                        var currentGroupOneSize = nextState.getGroup().getSize();

                        if (currentGroupOneSize <= idealGroupOneSize) {
                            states.add(nextState);
                        }
                    }
                }

            } else {
                if (state.getGroup().getWeight() == maxWeight) {
                    if (null == idealState) {
                        idealState = state;
                    } else {
                        var idealGroupOneSize = idealState.getGroup().getSize();
                        var currentGroupOneSize = state.getGroup().getSize();

                        if (idealGroupOneSize > currentGroupOneSize) {
                            idealState = state;
                        } else {
                            var idealQuantumEntanglement = idealState.getGroup().getQuantumEntanglement();
                            var currentQuantumEntanglement = state.getGroup().getQuantumEntanglement();

                            if (idealQuantumEntanglement > currentQuantumEntanglement) {
                                idealState = state;
                            }
                        }

                    }
                }
            }

        } while (!states.isEmpty());

        return idealState;
    }

    private List<SleightState> calcNextState(SleightState state) {
        List<SleightState> nextStates = new ArrayList<>();

        var currentPackage = packages.get(state.getPackageIndex());

        var nextState = new SleightState(state);
        nextStates.add(nextState);

        nextState = new SleightState(state);
        nextState.getGroup().addWeight(currentPackage);

        if (nextState.getGroup().getWeight() <= maxWeight) {
            nextStates.add(nextState);
        }

        return nextStates;
    }

    @Override
    protected void partTwo() {
        parseInput(4);
        var idealConfiguration = findIdealConfiguration();
        System.out.println(idealConfiguration);
    }

    private static class SleightState {

        @Getter
        private final PackageGroup group;

        @Getter
        private final int packageIndex;

        public SleightState() {
            packageIndex = 0;
            group = new PackageGroup();
        }

        public SleightState(SleightState state) {
            packageIndex = state.packageIndex + 1;
            group = new PackageGroup(state.getGroup());
        }

        @Override
        public String toString() {
            return "Group 1: %s".formatted(group);
        }
    }

    @Data
    private static class PackageGroup {

        private int weight;
        private long quantumEntanglement;
        private int size;

        public PackageGroup() {
            this.quantumEntanglement = 1;
        }

        public PackageGroup(PackageGroup group) {
            this.weight = group.weight;
            this.quantumEntanglement = group.quantumEntanglement;
            this.size = group.size;
        }

        @Override
        public String toString() {
            return "[size=%s, weight=%s, QE=%s]".formatted(size, weight, quantumEntanglement);
        }

        public void addWeight(int packageWeight) {
            size += 1;
            weight += packageWeight;
            quantumEntanglement *= packageWeight;
        }
    }

}
