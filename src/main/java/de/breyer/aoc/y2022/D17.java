package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.LongCoordinate2D;

@AocPuzzle("2022_17")
public class D17 extends AbstractAocPuzzle {

    private long cleanStart;
    private long[] surfaces;
    private long highestY;
    private int currentRockTypeIdx;
    private int currentJetInstructionIdx;
    private List<LongCoordinate2D> filledPositions;
    private List<Pair<SimulationStep, Long>> steps;

    @Override
    protected void partOne() {
        init();
        mainLoop(2022);
        System.out.println("tower of rocks is " + (highestY + 1) + " units high");
    }

    private void mainLoop(long end) {
        for (long i = 0; i < end; i++) {
            long startHeight = highestY;
            SimulationStep step = createSimulationStep();
            simulate();
            steps.add(new Pair<>(step, highestY - startHeight));
            sanitizeFilledPositions();
        }
    }

    private SimulationStep createSimulationStep() {
        SimulationStep step = new SimulationStep(currentRockTypeIdx, currentJetInstructionIdx);
        for (int x = 0; x < 7; x++) {
            step.getSurfaceCondition()[x] = highestY - surfaces[x];
        }
        return step;
    }

    private void init() {
        highestY = -1;
        currentRockTypeIdx = 0;
        currentJetInstructionIdx = 0;
        filledPositions = new ArrayList<>();
        steps = new ArrayList<>();
        surfaces = new long[7];
        surfaces[0] = -1;
        surfaces[1] = -1;
        surfaces[2] = -1;
        surfaces[3] = -1;
        surfaces[4] = -1;
        surfaces[5] = -1;
        surfaces[6] = -1;
        cleanStart = 0;
    }

    private void simulate() {
        RockType rockType = getNextRockType();

        LongCoordinate2D position = new LongCoordinate2D(2, highestY + 4);

        boolean atRest = false;

        do {
            position = moveByJetOfGas(rockType, position);
            LongCoordinate2D positionAfterFallDown = fallDown(rockType, position);

            if (positionAfterFallDown.equals(position)) {
                atRest = true;
                placeRock(rockType, position);
            } else {
                position = positionAfterFallDown;
            }
        } while (!atRest);
    }

    private RockType getNextRockType() {
        RockType rockType = RockType.values()[currentRockTypeIdx];
        currentRockTypeIdx = currentRockTypeIdx == RockType.values().length - 1 ? 0 : currentRockTypeIdx + 1;
        return rockType;
    }

    private LongCoordinate2D moveByJetOfGas(RockType rockType, LongCoordinate2D position) {
        char direction = lines.get(0).charAt(currentJetInstructionIdx);
        currentJetInstructionIdx = currentJetInstructionIdx == lines.get(0).length() - 1 ? 0 : currentJetInstructionIdx + 1;

        List<LongCoordinate2D> rockCoordinates = getRockCoordinates(rockType, position);
        int change = direction == '<' ? -1 : 1;
        boolean canMove = true;

        for (LongCoordinate2D coordinate : rockCoordinates) {
            long newX = coordinate.x() + change;
            if (newX < 0 || newX > 6 || filledPositions.contains(new LongCoordinate2D(newX, coordinate.y()))) {
                canMove = false;
                break;
            }
        }

        if (canMove) {
            position = new LongCoordinate2D(position.x() + change, position.y());
        }

        return position;
    }

    private LongCoordinate2D fallDown(RockType rockType, LongCoordinate2D position) {
        List<LongCoordinate2D> rockCoordinates = getRockCoordinates(rockType, position);
        boolean canMove = true;

        for (LongCoordinate2D coordinate : rockCoordinates) {
            long newY = coordinate.y() - 1;
            if (newY < 0 || filledPositions.contains(new LongCoordinate2D(coordinate.x(), newY))) {
                canMove = false;
                break;
            }
        }

        if (canMove) {
            position = new LongCoordinate2D(position.x(), position.y() - 1);
        }

        return position;
    }

    private List<LongCoordinate2D> getRockCoordinates(RockType rockType, LongCoordinate2D position) {
        List<LongCoordinate2D> coordinates = new ArrayList<>();

        if (rockType == RockType.MINUS) {
            coordinates.add(position);
            coordinates.add(new LongCoordinate2D(position.x() + 1, position.y()));
            coordinates.add(new LongCoordinate2D(position.x() + 2, position.y()));
            coordinates.add(new LongCoordinate2D(position.x() + 3, position.y()));
        } else if (rockType == RockType.PLUS) {
            coordinates.add(new LongCoordinate2D(position.x() + 1, position.y()));
            coordinates.add(new LongCoordinate2D(position.x(), position.y() + 1));
            coordinates.add(new LongCoordinate2D(position.x() + 1, position.y() + 1));
            coordinates.add(new LongCoordinate2D(position.x() + 2, position.y() + 1));
            coordinates.add(new LongCoordinate2D(position.x() + 1, position.y() + 2));
        } else if (rockType == RockType.CORNER) {
            coordinates.add(position);
            coordinates.add(new LongCoordinate2D(position.x() + 1, position.y()));
            coordinates.add(new LongCoordinate2D(position.x() + 2, position.y()));
            coordinates.add(new LongCoordinate2D(position.x() + 2, position.y() + 1));
            coordinates.add(new LongCoordinate2D(position.x() + 2, position.y() + 2));
        } else if (rockType == RockType.COLUMN) {
            coordinates.add(position);
            coordinates.add(new LongCoordinate2D(position.x(), position.y() + 1));
            coordinates.add(new LongCoordinate2D(position.x(), position.y() + 2));
            coordinates.add(new LongCoordinate2D(position.x(), position.y() + 3));
        } else if (rockType == RockType.SQUARE) {
            coordinates.add(position);
            coordinates.add(new LongCoordinate2D(position.x() + 1, position.y()));
            coordinates.add(new LongCoordinate2D(position.x(), position.y() + 1));
            coordinates.add(new LongCoordinate2D(position.x() + 1, position.y() + 1));
        }

        return coordinates;
    }

    private void placeRock(RockType rockType, LongCoordinate2D position) {
        List<LongCoordinate2D> rockCoordinates = getRockCoordinates(rockType, position);
        for (LongCoordinate2D coordinate : rockCoordinates) {
            filledPositions.add(coordinate);

            if (surfaces[(int) coordinate.x()] < coordinate.y()) {
                surfaces[(int) coordinate.x()] = coordinate.y();
            }

            if (coordinate.y() > highestY) {
                highestY = coordinate.y();
            }
        }
    }

    private void sanitizeFilledPositions() {
        long minY = Arrays.stream(surfaces).min().orElse(0);

        for (long y = cleanStart; y < minY; y++) {
            for (int x = 0; x < 7; x++) {
                filledPositions.remove(new LongCoordinate2D(x, y));
            }
        }

        cleanStart = minY;
    }

    @Override
    protected void partTwo() {
        Cycle cycle = findCycle();

        if (null == cycle) {
            System.out.println("no cycle found");
        } else {
            init();
            mainLoop(cycle.getStart());

            long cycleFits = (1000000000000L - cycle.getStart()) / cycle.getLength();
            long heightIncrease = cycleFits * cycle.getHeightIncrease();
            long remainingRocks = 1000000000000L - cycle.getStart() - cycleFits * cycle.getLength();

            highestY += heightIncrease;
            cleanStart += heightIncrease;

            for (int i = 0; i < 7; i++) {
                surfaces[i] += heightIncrease;
            }

            List<LongCoordinate2D> newPositions = new ArrayList<>();
            for (LongCoordinate2D coordinate : filledPositions) {
                newPositions.add(new LongCoordinate2D(coordinate.x(), coordinate.y() + heightIncrease));
            }
            filledPositions = newPositions;

            mainLoop(remainingRocks);

            System.out.println("tower of rocks is " + (highestY + 1) + " units high");
        }

    }

    private Cycle findCycle() {
        for (int i = 0; i < 2000; i++) {
            SimulationStep cycleStart = steps.get(i).getFirst();
            int cycleEnd = -1;
            long heightIncrease = steps.get(i).getSecond();

            for (int j = i + 1; j < steps.size(); j++) {
                SimulationStep possibleCycleEnd = steps.get(j).getFirst();

                if (cycleStart.equals(possibleCycleEnd)) {
                    cycleEnd = j;
                    break;
                }

                heightIncrease += steps.get(j).getSecond();
            }

            if (-1 != cycleEnd) {
                return new Cycle(i, cycleEnd - i, heightIncrease);
            }
        }

        return null;
    }

    private static class SimulationStep {

        private final int rockTypeIdx;
        private final int jetInstructionIdx;
        private final long[] surfaceCondition = new long[7];

        private SimulationStep(int rockTypeIdx, int jetInstructionIdx) {
            this.rockTypeIdx = rockTypeIdx;
            this.jetInstructionIdx = jetInstructionIdx;
        }

        public long[] getSurfaceCondition() {
            return surfaceCondition;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SimulationStep that = (SimulationStep) o;
            return rockTypeIdx == that.rockTypeIdx && jetInstructionIdx == that.jetInstructionIdx && Arrays.equals(surfaceCondition,
                    that.surfaceCondition);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(rockTypeIdx, jetInstructionIdx);
            result = 31 * result + Arrays.hashCode(surfaceCondition);
            return result;
        }
    }

    private record Cycle(int start, int length, long heightIncrease) {

        public int getStart() {
            return start;
        }

        public int getLength() {
            return length;
        }

        public long getHeightIncrease() {
            return heightIncrease;
        }

    }
}
