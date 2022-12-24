package de.breyer.aoc.y2022;

public class Blueprint {

    private final int id;
    private final int oreRobotOreCost;
    private final int clayRobotOreCost;
    private final int obsidianRobotOreCost;
    private final int obsidianRobotClayCost;
    private final int geodeRobotOreCost;
    private final int geodeRobotObsidianCost;

    private int maxGeodes;

    public int getOreRobotOreCost() {
        return oreRobotOreCost;
    }

    public int getClayRobotOreCost() {
        return clayRobotOreCost;
    }

    public int getObsidianRobotOreCost() {
        return obsidianRobotOreCost;
    }

    public int getObsidianRobotClayCost() {
        return obsidianRobotClayCost;
    }

    public int getGeodeRobotOreCost() {
        return geodeRobotOreCost;
    }

    public int getGeodeRobotObsidianCost() {
        return geodeRobotObsidianCost;
    }

    public int getMaxGeodes() {
        return maxGeodes;
    }

    public void setMaxGeodes(int maxGeodes) {
        this.maxGeodes = maxGeodes;
    }

    public Blueprint(int id, int oreRobotOreCost, int clayRobotOreCost, int obsidianRobotOreCost, int obsidianRobotClayCost, int geodeRobotOreCost,
            int geodeRobotClayCost) {
        this.id = id;
        this.oreRobotOreCost = oreRobotOreCost;
        this.clayRobotOreCost = clayRobotOreCost;
        this.obsidianRobotOreCost = obsidianRobotOreCost;
        this.obsidianRobotClayCost = obsidianRobotClayCost;
        this.geodeRobotOreCost = geodeRobotOreCost;
        this.geodeRobotObsidianCost = geodeRobotClayCost;
    }

    public int calcQualityLevel() {
        return id * maxGeodes;
    }
}
