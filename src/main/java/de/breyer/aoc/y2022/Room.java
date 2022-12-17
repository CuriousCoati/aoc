package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private final String name;
    private final List<Room> connectedRooms = new ArrayList<>();
    private int flowRate;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getFlowRate() {
        return this.flowRate;
    }

    public void setFlowRate(int flowRate) {
        this.flowRate = flowRate;
    }

    public List<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public void addRoom(Room room) {
        this.connectedRooms.add(room);
    }
}
