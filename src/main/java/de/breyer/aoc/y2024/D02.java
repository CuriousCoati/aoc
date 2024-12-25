package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2024_02")
public class D02 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        var reports = parseLines();
        var safeReports = calcSafeReports(reports, false);
        System.out.println("Safe reports: " + safeReports);
    }

    private List<List<Integer>> parseLines() {
        var reports = new ArrayList<List<Integer>>();

        for (var line : lines) {
            var report = new ArrayList<Integer>();
            reports.add(report);

            var split = line.split(" ");
            for (var s : split) {
                report.add(Integer.parseInt(s));
            }
        }

        return reports;
    }

    private long calcSafeReports(List<List<Integer>> reports, boolean tolerateBadLevel) {
        var safeReports = 0L;

        for (var report : reports) {
            var safe = isReportSafe(report);

            if (safe) {
                safeReports++;
            } else if (tolerateBadLevel) {
                for (int i = 0; i < report.size(); i++) {
                    var copy = new ArrayList<>(report);
                    copy.remove(i);
                    safe = isReportSafe(copy);

                    if (safe) {
                        safeReports++;
                        break;
                    }
                }
            }
        }

        return safeReports;
    }

    private boolean isReportSafe(List<Integer> report) {
        var previousResult = 0;
        var firstIndex = 0;

        for (int i = 1; i < report.size(); i++) {
            var first = report.get(firstIndex);
            var second = report.get(i);

            var result = second - first;
            if (Math.abs(result) >= 1 && Math.abs(result) <= 3) {
                if ((previousResult < 0 && result > 0) || (previousResult > 0 && result < 0)) {
                    return false;
                } else {
                    previousResult = result;
                    firstIndex = i;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void partTwo() {
        var reports = parseLines();
        var safeReports = calcSafeReports(reports, true);
        System.out.println("Safe reports: " + safeReports);
    }

}
