package de.breyer.aoc.y2024;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@AocPuzzle("2024_14")
@Slf4j
public class D14 extends AbstractAocPuzzle {

    private List<String> input;
    private int width;
    private int height;

    @Override
    protected void partOne() {
        config(false);
        var robots = parseInput();
        moveRobotsHundredTimes(robots);
        var safetyFactor = calcSafetyFactor(robots);
        System.out.println("safety factor: " + safetyFactor);
    }

    private void config(boolean test) {
        if (test) {
            input = testLinesPartOne;
            width = 11;
            height = 7;
        } else {
            input = lines;
            width = 101;
            height = 103;
        }
    }

    private List<Robot> parseInput() {
        var robots = new ArrayList<Robot>();

        for (var line : input) {
            var split = line.split(" ");
            var position = parsePoint(split[0]);
            var velocity = parsePoint(split[1]);
            robots.add(new Robot(position, velocity));
        }

        return robots;
    }

    private Point2D parsePoint(String pointStr) {
        var x = pointStr.substring(pointStr.indexOf("=") + 1, pointStr.indexOf(","));
        var y = pointStr.substring(pointStr.indexOf(",") + 1);
        return new Point2D(x, y);
    }

    private void moveRobotsHundredTimes(List<Robot> robots) {
        for (int i = 0; i < 100; i++) {
            moveRobots(robots);
        }
    }

    private void moveRobots(List<Robot> robots) {
        for (var robot : robots) {
            var nextX = Math.floorMod(robot.getPosition().getX() + robot.getVelocity().getX(), width);
            var nextY = Math.floorMod(robot.getPosition().getY() + robot.getVelocity().getY(), height);

            if (nextX < 0 || nextY < 0) {
                System.out.println(nextX + " " + nextY);
            }

            robot.setPosition(new Point2D(nextX, nextY));
        }
    }

    private int calcSafetyFactor(List<Robot> robots) {
        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;

        var middleV = (width - 1) / 2;
        var middleH = (height - 1) / 2;

        for (var robot : robots) {
            if (robot.position.getX() < middleV && robot.position.getY() < middleH) {
                q1++;
            } else if (robot.position.getX() > middleV && robot.position.getY() < middleH) {
                q2++;
            } else if (robot.position.getX() < middleV && robot.position.getY() > middleH) {
                q3++;
            } else if (robot.position.getX() > middleV && robot.position.getY() > middleH) {
                q4++;
            }
        }

        return q1 * q2 * q3 * q4;
    }

    @Override
    protected void partTwo() {
        var robots = parseInput();
        var step = moveRobotsUntilChristmasTree(robots);
        System.out.println(step + " steps are needed to form a christmas tree");
    }

    private int moveRobotsUntilChristmasTree(List<Robot> robots) {
        var christmasTree = false;
        var preventInfinity = false;
        var step = 0;

        do {
            moveRobots(robots);
            if (areRobotsClose(robots)) {
                christmasTree = true;
            }

            step++;

            if (step > 10000) {
                preventInfinity = true;
            }

        } while (!christmasTree && !preventInfinity);

        saveImage(robots, step);
        return step;
    }

    private boolean areRobotsClose(List<Robot> robots) {
        var totalRobots = robots.size();

        for (var i = 0; i < totalRobots; i++) {
            var centerRobot = robots.get(i);
            var closeCount = 0;

            for (var j = 0; j < totalRobots; j++) {
                if (i != j) {
                    var otherRobot = robots.get(j);
                    var distance = calculateDistance(centerRobot.getPosition(), otherRobot.getPosition());

                    if (distance <= 10) {
                        closeCount++;
                    }
                }
            }

            if ((double) closeCount / totalRobots >= 0.3) {
                centerRobot.setHighlight(true);
                return true;
            }
        }

        return false;
    }

    private double calculateDistance(Point2D point1, Point2D point2) {
        var deltaX = point1.getX() - point2.getX();
        var deltaY = point1.getY() - point2.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private void saveImage(List<Robot> robots, int step) {
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                image.setRGB(x, y, Color.WHITE.getRGB());
            }
        }

        for (var robot : robots) {
            var x = robot.getPosition().getX();
            var y = robot.getPosition().getY();
            var color = robot.highlight ? Color.RED : Color.BLACK;
            image.setRGB(x, y, color.getRGB());
        }

        try {
            var outputFile = new File("./target/step_" + step + ".png");
            ImageIO.write(image, "PNG", outputFile);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Data
    private static class Robot {

        private Point2D position;
        private final Point2D velocity;
        private boolean highlight;

        public Robot(Point2D position, Point2D velocity) {
            this.position = position;
            this.velocity = velocity;
        }

    }

}
