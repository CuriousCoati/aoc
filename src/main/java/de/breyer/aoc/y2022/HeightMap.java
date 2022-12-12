package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;
import de.breyer.aoc.data.Node;
import de.breyer.aoc.data.Point2D;

public class HeightMap {

    private final Node[][] map;

    public HeightMap(int height, int width) {
        map = new Node[height][width];
    }

    public void fillInHeight(int y, int x, char character) {
        map[y][x] = new Node(x, y, character);
    }

    public int findWay(Point2D start, char targetChar, Function<Integer, Boolean> canReach) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getPriority));
        List<Node> closedList = new ArrayList<>();
        List<Node> possibleEnds = new ArrayList<>();

        openList.add(map[start.getY()][start.getX()]);

        do {
            Node node = openList.remove();
            if (targetChar == node.getValue()) {
                possibleEnds.add(node);
                continue;
            }
            closedList.add(node);
            expandNode(node, openList, closedList, canReach);
        } while (!openList.isEmpty());

        possibleEnds.sort(Comparator.comparingInt(this::countSteps));
        return countSteps(possibleEnds.get(0));
    }

    private int countSteps(Node node) {
        Node currentNode = node;
        int steps = 0;
        do {
            currentNode = currentNode.getPredecessor();
            steps++;
        } while (null != currentNode.getPredecessor());
        return steps;
    }

    private void expandNode(Node node, PriorityQueue<Node> openList, List<Node> closedList, Function<Integer, Boolean> canReach) {
        List<Node> validNeighbours = getValidNeighbours(node, closedList, canReach);

        for (Node successor : validNeighbours) {

            int cost = node.getCost() + 1;

            if (openList.contains(successor) && cost >= successor.getCost()) {
                continue;
            }

            successor.setPredecessor(node);
            successor.setCost(cost);

            int remainingDistance = (map[0].length - 1 - successor.getX()) + (map.length - 1 - successor.getY());

            successor.setPriority(cost + remainingDistance);
            openList.remove(successor);
            openList.add(successor);
        }

    }

    private List<Node> getValidNeighbours(Node currentNode, List<Node> closedList, Function<Integer, Boolean> canReach) {
        List<Node> validNeighbours = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            int x = direction.getXExpression().apply(currentNode.getX(), 1);
            int y = direction.getYExpression().apply(currentNode.getY(), 1);

            // check neighbour is on map
            if (x >= 0 && x < map[0].length && y >= 0 && y < map.length) {
                Node neighbour = map[y][x];

                // check wasn't visited
                if (!closedList.contains(neighbour)) {
                    char currentHeight = (char) currentNode.getValue();
                    char neighbourHeight = (char) neighbour.getValue();

                    if (currentHeight == 'S') {
                        currentHeight = 'a';
                    }
                    if (currentHeight == 'E') {
                        currentHeight = 'z';
                    }
                    if (neighbourHeight == 'S') {
                        neighbourHeight = 'a';
                    }
                    if (neighbourHeight == 'E') {
                        neighbourHeight = 'z';
                    }

                    int diff = currentHeight - neighbourHeight;

                    // check can reach neighbour
                    if (canReach.apply(diff)) {
                        validNeighbours.add(neighbour);
                    }
                }
            }
        }

        return validNeighbours;
    }
}
