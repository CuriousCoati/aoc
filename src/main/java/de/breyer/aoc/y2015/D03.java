package de.breyer.aoc.y2015;

import java.util.HashMap;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2015_03")
public class D03 extends AbstractAocPuzzle {

    private HashMap<Point2D, Integer> visitedHouses;

    @Override
    protected void partOne() {
        followInstructions(false);
        System.out.println("houses delivered: " + visitedHouses.size());
    }

    private void followInstructions(boolean useRoboSanta) {
        visitedHouses = new HashMap<>();
        Point2D positionSanta = new Point2D(0, 0);
        Point2D positionRoboSanta = new Point2D(0, 0);

        visitHouse(positionSanta);
        if (useRoboSanta) {
            visitHouse(positionRoboSanta);
        }

        boolean roboTurn = false;
        for (char character : lines.get(0).toCharArray()) {
            Point2D position = useRoboSanta && roboTurn ? positionRoboSanta : positionSanta;

            Point2D newPosition = interpretInstruction(position, character);
            visitHouse(newPosition);

            if (useRoboSanta && roboTurn) {
                positionRoboSanta = newPosition;
            } else {
                positionSanta = newPosition;
            }

            roboTurn = !roboTurn;
        }
    }

    private Point2D interpretInstruction(Point2D position, char character) {
        int x = position.getX();
        int y = position.getY();

        switch (character) {
            case '^' -> y += 1;
            case '<' -> x -= 1;
            case '>' -> x += 1;
            case 'v' -> y -= 1;
        }

        return new Point2D(x, y);
    }

    private void visitHouse(Point2D position) {
        int visits = visitedHouses.getOrDefault(position, 0);
        visits++;
        visitedHouses.put(position, visits);
    }

    @Override
    protected void partTwo() {
        followInstructions(true);
        System.out.println("houses delivered: " + visitedHouses.size());
    }

}
