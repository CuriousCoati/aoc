package de.breyer.aoc.y2018;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2018_03")
public class D3 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var rectangles = parseInput();
        var grid = fillGrid(rectangles);
        var overlappingSquareInches = countOverlappingSquareInches(grid);
        System.out.println(overlappingSquareInches + " square inches overlap");
    }

    private List<Rectangle> parseInput() {
        var result = new ArrayList<Rectangle>();

        for (var line : lines) {
            var split = line.split(" ");
            var coordinateSplit = split[2].split(",");
            var sizeSplit = split[3].split("x");

            var id = Integer.parseInt(split[0].substring(1));
            var x = Integer.parseInt(coordinateSplit[0]);
            var y = Integer.parseInt(coordinateSplit[1].substring(0, coordinateSplit[1].length() - 1));
            var width = Integer.parseInt(sizeSplit[0]);
            var height = Integer.parseInt(sizeSplit[1]);

            result.add(new Rectangle(id, x, y, width, height));
        }

        return result;
    }

    private int[][] fillGrid(List<Rectangle> rectangles) {
        var grid = new int[2000][2000];

        for (var rectangle : rectangles) {
            for (int y = rectangle.y(); y < rectangle.y() + rectangle.height(); y++) {
                for (int x = rectangle.x(); x < rectangle.x() + rectangle.width(); x++) {
                    grid[y][x]++;
                }
            }
        }

        return grid;
    }

    private int countOverlappingSquareInches(int[][] grid) {
        var count = 0;
        for (int[] rows : grid) {
            for (int cell : rows) {
                if (cell > 1) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    protected void partTwo() {
        var rectangles = parseInput();
        var rectangle = findNotOverlappingRectangle(rectangles);

        System.out.println("rectangle " + rectangle.id() + " is not overlapping");
    }

    private Rectangle findNotOverlappingRectangle(List<Rectangle> rectangles) {
        for (var rectangle1 : rectangles) {
            var overlapping = false;

            for (var rectangle2 : rectangles) {
                if (rectangle1 == rectangle2) {
                    continue;
                }

                if (!(rectangle1.endX() < rectangle2.x() || rectangle1.x() > rectangle2.endX())) {
                    if (!(rectangle1.endY() < rectangle2.y() || rectangle1.y() > rectangle2.endY())) {
                        overlapping = true;
                        break;
                    }

                }
            }

            if (!overlapping) {
                return rectangle1;
            }
        }

        return null;
    }

}
