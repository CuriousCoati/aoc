package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import de.breyer.aoc.data.Point2D;

@AocPuzzle("2022_23")
public class D23 extends AbstractAocPuzzle {

    private Map<Point2D, ElfLocation> locations;
    private List<Orientation> considerationList;
    private int currentRound;

    @Override
    protected void partOne() {
        init();
        parseInput();
        simulateRounds(10);
        int emptyTiles = countEmptyTiles();
        System.out.println("empty tiles: " + emptyTiles);
    }

    private void init() {
        currentRound = 0;
        considerationList = new ArrayList<>();
        considerationList.add(Orientation.NORTH);
        considerationList.add(Orientation.SOUTH);
        considerationList.add(Orientation.WEST);
        considerationList.add(Orientation.EAST);
    }

    private void parseInput() {
        locations = new HashMap<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if ('#' == line.charAt(x)) {
                    Point2D position = new Point2D(x, y);
                    locations.put(position, new ElfLocation(position));
                }
            }
        }
    }

    private void simulateRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            currentRound++;
            considerMoves();
            if (moveElfs()) {
                break;
            }
            updateConsiderationList();
        }
    }

    private void considerMoves() {
        for (ElfLocation elf : locations.values()) {

            boolean needsToMove = false;

            for (int x = elf.getPosition().getX() - 1; x <= elf.getPosition().getX() + 1; x++) {
                for (int y = elf.getPosition().getY() - 1; y <= elf.getPosition().getY() + 1; y++) {
                    if ((x != elf.getPosition().getX() || y != elf.getPosition().getY()) && locations.containsKey(new Point2D(x, y))) {
                        needsToMove = true;
                        break;
                    }
                }
                if (needsToMove) {
                    break;
                }
            }

            if (needsToMove) {
                for (Orientation orientation : considerationList) {
                    if (orientation.canMove(elf.getPosition(), locations)) {
                        elf.setProposedPosition(Optional.of(orientation.getPosition(elf.getPosition())));
                        break;
                    }
                }
            }
        }
    }

    private boolean moveElfs() {
        List<ElfLocation> elfs = locations.values().stream().filter(elf -> elf.getProposedPosition().isPresent()).collect(Collectors.toList());

        if (elfs.isEmpty()) {
            return true;
        }

        do {
            ElfLocation current = elfs.get(0);
            elfs.remove(current);

            Point2D proposedPosition = current.getProposedPosition().orElse(null);
            boolean canMove = true;

            for (int i = 0; i < elfs.size(); i++) {
                ElfLocation otherElf = elfs.get(i);
                if (proposedPosition.equals(otherElf.getProposedPosition().orElse(null))) {
                    canMove = false;
                    elfs.remove(otherElf);
                    otherElf.setProposedPosition(Optional.empty());
                }
            }
            current.setProposedPosition(Optional.empty());

            if (canMove) {
                locations.remove(current.getPosition());
                current.setPosition(proposedPosition);
                locations.put(proposedPosition, current);
            }
        } while (!elfs.isEmpty());

        return false;
    }

    private void updateConsiderationList() {
        Orientation firstOrientation = considerationList.remove(0);
        considerationList.add(firstOrientation);
    }

    private int countEmptyTiles() {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point2D point : locations.keySet()) {
            minX = Integer.min(minX, point.getX());
            maxX = Integer.max(maxX, point.getX());
            minY = Integer.min(minY, point.getY());
            maxY = Integer.max(maxY, point.getY());
        }

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        return width * height - locations.size();
    }

    private void print() {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point2D point : locations.keySet()) {
            minX = Integer.min(minX, point.getX());
            maxX = Integer.max(maxX, point.getX());
            minY = Integer.min(minY, point.getY());
            maxY = Integer.max(maxY, point.getY());
        }

        for (int y = minY; y < maxY + 1; y++) {
            for (int x = minX; x < maxX + 1; x++) {
                char character = locations.containsKey(new Point2D(x, y)) ? '#' : '.';
                System.out.print(character);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    protected void partTwo() {
        simulateRounds(10000);
        System.out.println("final round: " + currentRound);
    }

    private enum Orientation {
        NORTH((x, i) -> x + i, (y, i) -> y - 1),
        SOUTH((x, i) -> x + i, (y, i) -> y + 1),
        WEST((x, i) -> x - 1, (y, i) -> y + i),
        EAST((x, i) -> x + 1, (y, i) -> y + i);

        private final BiFunction<Integer, Integer, Integer> xExpression;
        private final BiFunction<Integer, Integer, Integer> yExpression;

        Orientation(BiFunction<Integer, Integer, Integer> xExpression, BiFunction<Integer, Integer, Integer> yExpression) {
            this.xExpression = xExpression;
            this.yExpression = yExpression;
        }

        public boolean canMove(Point2D position, Map<Point2D, ElfLocation> locations) {
            boolean canMove = true;

            for (int i = -1; i <= 1; i++) {
                int nextX = xExpression.apply(position.getX(), i);
                int nextY = yExpression.apply(position.getY(), i);

                if (locations.containsKey(new Point2D(nextX, nextY))) {
                    canMove = false;
                    break;
                }
            }

            return canMove;
        }

        public Point2D getPosition(Point2D position) {
            int nextX = xExpression.apply(position.getX(), 0);
            int nextY = yExpression.apply(position.getY(), 0);
            return new Point2D(nextX, nextY);
        }
    }

    private static class ElfLocation {

        private Point2D position;
        private Optional<Point2D> proposedPosition = Optional.empty();

        public ElfLocation(Point2D position) {
            this.position = position;
        }

        public Point2D getPosition() {
            return position;
        }

        public void setPosition(Point2D position) {
            this.position = position;
        }

        public Optional<Point2D> getProposedPosition() {
            return proposedPosition;
        }

        public void setProposedPosition(Optional<Point2D> proposedPosition) {
            this.proposedPosition = proposedPosition;
        }

    }

}
