package de.breyer.aoc.y2021;

public class Node {

    public static String buildName(int x, int y) {
        return x + ";" + y;
    }

    private Node predecessor;
    private int x;
    private int y;
    private String name;
    private int value;
    private int cost;
    private int priority;

    public String getName() {
        return name;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getValue() {
        return this.value;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public Node(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.name = buildName(x, y);
    }
}
