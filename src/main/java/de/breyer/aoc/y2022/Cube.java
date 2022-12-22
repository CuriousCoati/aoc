package de.breyer.aoc.y2022;

import de.breyer.aoc.data.Point2D;

public class Cube {

    private static final char OPEN = '.';
    private static final char BLOCKED = '#';

    private final char[][][] sides = new char[6][50][50];
    private Direction direction = Direction.RIGHT;
    private Point2D position = new Point2D(0, 0);
    private int currentSide = 0;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point2D getAbsolutePosition() {
        int xAbs = 0;
        int yAbs = 0;
        switch (currentSide) {
            case 0 -> {
                xAbs = position.getX() + 50;
                yAbs = position.getY();
            }
            case 1 -> {
                xAbs = position.getX() + 100;
                yAbs = position.getY();
            }
            case 2 -> {
                xAbs = position.getX() + 50;
                yAbs = position.getY() + 50;
            }
            case 3 -> {
                xAbs = position.getX() + 50;
                yAbs = position.getY() + 100;
            }
            case 4 -> {
                xAbs = position.getX();
                yAbs = position.getY() + 100;
            }
            case 5 -> {
                xAbs = position.getX();
                yAbs = position.getY() + 150;
            }
            default -> throw new IllegalStateException("Unexpected value: " + currentSide);
        }

        return new Point2D(xAbs, yAbs);
    }

    public void fillPosition(char character, int side, int x, int y) {
        sides[side][y][x] = character;
    }

    public void move(int distance) {
        for (int i = 0; i < distance; i++) {
            int nextX = direction.getXExpression().apply(position.getX(), 1);
            int nextY = direction.getYExpression().apply(position.getY(), 1);

            if (nextX < 0 || nextX >= 50 || nextY < 0 || nextY >= 50) {
                boolean found = findPositionOnOtherSide();
                if (!found) {
                    break;
                }
            } else {
                char mapValue = sides[currentSide][nextY][nextX];
                if (BLOCKED == mapValue) {
                    break;
                } else if (OPEN == mapValue) {
                    position = new Point2D(nextX, nextY);
                }
            }
        }
    }

    private boolean findPositionOnOtherSide() {
        Point2D nextPosition = null;
        int nextSide = -1;
        Direction nextDirection = null;
        switch (direction) {
            case UP -> {
                switch (currentSide) {
                    case 0 -> {
                        nextSide = 5;
                        nextDirection = Direction.RIGHT;
                        nextPosition = new Point2D(0, position.getX());
                    }
                    case 1 -> {
                        nextSide = 5;
                        nextDirection = Direction.UP;
                        nextPosition = new Point2D(position.getX(), 49);
                    }
                    case 2 -> {
                        nextSide = 0;
                        nextDirection = Direction.UP;
                        nextPosition = new Point2D(position.getX(), 49);
                    }
                    case 3 -> {
                        nextSide = 2;
                        nextDirection = Direction.UP;
                        nextPosition = new Point2D(position.getX(), 49);
                    }
                    case 4 -> {
                        nextSide = 2;
                        nextDirection = Direction.RIGHT;
                        nextPosition = new Point2D(0, position.getX());
                    }
                    case 5 -> {
                        nextSide = 4;
                        nextDirection = Direction.UP;
                        nextPosition = new Point2D(position.getX(), 49);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + currentSide);
                }
            }
            case RIGHT -> {
                switch (currentSide) {
                    case 0 -> {
                        nextSide = 1;
                        nextDirection = Direction.RIGHT;
                        nextPosition = new Point2D(0, position.getY());
                    }
                    case 1 -> {
                        nextSide = 3;
                        nextDirection = Direction.LEFT;
                        nextPosition = new Point2D(49, 49 - position.getY());
                    }
                    case 2 -> {
                        nextSide = 1;
                        nextDirection = Direction.UP;
                        nextPosition = new Point2D(position.getY(), 49);
                    }
                    case 3 -> {
                        nextSide = 1;
                        nextDirection = Direction.LEFT;
                        nextPosition = new Point2D(49, 49 - position.getY());
                    }
                    case 4 -> {
                        nextSide = 3;
                        nextDirection = Direction.RIGHT;
                        nextPosition = new Point2D(0, position.getY());
                    }
                    case 5 -> {
                        nextSide = 3;
                        nextDirection = Direction.UP;
                        nextPosition = new Point2D(position.getY(), 49);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + currentSide);
                }
            }
            case DOWN -> {
                switch (currentSide) {
                    case 0 -> {
                        nextSide = 2;
                        nextDirection = Direction.DOWN;
                        nextPosition = new Point2D(position.getX(), 0);
                    }
                    case 1 -> {
                        nextSide = 2;
                        nextDirection = Direction.LEFT;
                        nextPosition = new Point2D(49, position.getX());
                    }
                    case 2 -> {
                        nextSide = 3;
                        nextDirection = Direction.DOWN;
                        nextPosition = new Point2D(position.getX(), 0);
                    }
                    case 3 -> {
                        nextSide = 5;
                        nextDirection = Direction.LEFT;
                        nextPosition = new Point2D(49, position.getX());
                    }
                    case 4 -> {
                        nextSide = 5;
                        nextDirection = Direction.DOWN;
                        nextPosition = new Point2D(position.getX(), 0);
                    }
                    case 5 -> {
                        nextSide = 1;
                        nextDirection = Direction.DOWN;
                        nextPosition = new Point2D(position.getX(), 0);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + currentSide);
                }
            }
            case LEFT -> {
                switch (currentSide) {
                    case 0 -> {
                        nextSide = 4;
                        nextDirection = Direction.RIGHT;
                        nextPosition = new Point2D(0, 49 - position.getY());
                    }
                    case 1 -> {
                        nextSide = 0;
                        nextDirection = Direction.LEFT;
                        nextPosition = new Point2D(49, position.getY());
                    }
                    case 2 -> {
                        nextSide = 4;
                        nextDirection = Direction.DOWN;
                        nextPosition = new Point2D(position.getY(), 0);
                    }
                    case 3 -> {
                        nextSide = 4;
                        nextDirection = Direction.LEFT;
                        nextPosition = new Point2D(49, position.getY());
                    }
                    case 4 -> {
                        nextSide = 0;
                        nextDirection = Direction.RIGHT;
                        nextPosition = new Point2D(0, 49 - position.getY());
                    }
                    case 5 -> {
                        nextSide = 0;
                        nextDirection = Direction.DOWN;
                        nextPosition = new Point2D(position.getY(), 0);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + currentSide);
                }
            }
        }

        char mapValue = sides[nextSide][nextPosition.getY()][nextPosition.getX()];

        if (mapValue == OPEN) {
            currentSide = nextSide;
            direction = nextDirection;
            position = nextPosition;
            return true;
        } else {
            return false;
        }

    }
}
