package de.breyer.aoc.y2023.machinepart;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.data.Point2D;

public record Symbol(char symbol, Point2D coordinate) {

    public long calcGearRatio(List<Number> numbers) {
        var gearRatio = 0L;

        if (symbol == '*') {
            var neighbours = new ArrayList<Number>();
            for (var number : numbers) {
                for (var numberCoordinate : number.getCoordinates()) {
                    if (coordinate.isNeighbour(numberCoordinate)) {
                        neighbours.add(number);
                        break;
                    }
                }
            }

            if (neighbours.size() == 2) {
                gearRatio = (long) neighbours.get(0).getNumber() * neighbours.get(1).getNumber();
            }

        }

        return gearRatio;
    }
}
