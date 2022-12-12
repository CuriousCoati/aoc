package de.breyer.aoc.y2022;

import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2022_12")
public class D12 extends AbstractAocPuzzle {

    private Point2D start;
    private Point2D target;

    @Override
    protected void partOne() {
        HeightMap map = parseInput();
        int steps = map.findWay(start, 'E', (diff) -> diff >= -1);
        System.out.println("needed " + steps + " steps");
    }

    private HeightMap parseInput() {
        HeightMap map = new HeightMap(lines.size(), lines.get(0).length());

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);
                map.fillInHeight(y, x, character);
                if ('S' == character) {
                    start = new Point2D(x, y);
                } else if ('E' == character) {
                    target = new Point2D(x, y);
                }
            }
        }

        return map;
    }

    @Override
    protected void partTwo() {
        HeightMap map = parseInput();
        int steps = map.findWay(target, 'a', (diff) -> diff <= 1);
        System.out.println("needed " + steps + " steps");
    }

}
