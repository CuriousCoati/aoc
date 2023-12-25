package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2023_25")
public class D25 extends AbstractAocPuzzle {

    private Map<String, List<Connection>> components;

    @Override
    protected void partOne() {
        var cuts = kargerMinCut();
        parseInput();
        var result = cutAndCountSize(cuts);
        System.out.println("result: " + result);
    }

    private void parseInput() {
        components = new HashMap<>();

        for (var line : lines) {
            var split = line.split(": ");
            var mainComponent = getOrCreateComponent(split[0]);

            for (var connectedComponentName : split[1].split(" ")) {
                var connectedComponent = getOrCreateComponent(connectedComponentName);

                mainComponent.add(new Connection(split[0], connectedComponentName));
                connectedComponent.add(new Connection(connectedComponentName, split[0]));
            }
        }
    }

    public List<Connection> getOrCreateComponent(String name) {
        if (!components.containsKey(name)) {
            components.put(name, new ArrayList<>());
        }
        return components.get(name);
    }

    public List<Connection> kargerMinCut() {
        for (int i = 0; i < 1000; i++) {
            parseInput();

            while (components.size() > 2) {
                var keyList = components.keySet().stream().toList();
                new Random().nextInt(components.keySet().size());

                var connections = components.get(keyList.get(new Random().nextInt(keyList.size())));
                var randomConnection = connections.get(new Random().nextInt(connections.size()));
                mergeComponents(randomConnection);
            }

            var first = components.values().iterator().next();
            if (first.size() == 3) {
                return first;
            }

        }

        throw new RuntimeException("min cut of 3 not found");
    }

    private void mergeComponents(Connection connection) {
        var source = connection.comp1;
        var target = connection.comp2;

        components.get(source).addAll(components.get(target));
        components.remove(target);

        for (var entry : components.entrySet()) {
            for (var currentConnection : entry.getValue()) {
                if (entry.getKey().equals(source)) {
                    currentConnection.comp1 = source;
                }

                if (currentConnection.comp2.equals(target)) {
                    currentConnection.comp2 = source;
                }
            }
        }

        components.get(source).removeIf(c -> c.comp2.equals(source));
    }

    private int cutAndCountSize(List<Connection> cuts) {
        for (var cut : cuts) {
            components.get(cut.origComp1).remove(cut);
            components.get(cut.origComp2).remove(cut);
        }

        return countSize(cuts.get(0).origComp1) * countSize(cuts.get(0).origComp2);
    }

    private int countSize(String component) {
        var visitedComponents = new ArrayList<String>();

        var set = new HashSet<String>();
        set.add(component);

        do {
            var current = set.iterator().next();
            set.remove(current);
            visitedComponents.add(current);

            for (var connection : components.get(current)) {
                if (!set.contains(connection.comp2) && !visitedComponents.contains(connection.comp2)) {
                    set.add(connection.comp2);
                }
            }

        } while (!set.isEmpty());

        return visitedComponents.size();
    }

    @Override
    protected void partTwo() {
    }

    private static class Connection {

        private final String origComp1;
        private final String origComp2;
        private String comp1;
        private String comp2;

        public Connection(String comp1, String comp2) {
            this.origComp1 = comp1;
            this.origComp2 = comp2;
            this.comp1 = comp1;
            this.comp2 = comp2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Connection that = (Connection) o;
            return (Objects.equals(origComp1, that.origComp1) && Objects.equals(origComp2, that.origComp2)) || (
                    Objects.equals(origComp1, that.origComp2) && Objects.equals(
                            origComp2,
                            that.origComp1));
        }

        @Override
        public int hashCode() {
            if (origComp1.compareTo(origComp2) < 0) {
                return Objects.hash(origComp1, origComp2);
            } else {
                return Objects.hash(origComp2, origComp1);
            }
        }
    }

}
