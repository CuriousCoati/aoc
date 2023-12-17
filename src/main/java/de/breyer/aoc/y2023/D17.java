package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.y2022.Direction;

@AocPuzzle("2023_17")
public class D17 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var cityMap = parseInput();
        var heatLoss = findPath(cityMap, 0, 3);
        System.out.println("minimized heat loss: " + heatLoss);
    }

    private int[][] parseInput() {
        var map = new int[lines.size()][lines.get(0).length()];

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                map[y][x] = Integer.parseInt("" + lines.get(y).charAt(x));
            }
        }

        return map;
    }

    private int findPath(int[][] map, int minStepsInDirection, int maxStepsInDirection) {
        var visited = new HashSet<String>();
        var queue = new PriorityQueue<Node>();
        queue.add(new Node());

        do {
            var current = queue.poll();
            var id = current.generateId();

            if (!visited.contains(id)) {
                visited.add(id);
                if (current.position.getX() == map[0].length - 1 && current.position.getY() == map.length - 1) {
                    if (current.stepsInDirection >= minStepsInDirection) {
                        return current.heatLoss;
                    }
                } else {
                    queue.addAll(calcNextNodes(current, map, minStepsInDirection, maxStepsInDirection));
                }
            }
        } while (!queue.isEmpty());

        return -1;
    }

    private List<Node> calcNextNodes(Node currentNode, int[][] map, int minStepsInDirection, int maxStepsInDirection) {
        var nextNodes = new ArrayList<Node>();

        for (var direction : Direction.values()) {
            if (null == currentNode.direction || !Direction.isOpposite(currentNode.direction, direction)) {
                if (null == currentNode.direction ||
                        (currentNode.direction == direction && currentNode.stepsInDirection < maxStepsInDirection) ||
                        (currentNode.direction != direction && currentNode.stepsInDirection >= minStepsInDirection)) {

                    var nextX = direction.getXExpression().apply(currentNode.position.getX(), 1);
                    var nextY = direction.getYExpression().apply(currentNode.position.getY(), 1);

                    if (nextX >= 0 && nextX < map[0].length && nextY >= 0 && nextY < map.length) {

                        var position = new Point2D(nextX, nextY);
                        var heatLoss = currentNode.heatLoss + map[nextY][nextX];
                        var stepsInDirection = direction == currentNode.direction ? currentNode.stepsInDirection + 1 : 1;

                        nextNodes.add(new Node(position, heatLoss, direction, stepsInDirection));
                    }
                }
            }
        }

        return nextNodes;
    }

    @Override
    protected void partTwo() {
        var cityMap = parseInput();
        var heatLoss = findPath(cityMap, 4, 10);
        System.out.println("minimized heat loss: " + heatLoss);
    }

    private static class Node implements Comparable<Node> {

        private final Point2D position;
        private final Direction direction;
        private final int stepsInDirection;
        private final int heatLoss;

        public Node() {
            this(new Point2D(0, 0), 0, null, 0);
        }

        public Node(Point2D position, int heatLoss, Direction direction, int stepsInDirection) {
            this.position = position;
            this.heatLoss = heatLoss;
            this.direction = direction;
            this.stepsInDirection = stepsInDirection;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.heatLoss, other.heatLoss);
        }

        public String generateId() {
            return position.getX() + "," + position.getY() + "," + direction + "," + stepsInDirection;
        }
    }

}
