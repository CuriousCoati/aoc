package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private final List<String> cities = new ArrayList<>();
    private long totalDistance;

    public Route(String city) {
        cities.add(city);
    }

    public Route(Route route, CityDistance cityDistance) {
        cities.addAll(route.cities);

        String currentCity = cities.get(cities.size() - 1);
        if (cityDistance.cityOne().equals(currentCity)) {
            cities.add(cityDistance.cityTwo());
        } else if (cityDistance.cityTwo().equals(currentCity)) {
            cities.add(cityDistance.cityOne());
        } else {
            throw new IllegalStateException("should never reach here");
        }

        totalDistance = route.totalDistance + cityDistance.distance();
    }

    public long getTotalDistance() {
        return totalDistance;
    }

    public void addCity(String city) {
        cities.add(city);
    }

    public boolean isPossibleMove(CityDistance cityDistance) {
        String currentCity = cities.get(cities.size() - 1);
        return (cityDistance.cityOne().equals(currentCity) && !cities.contains(cityDistance.cityTwo())) ||
                (cityDistance.cityTwo().equals(currentCity) && !cities.contains(cityDistance.cityOne()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.join(" -> ", cities));
        builder.append(" = ").append(totalDistance);
        return builder.toString();
    }
}
