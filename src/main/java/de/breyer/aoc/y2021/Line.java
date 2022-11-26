package de.breyer.aoc.y2021;

import de.breyer.aoc.data.Point2D;

public class Line {

    private final Point2D start;
    private final Point2D end;

    public Line(String line) {
        String[] points = line.split(" -> ");
        String[] startPoint = points[0].split(",");
        String[] endPoint = points[1].split(",");

        start = new Point2D(Integer.parseInt(startPoint[0]), Integer.parseInt(startPoint[1]));
        end = new Point2D(Integer.parseInt(endPoint[0]), Integer.parseInt(endPoint[1]));
    }

    public boolean isStraight() {
        return start.getX() == end.getX() || start.getY() == end.getY();
    }

    public void markPointsOnMap(int[][] map) {
        int x = start.getX();
        int y = start.getY();
        int compareX = Integer.compare(end.getX(), start.getX());
        int compareY = Integer.compare(end.getY(), start.getY());

        do {
            markOnMap(map, new Point2D(x, y));
            if (compareX != 0) {
                x = compareX > 0 ? x + 1 : x - 1;
            }
            if (compareY != 0) {
                y = compareY > 0 ? y + 1 : y - 1;
            }
        } while (x != end.getX() || y != end.getY());
        markOnMap(map, end);
    }

    private void markOnMap(int[][] map, Point2D point) {
        try {
            int count = map[point.getX()][point.getY()];
            map[point.getX()][point.getY()] = count + 1;
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Point: " + point.getX() + "," + point.getY());
            throw e;
        }
    }
}
