package de.breyer.aoc.y2015;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2015_09")
public class D09 extends AbstractAocPuzzle {

    private Route shortestRoute;
    private Route longestRoute;

    @Override
    protected void partOne() {
        shortestRoute = null;
        longestRoute = null;

        List<CityDistance> distances = convertInput();
        Set<String> distinctCities = findDistinctCities(distances);
        findShortestAndLongestRoute(distances, distinctCities);

        System.out.println("shortest route: " + shortestRoute.toString());
    }

    public List<CityDistance> convertInput() {
        return lines.stream().map(this::convertLine).collect(Collectors.toList());
    }

    private CityDistance convertLine(String line) {
        String[] split = line.split(" ");
        return new CityDistance(split[0], split[2], Integer.parseInt(split[4]));
    }

    private Set<String> findDistinctCities(List<CityDistance> distances) {
        Set<String> distinctCities = new HashSet<>();
        distances.forEach(distance -> {
            distinctCities.add(distance.cityOne());
            distinctCities.add(distance.cityTwo());
        });
        return distinctCities;
    }

    private void findShortestAndLongestRoute(List<CityDistance> distances, Set<String> distinctCities) {
        for (String city : distinctCities) {
            Queue<Route> queue = new ArrayDeque<>();
            queue.add(new Route(city));

            Route currentRoute = queue.poll();
            do {
                findNextRoutes(distances, queue, currentRoute);
                currentRoute = queue.poll();
            } while (null != currentRoute);
        }
    }

    private void findNextRoutes(List<CityDistance> distances, Queue<Route> queue, Route currentRoute) {
        List<CityDistance> possibleDestinations = distances.stream().filter(currentRoute::isPossibleMove).toList();

        if (possibleDestinations.isEmpty()) {
            if (null == shortestRoute || currentRoute.getTotalDistance() < shortestRoute.getTotalDistance()) {
                shortestRoute = currentRoute;
            }
            if (null == longestRoute || currentRoute.getTotalDistance() > longestRoute.getTotalDistance()) {
                longestRoute = currentRoute;
            }
        } else {
            possibleDestinations.stream().map(distance -> new Route(currentRoute, distance)).forEach(queue::add);
        }
    }

    @Override
    protected void partTwo() {
        System.out.println("longest route: " + longestRoute.toString());
    }
}
