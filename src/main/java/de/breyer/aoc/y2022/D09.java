package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2022_09")
public class D09 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        int visited = simulate(2);
        System.out.println("tail visited " + visited + " positions");
    }

    private int simulate(int size) {
        Set<Point2D> visitedByTail = new HashSet<>();
        List<Point2D> rope = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            rope.add(new Point2D(0, 0));
        }
        visitedByTail.add(rope.get(rope.size() - 1));

        for (String line : lines) {
            String[] split = line.split(" ");
            int moves = Integer.parseInt(split[1]);
            Direction direction = Direction.valueOf(split[0].charAt(0));

            for (int i = 0; i < moves; i++) {
                Point2D newPosition = moveFirstKnot(rope.get(0), direction);
                rope.remove(0);
                rope.add(0, newPosition);

                for (int j = 1; j < rope.size(); j++) {
                    if (needKnotToMove(rope.get(j - 1), rope.get(j))) {
                        newPosition = moveKnot(rope.get(j - 1), rope.get(j));
                        rope.remove(j);
                        rope.add(j, newPosition);

                        if (j == rope.size() - 1) {
                            visitedByTail.add(newPosition);
                        }
                    }
                }
            }
        }

        return visitedByTail.size();
    }

    private Point2D moveFirstKnot(Point2D knot, Direction direction) {
        int x = direction.getXExpression().apply(knot.getX(), 1);
        int y = direction.getYExpression().apply(knot.getY(), 1);
        return new Point2D(x, y);
    }

    private boolean needKnotToMove(Point2D previous, Point2D current) {
        int xDiff = Math.abs(previous.getX() - current.getX());
        int yDiff = Math.abs(previous.getY() - current.getY());

        return xDiff > 1 || yDiff > 1;
    }

    private Point2D moveKnot(Point2D previous, Point2D current) {
        int xDiff = Integer.compare(previous.getX(), current.getX());
        int yDiff = Integer.compare(previous.getY(), current.getY());
        return new Point2D(current.getX() + xDiff, current.getY() + yDiff);
    }

    @Override
    protected void partTwo() {
        int visited = simulate(10);
        System.out.println("tail visited " + visited + " positions");
    }

}
