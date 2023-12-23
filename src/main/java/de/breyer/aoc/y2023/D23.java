package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import de.breyer.aoc.y2022.Direction;
import de.breyer.aoc.y2022.Pair;

@AocPuzzle("2023_23")
public class D23 extends AbstractAocPuzzle {

    private Point2D startPosition;
    private Point2D endPosition;
    private char[][] map;

    private static String generateCacheKey(Direction d, Point2D p) {
        return d.toString() + "_" + p.getX() + "," + p.getY();
    }

    @Override
    protected void partOne() {
        parseInput();
        var steps = findLongestHike(true);
        System.out.println("the longest hike count consists of " + steps + " steps");
    }

    private void parseInput() {
        map = new char[lines.size()][lines.get(0).length()];
        startPosition = new Point2D(lines.get(0).indexOf('.'), 0);
        endPosition = new Point2D(lines.get(lines.size() - 1).indexOf('.'), lines.size() - 1);

        for (var y = 0; y < lines.size(); y++) {
            map[y] = lines.get(y).toCharArray();
        }
    }

    private int findLongestHike(boolean mindSlopes) {
        var pathCache = new HashMap<String, PathCacheable>();

        var stack = new Stack<SearchState>();
        stack.push(new SearchState(startPosition));

        var longestHike = Integer.MIN_VALUE;

        do {
            var current = stack.pop();

            if (current.position.equals(endPosition)) {
                fillCache(current, pathCache, false);

                longestHike = Math.max(longestHike, current.length);
                continue;
            }

            var cacheKey = generateCacheKey(current.direction, current.position);
            if (pathCache.containsKey(cacheKey)) {
                var cached = pathCache.get(cacheKey);

                if (!current.visitedPoints.contains(cached.endPoint)) {
                    stack.push(new SearchState(cached, current));
                }

                continue;
            }

            var nextSteps = new ArrayList<Pair<Direction, Point2D>>();

            for (var direction : Direction.values()) {
                if (Direction.isOpposite(current.direction, direction)) {
                    continue;
                }

                var nextX = direction.getXExpression().apply(current.position.getX(), 1);
                var nextY = direction.getYExpression().apply(current.position.getY(), 1);

                if (nextX < 0 || nextY < 0 || nextX >= map[0].length || nextY >= map.length) {
                    continue;
                }

                var neighbour = map[nextY][nextX];
                if (mindSlopes) {
                    if ('.' == neighbour ||
                            ('>' == neighbour && direction == Direction.RIGHT) ||
                            ('^' == neighbour && direction == Direction.UP) ||
                            ('v' == neighbour && direction == Direction.DOWN) ||
                            ('<' == neighbour && direction == Direction.LEFT)) {
                        nextSteps.add(new Pair<>(direction, new Point2D(nextX, nextY)));
                    }
                } else {
                    if (List.of('.', '>', '^', 'v', '<').contains(neighbour)) {
                        nextSteps.add(new Pair<>(direction, new Point2D(nextX, nextY)));
                    }
                }
            }

            var pathEnded = nextSteps.size() > 1;
            if (pathEnded) {
                fillCache(current, pathCache, true);
            }

            for (var step : nextSteps) {
                var direction = step.getFirst();
                var nextPosition = step.getSecond();

                if (!current.visitedPoints.contains(nextPosition)) {
                    stack.add(new SearchState(direction, nextPosition, current, pathEnded));
                }
            }

        } while (!stack.isEmpty());

        return longestHike;
    }

    private void fillCache(SearchState current, Map<String, PathCacheable> pathCache, boolean withReverse) {
        if (null != current.startOfPath && !pathCache.containsKey(current.cacheKeyForPath)) {
            pathCache.put(current.cacheKeyForPath, new PathCacheable(current.direction, current.position, current.pathLength));

            if (withReverse) {
                var reversedDirection = Direction.getOpposite(current.direction);
                var previousX = reversedDirection.getXExpression().apply(current.position.getX(), 1);
                var previousY = reversedDirection.getYExpression().apply(current.position.getY(), 1);

                var previousPosition = new Point2D(previousX, previousY);

                var reversedKey = generateCacheKey(reversedDirection, previousPosition);
                pathCache.put(reversedKey, new PathCacheable(reversedDirection, current.startOfPath, current.pathLength));
            }
        } else if (null != current.startOfPath) {
            System.out.println("found cache for " + current.cacheKeyForPath);
        }
    }

    @Override
    protected void partTwo() {
        var steps = findLongestHike(false);
        System.out.println("the longest hike count consists of " + steps + " steps");
    }

    private static class SearchState {

        private final String cacheKeyForPath;
        private final Point2D startOfPath;
        private final Direction direction;
        private final Point2D position;
        private final int length;
        private final int pathLength;
        private final List<Point2D> visitedPoints = new ArrayList<>();

        public SearchState(Point2D position) {
            this.direction = Direction.DOWN;
            this.position = position;
            this.length = 0;

            this.pathLength = 0;
            this.startOfPath = null;
            this.cacheKeyForPath = null;
        }

        public SearchState(Direction direction, Point2D newPosition, SearchState oldState, boolean pathEnded) {
            this.direction = direction;
            this.position = newPosition;
            this.visitedPoints.addAll(oldState.visitedPoints);
            this.length = oldState.length + 1;

            if (pathEnded) {
                this.pathLength = 0;
                this.visitedPoints.add(oldState.position);
                this.startOfPath = oldState.position;
                this.cacheKeyForPath = generateCacheKey(direction, position);
            } else {
                this.pathLength = oldState.pathLength + 1;
                this.startOfPath = oldState.startOfPath;
                this.cacheKeyForPath = oldState.cacheKeyForPath;
            }
        }

        public SearchState(PathCacheable cached, SearchState oldState) {
            this.direction = cached.direction;
            this.position = cached.endPoint;
            this.visitedPoints.addAll(oldState.visitedPoints);
            this.visitedPoints.add(cached.endPoint);
            this.length = oldState.length + cached.length;

            this.pathLength = 0;
            this.startOfPath = null;
            this.cacheKeyForPath = null;
        }

    }

    private record PathCacheable(Direction direction, Point2D endPoint, int length) {

    }

}
