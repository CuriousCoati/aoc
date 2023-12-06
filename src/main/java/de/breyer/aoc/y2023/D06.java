package de.breyer.aoc.y2023;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.y2023.boatrace.RaceRecord;

@AocPuzzle("2023_06")
public class D06 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var raceRecords = parseInput();
        var errorMargin = raceRecords.parallelStream().map(RaceRecord::countWaysToBeatRecord).reduce(1L, Math::multiplyExact);
        System.out.println("error margin: " + errorMargin);
    }

    private List<RaceRecord> parseInput() {
        var raceRecords = new ArrayList<RaceRecord>();
        var splitLineOne = lines.get(0).replaceAll(" +", " ").split(" ");
        var splitLineTwo = lines.get(1).replaceAll(" +", " ").split(" ");

        for (int i = 1; i < splitLineOne.length; i++) {
            var time = Long.parseLong(splitLineOne[i]);
            var distance = Long.parseLong(splitLineTwo[i]);

            raceRecords.add(new RaceRecord(time, distance));
        }

        return raceRecords;
    }

    @Override
    protected void partTwo() {
        var raceRecord = parseInputWithoutKerning();
        var waysToBeatRecord = raceRecord.countWaysToBeatRecord();
        System.out.println("ways to beat record: " + waysToBeatRecord);
    }

    private RaceRecord parseInputWithoutKerning() {
        var splitLineOne = lines.get(0).replaceAll(" ", "").split(":");
        var splitLineTwo = lines.get(1).replaceAll(" ", "").split(":");

        var time = Long.parseLong(splitLineOne[1]);
        var distance = Long.parseLong(splitLineTwo[1]);

        return new RaceRecord(time, distance);
    }

}
