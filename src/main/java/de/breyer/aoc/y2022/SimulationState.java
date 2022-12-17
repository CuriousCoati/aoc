package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimulationState {

    protected final Room currentRoom;
    protected final List<Room> remainingValves;
    protected final int remainingTime;
    protected final int currentPressureRelease;
    protected int[] benchmark = new int[30];

    public SimulationState(Room currentRoom, List<Room> remainingValves, int remainingTime) {
        this(currentRoom, remainingValves, remainingTime, 0);
    }

    public SimulationState(Room currentRoom, List<Room> remainingValves, int remainingTime, int currentPressureRelease) {
        this.currentRoom = currentRoom;
        this.remainingValves = remainingValves;
        this.remainingTime = remainingTime;
        this.currentPressureRelease = currentPressureRelease;
    }

    public static String getPathName(Room room1, Room room2) {
        int result = room1.getName().compareTo(room2.getName());
        if (result < 0) {
            return room1.getName() + "-" + room2.getName();
        } else {
            return room2.getName() + "-" + room1.getName();
        }
    }

    public int[] getBenchmark() {
        return benchmark;
    }

    public List<SimulationState> simulate(Map<String, Integer> allPaths) {
        List<SimulationState> nextStates = new ArrayList<>();

        for (Room remainingValve : remainingValves) {

            int pathLength = allPaths.get(SimulationState.getPathName(currentRoom, remainingValve));

            if (pathLength < remainingTime) {
                List<Room> remainingValvesCopy = new ArrayList<>(remainingValves);
                remainingValvesCopy.remove(remainingValve);
                SimulationState state = new SimulationState(remainingValve, remainingValvesCopy, remainingTime - (pathLength + 1),
                        currentPressureRelease + remainingValve.getFlowRate());
                nextStates.add(state);

                calcBenchmark(pathLength, state);
            }
        }

        if (nextStates.isEmpty()) {
            endBenchmark();
        }

        return nextStates;
    }

    protected void endBenchmark() {
        int benchmarkIdx = 30 - remainingTime;
        for (int i = 0; i < remainingTime; i++) {
            int idx = benchmarkIdx + i;
            int previous = idx == 0 ? 0 : benchmark[idx - 1];
            benchmark[benchmarkIdx + i] = previous + currentPressureRelease;
        }
    }

    protected void calcBenchmark(int pathLength, SimulationState state) {
        int benchmarkIdx = 30 - remainingTime;

        for (int i = 0; i < (pathLength + 1); i++) {
            int idx = benchmarkIdx + i;
            int previous = idx == 0 ? 0 : benchmark[idx - 1];
            benchmark[idx] = previous + currentPressureRelease;
        }
        state.mergeBenchmark(benchmark);
    }

    private void mergeBenchmark(int[] benchmark) {
        for (int i = 0; i < benchmark.length; i++) {
            this.benchmark[i] = benchmark[i];
        }
    }

}
