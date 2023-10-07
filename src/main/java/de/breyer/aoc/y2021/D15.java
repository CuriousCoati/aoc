package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Node;

@AocPuzzle("2021_15")
public class D15 extends AbstractAocPuzzle {

    private int width = 100;
    private int height = 100;
    private Map<String, Node> nodes;
    private PriorityQueue<Node> openList;
    private List<Node> closedList;

    @Override
    protected void partOne() {
        processInput();
        evaluateNodes();
        printResult();
    }

    @Override
    protected void partTwo() {
        processInput();
        scaleMap();
        evaluateNodes();
        printResult();
    }

    private void processInput() {
        int x = 0, y = 0;
        nodes = new HashMap<>();

        for (String line : lines) {
            for (char character : line.toCharArray()) {
                Node node = new Node(x, y, Integer.parseInt("" + character));
                nodes.put(node.getName(), node);
                x++;
            }
            x = 0;
            y++;
        }
    }

    private void scaleMap() {
        List<Node> newNodes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i != 0 || j != 0) {
                    for (Node node : nodes.values()) {
                        int value = node.getValue() + i + j;
                        if (value > 9) {
                            value -= 9;
                        }
                        Node newNode = new Node(node.getX() + (width * i), node.getY() + (height * j), value);
                        newNodes.add(newNode);
                    }
                }
            }
        }

        for (Node node : newNodes) {
            nodes.put(node.getName(), node);
        }
        width *= 5;
        height *= 5;
    }

    private void evaluateNodes() {
        Node startNode = nodes.get(Node.buildName(0,0));
        Node endNode = nodes.get(Node.buildName(width - 1, height - 1));

        openList = new PriorityQueue<>(Comparator.comparingInt(Node::getPriority));
        closedList = new ArrayList<>();

        openList.add(startNode);

        do {
            Node node = openList.remove();
            if (node == endNode) {
                System.out.println("path found");
                return;
            }
            closedList.add(node);
            expandNode(node);
        } while (!openList.isEmpty());

        System.out.println("no path found");
    }

    private void expandNode(Node node) {
        List<Node> successors = new ArrayList<>();
        if (node.getX() - 1 >= 0) {
            successors.add(nodes.get(Node.buildName(node.getX() - 1, node.getY())));
        }
        if (node.getX() + 1 < width) {
            successors.add(nodes.get(Node.buildName(node.getX() + 1, node.getY())));
        }
        if (node.getY() - 1 >= 0) {
            successors.add(nodes.get(Node.buildName(node.getX(), node.getY() - 1)));
        }
        if (node.getY() + 1 < height) {
            successors.add(nodes.get(Node.buildName(node.getX(), node.getY() + 1)));
        }

        for (Node successor : successors) {
            if (closedList.contains(successor)) {
                continue;
            }

            int cost = node.getCost() + successor.getValue();

            if (openList.contains(successor) && cost >= successor.getCost()) {
                continue;
            }

            successor.setPredecessor(node);
            successor.setCost(cost);

            int remainingDistance = (width - 1 - successor.getX()) + (height - 1 - successor.getY());

            successor.setPriority(cost + remainingDistance);
            openList.remove(successor);
            openList.add(successor);
        }

    }

    private void printResult() {
        Node startNode = nodes.get(Node.buildName(0,0));
        Node node = nodes.get(Node.buildName(width - 1, height - 1));
        long totalRisk = 0;

        do {
            System.out.print(node.getValue());
            if (node != startNode) {
                totalRisk += node.getValue();
                System.out.print(" -> ");
            }
            node = node.getPredecessor();
        } while (node != null);

        System.out.println();
        System.out.println("total risk: " + totalRisk);
    }
}
