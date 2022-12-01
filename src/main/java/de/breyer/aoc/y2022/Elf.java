package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;

public class Elf {

    private final List<Integer> calorieItems = new ArrayList<>();

    public void addCalorieItem(int carlorieItem) {
        calorieItems.add(carlorieItem);
    }

    public int calcTotalCarlories() {
        return calorieItems.stream().reduce(0, Integer::sum);
    }

}
