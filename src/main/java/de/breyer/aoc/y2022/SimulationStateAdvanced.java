package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulationStateAdvanced extends SimulationState {

    private final int[] compareBenchmark;
    private Room currentRoomElephant;
    private int toMoveMe;
    private int toMoveElephant;

    public SimulationStateAdvanced(Room currentRoom, List<Room> remainingValves, int remainingTime, int[] compareBenchmark) {
        super(currentRoom, remainingValves, remainingTime);
        toMoveMe = -1;
        toMoveElephant = -1;
        currentRoomElephant = currentRoom;
        this.compareBenchmark = compareBenchmark;
    }

    public SimulationStateAdvanced(Room currentRoom, List<Room> remainingValves, int remainingTime, int currentPressureRelease,
            int[] compareBenchmark) {
        super(currentRoom, remainingValves, remainingTime, currentPressureRelease);
        this.compareBenchmark = compareBenchmark;
    }

    public void setCurrentRoomElephant(Room currentRoomElephant) {
        this.currentRoomElephant = currentRoomElephant;
    }

    public void setToMoveMe(int toMoveMe) {
        this.toMoveMe = toMoveMe;
    }

    public void setToMoveElephant(int toMoveElephant) {
        this.toMoveElephant = toMoveElephant;
    }

    @Override
    public List<SimulationState> simulate(Map<String, Integer> allPaths) {
        List<SimulationState> nextStates = new ArrayList<>();

        if (toMoveMe == -1 && toMoveElephant == -1) {
            nextStates = nextStatesForBoth(allPaths);
        } else if (toMoveMe == -1) {
            nextStates = moveMe(allPaths);
        } else if (toMoveElephant == -1) {
            nextStates = moveElephant(allPaths);
        }

        if (nextStates.isEmpty()) {
            endBenchmark();
        }

        return nextStates;
    }

    private List<SimulationState> nextStatesForBoth(Map<String, Integer> allPaths) {
        List<SimulationState> nextStates = new ArrayList<>();

        boolean sameRoom = currentRoom == currentRoomElephant;

        for (int i = 0; i < remainingValves.size() - 1; i++) {
            for (int j = i + 1; j < remainingValves.size(); j++) {

                Room firstValve = remainingValves.get(i);
                Room secondValve = remainingValves.get(j);

                SimulationState nextState = fillSimulationState(allPaths, firstValve, secondValve);

                if (null != nextState) {
                    nextStates.add(nextState);
                    if (!sameRoom) {
                        nextState = fillSimulationState(allPaths, secondValve, firstValve);
                        if (null != nextState) {
                            nextStates.add(nextState);
                        }
                    }
                }
            }
        }

        return nextStates;
    }

    private SimulationState fillSimulationState(Map<String, Integer> allPaths, Room remainingValveMe, Room remainingValveElephant) {

        int pathLengthMe = allPaths.get(SimulationState.getPathName(currentRoom, remainingValveMe));
        int pathLengthElephant = allPaths.get(SimulationState.getPathName(currentRoomElephant, remainingValveElephant));

        List<Room> remainingValvesCopy = new ArrayList<>(remainingValves);
        Room nextRoomMe = currentRoom;
        Room nextRoomElephant = currentRoomElephant;

        if (pathLengthMe < remainingTime) {
            remainingValvesCopy.remove(remainingValveMe);
            nextRoomMe = remainingValveMe;
        }

        if (pathLengthElephant < remainingTime) {
            remainingValvesCopy.remove(remainingValveElephant);
            nextRoomElephant = remainingValveElephant;
        }

        if (remainingValvesCopy.size() != remainingValves.size()) {

            int elapsedTime = Integer.min(pathLengthMe, pathLengthElephant);
            int nextRemainingTime = remainingTime - (elapsedTime + 1);

            int nextPressureRate = currentPressureRelease;
            if (elapsedTime == pathLengthMe) {
                nextPressureRate += remainingValveMe.getFlowRate();
            }
            if (elapsedTime == pathLengthElephant) {
                nextPressureRate += remainingValveElephant.getFlowRate();
            }

            SimulationStateAdvanced nextState = new SimulationStateAdvanced(nextRoomMe, remainingValvesCopy, nextRemainingTime, nextPressureRate,
                    compareBenchmark);
            nextState.setCurrentRoomElephant(nextRoomElephant);
            nextState.setToMoveMe(pathLengthMe - (elapsedTime + 1));
            nextState.setToMoveElephant(pathLengthElephant - (elapsedTime + 1));

            calcBenchmark(elapsedTime, nextState);

            if (isWorthy(nextState)) {
                return nextState;
            } else {
                return null;
            }
        }

        return null;
    }

    private List<SimulationState> moveMe(Map<String, Integer> allPaths) {
        List<SimulationState> nextStates = new ArrayList<>();

        for (Room remainingValve : remainingValves) {

            int pathLength = allPaths.get(SimulationState.getPathName(currentRoom, remainingValve));

            List<Room> remainingValvesCopy = new ArrayList<>(remainingValves);
            Room nextRoomMe = currentRoom;

            if (pathLength < remainingTime) {
                remainingValvesCopy.remove(remainingValve);
                nextRoomMe = remainingValve;
            }

            if (remainingValvesCopy.size() != remainingValves.size() || (toMoveElephant != -1 && toMoveElephant < remainingTime)) {

                int elapsedTime = Integer.min(pathLength, toMoveElephant);
                int nextRemainingTime = remainingTime - (elapsedTime + 1);

                int nextPressureRate = currentPressureRelease;
                if (elapsedTime == pathLength) {
                    nextPressureRate += remainingValve.getFlowRate();
                }
                if (elapsedTime == toMoveElephant) {
                    nextPressureRate += currentRoomElephant.getFlowRate();
                }

                SimulationStateAdvanced nextState = new SimulationStateAdvanced(nextRoomMe, remainingValvesCopy, nextRemainingTime, nextPressureRate,
                        compareBenchmark);
                nextState.setCurrentRoomElephant(currentRoomElephant);
                nextState.setToMoveMe(pathLength - (elapsedTime + 1));
                nextState.setToMoveElephant(toMoveElephant - (elapsedTime + 1));

                calcBenchmark(elapsedTime, nextState);

                if (isWorthy(nextState)) {
                    nextStates.add(nextState);
                }

            }
        }

        if (nextStates.isEmpty() && (toMoveElephant != -1 && toMoveElephant < remainingTime)) {
            int elapsedTime = toMoveElephant;
            int nextRemainingTime = remainingTime - (elapsedTime + 1);
            int nextPressureRate = currentPressureRelease + currentRoomElephant.getFlowRate();

            SimulationStateAdvanced nextState = new SimulationStateAdvanced(currentRoom, remainingValves, nextRemainingTime, nextPressureRate,
                    compareBenchmark);
            nextState.setCurrentRoomElephant(currentRoomElephant);
            nextState.setToMoveMe(-1);
            nextState.setToMoveElephant(-1);

            calcBenchmark(elapsedTime, nextState);

            if (isWorthy(nextState)) {
                nextStates.add(nextState);
            }
        }

        return nextStates;
    }

    private List<SimulationState> moveElephant(Map<String, Integer> allPaths) {
        List<SimulationState> nextStates = new ArrayList<>();

        for (Room remainingValve : remainingValves) {

            int pathLength = allPaths.get(SimulationState.getPathName(currentRoomElephant, remainingValve));

            List<Room> remainingValvesCopy = new ArrayList<>(remainingValves);
            Room nextRoomElephant = currentRoomElephant;

            if (pathLength < remainingTime) {
                remainingValvesCopy.remove(remainingValve);
                nextRoomElephant = remainingValve;
            }

            if (remainingValvesCopy.size() != remainingValves.size() || (toMoveMe != -1 && toMoveMe < remainingTime)) {

                int elapsedTime = Integer.min(pathLength, toMoveMe);
                int nextRemainingTime = remainingTime - (elapsedTime + 1);

                int nextPressureRate = currentPressureRelease;
                if (elapsedTime == pathLength) {
                    nextPressureRate += remainingValve.getFlowRate();
                }
                if (elapsedTime == toMoveMe) {
                    nextPressureRate += currentRoom.getFlowRate();
                }

                SimulationStateAdvanced nextState = new SimulationStateAdvanced(currentRoom, remainingValvesCopy, nextRemainingTime, nextPressureRate,
                        compareBenchmark);
                nextState.setCurrentRoomElephant(nextRoomElephant);
                nextState.setToMoveMe(toMoveMe - (elapsedTime + 1));
                nextState.setToMoveElephant(pathLength - (elapsedTime + 1));

                calcBenchmark(elapsedTime, nextState);

                if (isWorthy(nextState)) {
                    nextStates.add(nextState);
                }

            }
        }

        if (nextStates.isEmpty() && (toMoveMe != -1 && toMoveMe < remainingTime)) {
            int elapsedTime = toMoveMe;
            int nextRemainingTime = remainingTime - (elapsedTime + 1);
            int nextPressureRate = currentPressureRelease + currentRoom.getFlowRate();

            SimulationStateAdvanced nextState = new SimulationStateAdvanced(currentRoom, remainingValves, nextRemainingTime, nextPressureRate,
                    compareBenchmark);
            nextState.setCurrentRoomElephant(currentRoomElephant);
            nextState.setToMoveMe(-1);
            nextState.setToMoveElephant(-1);

            calcBenchmark(elapsedTime, nextState);

            if (isWorthy(nextState)) {
                nextStates.add(nextState);
            }
        }

        return nextStates;
    }

    private boolean isWorthy(SimulationStateAdvanced nextState) {
        int benchmarkIdx = 30 - nextState.remainingTime;
        if (benchmarkIdx < 30) {
            int benchmarkValue = compareBenchmark[benchmarkIdx];
            int currentValue = nextState.getBenchmark()[benchmarkIdx];
            int worthyDiff = 250 * nextState.remainingTime;

            return benchmarkValue - currentValue <= worthyDiff;
        }
        return true;
    }
}
