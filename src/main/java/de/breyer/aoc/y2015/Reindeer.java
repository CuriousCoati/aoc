package de.breyer.aoc.y2015;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Reindeer {

    private final String name;
    private final int flySpeed;
    private final int flyTime;
    private final int restTime;

}
