package de.breyer.aoc.data;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Point2DTest {

    @Test
    public void getNeighbourCoordinatesWithDiagonals() {
        var point = new Point2D(0, 0);
        var neighbours = point.getNeighbourCoordinates(true);

        Assertions.assertEquals(8, neighbours.size(), "should have 8 neighbours");

        var expectedNeighbours = List.of(
                new Point2D(-1, -1),
                new Point2D(0, -1),
                new Point2D(1, -1),
                new Point2D(1, 0),
                new Point2D(1, 1),
                new Point2D(0, 1),
                new Point2D(-1, 1),
                new Point2D(-1, 0)
        );

        for (var expectedNeighbour : expectedNeighbours) {
            Assertions.assertTrue(neighbours.contains(expectedNeighbour), "neighbour coordinate " + expectedNeighbour + " not found");
        }
    }

    @Test
    public void getNeighbourCoordinatesWithoutDiagonals() {
        var point = new Point2D(0, 0);
        var neighbours = point.getNeighbourCoordinates(true);

        Assertions.assertEquals(8, neighbours.size(), "should have 4 neighbours");

        var expectedNeighbours = List.of(
                new Point2D(0, -1),
                new Point2D(1, 0),
                new Point2D(0, 1),
                new Point2D(-1, 0)
        );

        for (var expectedNeighbour : expectedNeighbours) {
            Assertions.assertTrue(neighbours.contains(expectedNeighbour), "neighbour coordinate " + expectedNeighbour + " not found");
        }
    }
}
