package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.utils.MathUtil;
import de.breyer.aoc.y2022.Pair;

@AocPuzzle("2023_08")
public class D08 extends AbstractAocPuzzle {

    private final Map<String, Pair<String, String>> nodes = new HashMap<>();
    private String instructions;

    @Override
    protected void partOne() {
        init();
        parseInput();
        var steps = followInstructions();
        System.out.println("steps to reach target: " + steps);
    }

    private void init() {
        nodes.clear();
        instructions = null;
    }

    private void parseInput() {
        instructions = lines.get(0);

        for (int i = 2; i < lines.size(); i++) {
            var line = lines.get(i);
            var startNode = line.substring(0, 3);
            var leftNode = line.substring(line.indexOf('(') + 1, line.indexOf('(') + 4);
            var rightNode = line.substring(line.indexOf(", ") + 2, line.indexOf(", ") + 5);

            nodes.put(startNode, new Pair<>(leftNode, rightNode));
        }
    }

    private int followInstructions() {
        var steps = 0;
        var currentNode = "AAA";

        do {
            var nextNodePair = nodes.get(currentNode);
            var instructionIndex = steps % instructions.length();
            var instruction = instructions.charAt(instructionIndex);

            if (instruction == 'L') {
                currentNode = nextNodePair.getFirst();
            } else {
                currentNode = nextNodePair.getSecond();
            }
            steps++;

        } while (!currentNode.equals("ZZZ"));

        return steps;
    }

    @Override
    protected void partTwo() {
        var startNodes = findStartNodes();
        var steps = calcNeededSteps(startNodes);
        var lcm = MathUtil.calculateLCM(steps);

        System.out.println("steps to reach target: " + lcm);
    }

    private List<String> findStartNodes() {
        var startNodes = new ArrayList<String>();
        for (var node : nodes.keySet()) {
            if (node.endsWith("A")) {
                startNodes.add(node);
            }
        }
        return startNodes;
    }

    private List<Integer> calcNeededSteps(List<String> startNodes) {
        var steps = 0;
        var currentNodes = new ArrayList<>(startNodes);
        var neededSteps = new ArrayList<Integer>();

        do {
            var instructionIndex = steps % instructions.length();
            var instruction = instructions.charAt(instructionIndex);
            steps++;

            var nextNodes = new ArrayList<String>();
            for (String currentNode : currentNodes) {
                var nextNodePair = nodes.get(currentNode);

                var nextNode = "";
                if (instruction == 'L') {
                    nextNode = nextNodePair.getFirst();
                } else {
                    nextNode = nextNodePair.getSecond();
                }

                if (!nextNode.endsWith("Z")) {
                    nextNodes.add(nextNode);
                } else {
                    neededSteps.add(steps);
                }
            }
            currentNodes = nextNodes;

        } while (!currentNodes.isEmpty());

        return neededSteps;
    }

}
