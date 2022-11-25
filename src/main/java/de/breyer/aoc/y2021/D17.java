package de.breyer.aoc.y2021;

import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_17")
public class D17 extends AbstractAocPuzzle {

    private final Probe probe = new Probe();
    private TargetArea targetArea;
    private int maxHeight = 0;
    private int hits = 0;

    @Override
    protected void partOne() {
        parseInput();
        tryVelocities();
        System.out.println("max height: " + maxHeight);
    }

    @Override
    protected void partTwo() {
        System.out.println("hitting velocities: " + hits);
    }


    private void parseInput() {
        String line  = lines.get(0);
        int minX = Integer.parseInt(line.substring(line.indexOf("=") + 1, line.indexOf("..")));
        int maxX = Integer.parseInt(line.substring(line.indexOf("..") + 2, line.indexOf(",")));
        int minY = Integer.parseInt(line.substring(line.lastIndexOf("=") + 1, line.lastIndexOf("..")));
        int maxY = Integer.parseInt(line.substring(line.lastIndexOf("..") + 2));
        targetArea = new TargetArea(minX, maxX, minY, maxY);
    }

    private void tryVelocities() {
        for (int x = 0; x <= 4000; x++) {
            for (int y = -1000; y <= 4000; y++) {
                simulate(x, y);
            }
        }
    }

    private void simulate(int x, int y) {
        int maxY = 0;
        boolean hit = false;
        boolean missed = false;
        probe.setX(0);
        probe.setY(0);
        probe.setVelocityX(x);
        probe.setVelocityY(y);

        do {
            int vX = probe.getVelocityX();
            int vY = probe.getVelocityY();

            probe.setX(probe.getX() + vX);
            probe.setY(probe.getY() + vY);

            probe.setVelocityX(vX > 0 ? vX - 1 : 0);
            probe.setVelocityY(vY - 1);

            if (maxY < probe.getY()) {
                maxY = probe.getY();
            }

            if (targetArea.isInside(probe)) {
                hit = true;
                hits++;
            } else if (targetArea.missed(probe)) {
                missed = true;
            }

        } while(!hit && !missed);

        if (hit && maxY > maxHeight) {
            maxHeight = maxY;
        }
    }
}
