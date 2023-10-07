package de.breyer.aoc.y2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_16")
public class D16 extends AbstractAocPuzzle {

    private Room startRoom;
    private List<Room> relevantRooms;
    private Map<String, Integer> allPaths;
    private int[] benchmark;

    @Override
    protected void partOne() {
        parseInput();
        prepare();
        benchmark = simulate(new SimulationState(startRoom, relevantRooms, 30));
        System.out.println("pressure released: " + benchmark[29]);
    }

    private void parseInput() {
        Map<String, Room> rooms = new HashMap<>();
        relevantRooms = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(" ");

            Room room = rooms.getOrDefault(split[1], new Room(split[1]));
            rooms.putIfAbsent(room.getName(), room);

            room.setFlowRate(Integer.parseInt(split[4].replace("rate=", "").replace(";", "")));

            for (int i = 9; i < split.length; i++) {
                String roomName = split[i].replace(",", "");

                Room neighbour = rooms.getOrDefault(roomName, new Room(roomName));
                rooms.putIfAbsent(roomName, neighbour);

                room.addRoom(neighbour);
            }

            if ("AA".equals(room.getName())) {
                startRoom = room;
                relevantRooms.add(room);
            }

            if (room.getFlowRate() > 0) {
                relevantRooms.add(room);
            }

        }
    }

    private void prepare() {
        allPaths = new HashMap<>();
        for (int i = 0; i < relevantRooms.size() - 1; i++) {
            Room room1 = relevantRooms.get(i);
            for (int j = i + 1; j < relevantRooms.size(); j++) {
                Room room2 = relevantRooms.get(j);
                String pathName = SimulationState.getPathName(room1, room2);
                int pathLength = calcShortestPath(relevantRooms.get(i), relevantRooms.get(j));
                allPaths.put(pathName, pathLength);
            }
        }

        relevantRooms.remove(startRoom);
    }

    private int[] simulate(SimulationState startState) {
        Stack<SimulationState> states = new Stack<>();
        states.add(startState);
        int[] bestBenchmark = null;

        while (!states.isEmpty()) {
            SimulationState currentState = states.pop();
            List<SimulationState> nextStates = currentState.simulate(allPaths);

            if (nextStates.isEmpty()) {
                if (null == bestBenchmark || bestBenchmark[29] < currentState.getBenchmark()[29]) {
                    bestBenchmark = currentState.getBenchmark();
                }
            } else {
                states.addAll(nextStates);
            }
        }

        return bestBenchmark;
    }

    private int calcShortestPath(Room start, Room goal) {
        Queue<Room> queue = new ArrayDeque<>();
        queue.add(start);

        List<Room> seen = new ArrayList<>();
        seen.add(start);

        Map<Room, Integer> distances = new HashMap<>();
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Room currentRoom = queue.poll();

            if (currentRoom == goal) {
                break;
            }

            for (Room child : currentRoom.getConnectedRooms()) {
                if (!seen.contains(child)) {
                    queue.add(child);
                    seen.add(currentRoom);
                    distances.put(child, distances.get(currentRoom) + 1);
                }
            }
        }

        return distances.get(goal);
    }

    @Override
    protected void partTwo() {
        int[] bestBenchmark = simulate(new SimulationStateAdvanced(startRoom, relevantRooms, 26, benchmark));
        System.out.println("pressure released: " + bestBenchmark[29]);
    }
}
