package de.breyer.aoc.y2022;

import java.util.Objects;

public class MiningState {

    private int oreRobotCount = 1;
    private int clayRobotCount = 0;
    private int obsidianRobotCount = 0;
    private int geodeRobotCount = 0;
    private int oreCount = 0;
    private int clayCount = 0;
    private int obsidianCount = 0;
    private int geodeCount = 0;

    public int getGeodeRobotCount() {
        return geodeRobotCount;
    }

    public int getGeodeCount() {
        return geodeCount;
    }

    public void increaseResources() {
        oreCount += oreRobotCount;
        clayCount += clayRobotCount;
        obsidianCount += obsidianRobotCount;
        geodeCount += geodeRobotCount;
    }

    public boolean canBuildOreRobot(Blueprint blueprint) {
        return oreCount >= blueprint.getOreRobotOreCost();
    }

    public boolean canBuildClayRobot(Blueprint blueprint) {
        return oreCount >= blueprint.getClayRobotOreCost();
    }

    public boolean canBuildObsidianRobot(Blueprint blueprint) {
        return oreCount >= blueprint.getObsidianRobotOreCost() && clayCount >= blueprint.getObsidianRobotClayCost();
    }

    public boolean canBuildGeodeRobot(Blueprint blueprint) {
        return oreCount >= blueprint.getGeodeRobotOreCost() && obsidianCount >= blueprint.getGeodeRobotObsidianCost();
    }

    public MiningState buildOreRobot(Blueprint blueprint) {
        MiningState copy = copy();
        copy.oreRobotCount++;
        copy.oreCount -= blueprint.getOreRobotOreCost();
        return copy;
    }

    public MiningState buildClayRobot(Blueprint blueprint) {
        MiningState copy = copy();
        copy.clayRobotCount++;
        copy.oreCount -= blueprint.getClayRobotOreCost();
        return copy;
    }

    public MiningState buildObsidianRobot(Blueprint blueprint) {
        MiningState copy = copy();
        copy.obsidianRobotCount++;
        copy.oreCount -= blueprint.getObsidianRobotOreCost();
        copy.clayCount -= blueprint.getObsidianRobotClayCost();
        return copy;
    }

    public MiningState buildGeodeRobot(Blueprint blueprint) {
        MiningState copy = copy();
        copy.geodeRobotCount++;
        copy.oreCount -= blueprint.getGeodeRobotOreCost();
        copy.obsidianCount -= blueprint.getGeodeRobotObsidianCost();
        return copy;
    }

    private MiningState copy() {
        MiningState copy = new MiningState();
        copy.oreCount = oreCount;
        copy.clayCount = clayCount;
        copy.obsidianCount = obsidianCount;
        copy.geodeCount = geodeCount;
        copy.oreRobotCount = oreRobotCount;
        copy.clayRobotCount = clayRobotCount;
        copy.obsidianRobotCount = obsidianRobotCount;
        copy.geodeRobotCount = geodeRobotCount;
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MiningState that = (MiningState) o;
        return oreRobotCount == that.oreRobotCount && clayRobotCount == that.clayRobotCount && obsidianRobotCount == that.obsidianRobotCount
                && geodeRobotCount == that.geodeRobotCount && oreCount == that.oreCount && clayCount == that.clayCount
                && obsidianCount == that.obsidianCount && geodeCount == that.geodeCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(oreRobotCount, clayRobotCount, obsidianRobotCount, geodeRobotCount, oreCount, clayCount, obsidianCount, geodeCount);
    }

    public int getPossibleGeodes(int maxMinutes, int minute) {
        int remainingMinutes = maxMinutes - minute - 1;

        int geodeFromRobots = getGeodeRobotCount() * remainingMinutes;
        int geodeFromPossibleRobots = remainingMinutes * (remainingMinutes - 1) / 2;

        return getGeodeCount() + geodeFromRobots + geodeFromPossibleRobots;
    }

    public boolean moreOreBotsUseful(Blueprint blueprint) {
        return oreRobotCount < Math.max(blueprint.getGeodeRobotOreCost(),
                Math.max(blueprint.getObsidianRobotOreCost(), Math.max(blueprint.getClayRobotOreCost(),
                        blueprint.getOreRobotOreCost())));
    }

    public boolean moreClayBotsUseful(Blueprint blueprint) {
        return clayRobotCount < blueprint.getObsidianRobotClayCost();
    }

    public boolean moreObsidianBotsUseful(Blueprint blueprint) {
        return obsidianRobotCount < blueprint.getGeodeRobotObsidianCost();
    }
}
