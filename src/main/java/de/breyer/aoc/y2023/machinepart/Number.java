package de.breyer.aoc.y2023.machinepart;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.data.Point2D;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Number {

    private final int number;
    private final List<Point2D> coordinates = new ArrayList<>();

    public boolean isPartNumber(List<Symbol> symbols) {
        for (var symbol : symbols) {
            for (var coordinate : coordinates) {
                if (symbol.coordinate().isNeighbour(coordinate)) {
                    return true;
                }
            }
        }
        return false;
    }
}
