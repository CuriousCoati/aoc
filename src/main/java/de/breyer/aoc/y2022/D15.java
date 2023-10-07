package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.utils.MathUtil;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2022_15")
public class D15 extends AbstractAocPuzzle {

    private int highestX;
    private int lowestX;
    private List<Point2D> beacons;

    @Override
    protected void partOne() {
        List<Sensor> sensors = parseInput();
        long countCovered = countCovered(sensors);
        System.out.println(countCovered + " places are covered");
    }

    private List<Sensor> parseInput() {
        highestX = 0;
        lowestX = 0;
        beacons = new ArrayList<>();

        List<Sensor> sensors = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" ");

            int xSensor = Integer.parseInt(split[2].split("=")[1].replace(",", ""));
            int ySensor = Integer.parseInt(split[3].split("=")[1].replace(":", ""));
            int xBeacon = Integer.parseInt(split[8].split("=")[1].replace(",", ""));
            int yBeacon = Integer.parseInt(split[9].split("=")[1].replace(":", ""));

            Point2D sensorPos = new Point2D(xSensor, ySensor);
            Point2D beaconPos = new Point2D(xBeacon, yBeacon);
            int distance = MathUtil.manhattenDistance(sensorPos, beaconPos);

            highestX = Integer.max(highestX, sensorPos.getX() + distance);
            lowestX = Integer.min(lowestX, sensorPos.getX() - distance);

            sensors.add(new Sensor(sensorPos, distance));
            beacons.add(beaconPos);
        }
        return sensors;
    }

    private long countCovered(List<Sensor> sensors) {
        boolean finished = false;
        int x = lowestX;
        int count = 0;

        do {
            Point2D point = new Point2D(x, 2000000);
            for (Sensor sensor : sensors) {
                if (sensor.covers(point) && !beacons.contains(point)) {
                    count++;
                    break;
                }
            }

            x++;

            if (x == highestX) {
                finished = true;
            }

        } while (!finished);

        return count;
    }

    private Point2D findUncoveredPosition(List<Sensor> sensors) {
        Point2D uncovered = null;

        for (int y = 0; y <= 4000000; y++) {
            boolean finished = false;
            int x = 0;
            do {
                boolean covered = false;
                Point2D point = new Point2D(x, y);

                for (Sensor sensor : sensors) {
                    if (sensor.covers(point)) {
                        int coveredInRow = sensor.canCoverInRowOfPoint(point);
                        x += coveredInRow + 1;
                        covered = true;
                        break;
                    }
                }

                if (!covered) {
                    uncovered = point;
                    finished = true;
                }

                if (x >= 4000000) {
                    finished = true;
                }

            } while (!finished);
        }

        return uncovered;
    }

    @Override
    protected void partTwo() {
        List<Sensor> sensors = parseInput();
        Point2D uncovered = findUncoveredPosition(sensors);
        long tuningFrequency = uncovered.getX() * 4000000L + uncovered.getY();
        System.out.println("tuning frequency: " + tuningFrequency);
    }
}
