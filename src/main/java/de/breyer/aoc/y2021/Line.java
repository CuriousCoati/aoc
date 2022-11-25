package de.breyer.aoc.y2021;

public class Line {

    private final Point start;
    private final Point end;

    public Line(String line) {
        String[] points = line.split(" -> ");
        String[] startPoint = points[0].split(",");
        String[] endPoint = points[1].split(",");

        start = new Point(Integer.parseInt(startPoint[0]), Integer.parseInt(startPoint[1]));
        end = new Point(Integer.parseInt(endPoint[0]), Integer.parseInt(endPoint[1]));
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
            markOnMap(map, new Point(x, y));
            if (compareX != 0) {
                x = compareX > 0 ? x + 1 : x - 1;
            }
            if (compareY != 0) {
                y = compareY > 0 ? y + 1 : y - 1;
            }
        } while (x != end.getX() || y != end.getY());
        markOnMap(map, end);
    }

    private void markOnMap(int[][] map, Point point) {
        try {
            int count = map[point.getX()][point.getY()];
            map[point.getX()][point.getY()] = count + 1;
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Point: " + point.getX() + "," + point.getY());
            throw e;
        }
    }
}
