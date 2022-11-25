package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2021_19")
public class D19 extends AbstractAocPuzzle {

    private final List<Scanner> scanners = new ArrayList<>();
    private final List<Rotation> rotations = new ArrayList<>();
    private final List<Scanner> normalizedScanners = new ArrayList<>();
    private final List<Scanner> unnormalizedScanners = new ArrayList<>();

    @Override
    protected void partOne() {
        init();
        readInput();
        calculateDistances();
        calculateScanners();
        countBeacons();
    }

    @Override
    protected void partTwo() {
        calculateMaxManhattenDistance();

    }

    private void init() {
        for (double x = 0; x < 271; x += 90) {
            for (double y = 0; y < 271; y += 90) {
                for (double z = 0; z < 271; z += 90) {
                    rotations.add(new Rotation(x, y, z));
                }
            }
        }
    }

    private void readInput() {
        Scanner currentScanner = null;
        for (String line : lines) {
            if (line.startsWith("---")) {
                currentScanner = new Scanner();
                scanners.add(currentScanner);
            } else if (line.length() != 0) {
                String[] split = line.split(",");
                long x = Long.parseLong(split[0]);
                long y = Long.parseLong(split[1]);
                long z = Long.parseLong(split[2]);
                currentScanner.addBeacon(new Beacon(new Coordinate3D(x, y, z)));
            }
        }
    }

    private void calculateDistances() {
        for (Scanner scanner : scanners) {
            for (int i = 0; i < scanner.getBeacons().size() - 1; i++) {
                for (int j = i+1; j < scanner.getBeacons().size(); j++) {
                    Beacon beacon1 = scanner.getBeacons().get(i);
                    Beacon beacon2 = scanner.getBeacons().get(j);
                    double distance = CoordinateUtils.calculateDistance(beacon1.getRelativePosition(), beacon2.getRelativePosition());

                    beacon1.addDistance(beacon2, distance);
                    beacon2.addDistance(beacon1, distance);
                }
            }
        }
    }

    private void calculateScanners() {
        Scanner firstScanner = scanners.get(0);
        firstScanner.setPosition(new Coordinate3D(0, 0, 0));
        firstScanner.setRotation(rotations.get(0));
        firstScanner.calcAbsoulteBeaconPositions();

        normalizedScanners.add(firstScanner);
        for (int i = 1; i < scanners.size(); i++) {
            unnormalizedScanners.add(scanners.get(i));
        }

        do {
            List<Scanner> processedScanners = new ArrayList<>();
            for (Scanner normalizedScanner : normalizedScanners) {
                for (Scanner unnormalizedScanner : unnormalizedScanners) {
                    if (findOverlappingBeacons(normalizedScanner, unnormalizedScanner)) {
                        processedScanners.add(unnormalizedScanner);
                    }
                }
            }

            unnormalizedScanners.removeAll(processedScanners);
            normalizedScanners.addAll(processedScanners);

        } while(!unnormalizedScanners.isEmpty());
    }

    private boolean findOverlappingBeacons(Scanner normalizedScanner, Scanner unnormalizedScanner) {
        for (Beacon beacon1 : normalizedScanner.getBeacons()) {
            for (Beacon beacon2 : unnormalizedScanner.getBeacons()) {

                Beacon matchingBeacon1 = null;
                Beacon matchingBeacon2 = null;
                int countMatches = 0;
                for (Entry<Beacon, Double> entry1 : beacon1.getDistances().entrySet()) {
                    for (Entry<Beacon, Double> entry2 : beacon2.getDistances().entrySet()) {
                        if (entry1.getValue().equals(entry2.getValue())) {
                            countMatches++;
                            if (null == matchingBeacon1) {
                                matchingBeacon1 = entry1.getKey();
                                matchingBeacon2 = entry2.getKey();
                            }
                        }
                    }
                }

                if (countMatches >= 11) {
                    for (Rotation rotation : rotations) {
                        Coordinate3D rotBeacon1 = CoordinateUtils.rotateCoordinate(beacon2.getRelativePosition(), rotation);
                        Coordinate3D rotBeacon2 = CoordinateUtils.rotateCoordinate(matchingBeacon2.getRelativePosition(), rotation);

                        Coordinate3D diffBeacon1 = CoordinateUtils.substractCoordinates(beacon1.getAbsolutePosition(), rotBeacon1);
                        Coordinate3D diffBeacon2 = CoordinateUtils.substractCoordinates(matchingBeacon1.getAbsolutePosition(), rotBeacon2);

                        if (diffBeacon1.equals(diffBeacon2)) {
                            unnormalizedScanner.setPosition(CoordinateUtils.substractCoordinates(beacon1.getAbsolutePosition(), rotBeacon1));
                            unnormalizedScanner.setRotation(rotation);
                            unnormalizedScanner.calcAbsoulteBeaconPositions();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void countBeacons() {

        Set<Coordinate3D> uniqueBeaconCoordinates = new HashSet<>();

        for (Scanner scanner : scanners) {
            for (Beacon beacon : scanner.getBeacons()) {
                uniqueBeaconCoordinates.add(beacon.getAbsolutePosition());
            }
        }

        System.out.println("there are " + uniqueBeaconCoordinates.size() + " beacons");
    }

    private void calculateMaxManhattenDistance() {
        long maxManhattenDistance = 0;
        for (int i = 0; i <= scanners.size() - 1; i++) {
            for (int j = i + 1; j < scanners.size(); j++) {
                long manhattenDistance = CoordinateUtils.calcManhattenDistance(scanners.get(i).getPosition(), scanners.get(j).getPosition());
                if (manhattenDistance > maxManhattenDistance) {
                    maxManhattenDistance = manhattenDistance;
                }
            }
        }
        System.out.println("max manhatten distance: " + maxManhattenDistance);
    }
}
