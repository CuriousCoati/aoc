package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameStateD23 {

    private final char[] hallway = new char[11];
    private final char[][] sideRooms;
    private final int roomDepth;
    private long usedEnergy = 0;
    private boolean finished = false;

    public char[] getHallway() {
        return hallway;
    }

    public char[][] getSideRooms() {
        return sideRooms;
    }

    public long getUsedEnergy() {
        return usedEnergy;
    }

    public boolean isFinished() {
        return finished;
    }

    public GameStateD23(int roomDepth) {
        this.roomDepth = roomDepth;
        sideRooms = new char[4][roomDepth];
        Arrays.fill(hallway, '0');
    }

    public GameStateD23(int roomDepth, char[] hallway, char[][] sideRooms, long usedEnergy) {
        this.roomDepth = roomDepth;
        this.sideRooms = new char[4][roomDepth];
        System.arraycopy(hallway, 0, this.hallway, 0, 11);
        for (int i = 0; i < 4; i++) {
            System.arraycopy(sideRooms[i], 0, this.sideRooms[i], 0, roomDepth);
        }
        this.usedEnergy = usedEnergy;
    }

    public void addAmphipod(int room, int roomPosition, char character) {
        sideRooms[room][roomPosition] = character;
    }

    public List<GameStateD23> calculatePossibleMoves() {
        List<GameStateD23> possibleMoves = new ArrayList<>();
        for (int pos = 0; pos < hallway.length; pos++) {
            GameStateD23 newGameState = canMoveHome(pos);
            if (null != newGameState) {
                possibleMoves.add(newGameState);
            }
        }
        for (int roomIndex = 0; roomIndex < 4; roomIndex++) {
            for (int roomPosition = 0; roomPosition < roomDepth; roomPosition++) {
                char amphipod = sideRooms[roomIndex][roomPosition];
                if (amphipod != '0') {
                    if (isNotAtHome(amphipod, roomIndex, roomPosition)) {
                        GameStateD23 newGameState = canMoveHome(roomIndex, roomPosition);
                        if (null != newGameState) {
                            possibleMoves.add(newGameState);
                        } else {
                            List<GameStateD23> gameStates = moveToHallway(amphipod, roomIndex, roomPosition);
                            possibleMoves.addAll(gameStates);
                        }
                    }
                    break;
                }
            }
        }
        return possibleMoves;
    }

    private List<GameStateD23> moveToHallway(char amphipod, int roomIndex, int roomPosition) {
        List<GameStateD23> resultList = new ArrayList<>();
        for (int i = 0; i < hallway.length; i++) {
            if (i != 2 && i != 4 && i != 6 && i != 8) {
                char hallwayContent = hallway[i];
                if (hallwayContent == '0' && wayIsFree(i, roomIndex)) {
                    GameStateD23 gameState = copy();
                    gameState.getHallway()[i] = amphipod;
                    gameState.getSideRooms()[roomIndex][roomPosition] = '0';
                    gameState.calculateUsedEnergy(i, roomIndex, roomPosition, amphipod);
                    resultList.add(gameState);
                }
            }
        }
        return resultList;
    }

    private boolean isNotAtHome(char amphipod, int roomIndex, int roomPosition) {
        int homeRoomIndex = getHomeRoomIndex(amphipod);
        if (homeRoomIndex == roomIndex) {
            for (int i = roomPosition + 1; i < roomDepth; i++) {
                if (amphipod != sideRooms[roomIndex][i]) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private GameStateD23 canMoveHome(int roomIndex, int roomPosition) {
        char amphipod = sideRooms[roomIndex][roomPosition];
        int homeRoomIndex = getHomeRoomIndex(amphipod);
        int homeRoomPosition = getFreeHomeRoomPosition(homeRoomIndex, amphipod);
        if (-1 != homeRoomPosition && wayIsFree(roomIndex * 2 + 2, homeRoomIndex)) {
            GameStateD23 gameState = copy();
            gameState.getSideRooms()[roomIndex][roomPosition] = '0';
            gameState.getSideRooms()[homeRoomIndex][homeRoomPosition] = amphipod;
            gameState.calculateUsedEnergy(roomIndex, roomPosition, homeRoomIndex, homeRoomPosition, amphipod);
            gameState.checkIfFinished();
            return gameState;
        }
        return null;
    }

    private GameStateD23 canMoveHome(int pos) {
        char amphipod = hallway[pos];
        if (amphipod != '0') {
            int homeRoomIndex = getHomeRoomIndex(amphipod);
            int homeRoomPosition = getFreeHomeRoomPosition(homeRoomIndex, amphipod);
            if (-1 != homeRoomPosition && wayIsFree(pos, homeRoomIndex)) {
                GameStateD23 gameState = copy();
                gameState.getHallway()[pos] = '0';
                gameState.getSideRooms()[homeRoomIndex][homeRoomPosition] = amphipod;
                gameState.calculateUsedEnergy(pos, homeRoomIndex, homeRoomPosition, amphipod);
                gameState.checkIfFinished();
                return gameState;
            }
        }
        return null;
    }

    private int getFreeHomeRoomPosition(int homeRoomIndex, char amphipod) {
        for (int i = roomDepth - 1; i >= 0; i--) {
            char tmp = sideRooms[homeRoomIndex][i];
            if (tmp == '0') {
                return i;
            } else if (tmp != amphipod) {
                break;
            }
        }
        return -1;
    }

    private boolean wayIsFree(int pos, int roomIndex) {
        int entrance = roomIndex * 2 + 2;

        int idx, end;
        if (entrance > pos) {
            idx = pos + 1;
            end = entrance;
        } else {
            idx = entrance;
            end = pos;
        }

        for (; idx < end; idx++) {
            char tmp = hallway[idx];
            if (tmp != '0') {
                return false;
            }
        }
        return true;
    }

    private int getHomeRoomIndex(char amphipod) {
        if (amphipod == 'A') {
            return 0;
        } else if (amphipod == 'B') {
            return 1;
        } else if (amphipod == 'C') {
            return 2;
        } else {
            return 3;
        }
    }

    private GameStateD23 copy() {
        return new GameStateD23(roomDepth, hallway, sideRooms, usedEnergy);
    }

    private void calculateUsedEnergy(int hallwayPos, int homeRoomIndex, int homeRoomPosition, char amphipod) {
        int steps = homeRoomPosition + 1;
        int entrance = homeRoomIndex * 2 + 2;
        if (entrance > hallwayPos) {
            steps += entrance - hallwayPos;
        } else {
            steps += hallwayPos - entrance;
        }

        this.usedEnergy += (long) steps * getStepEnergy(amphipod);
    }

    private void calculateUsedEnergy(int rooomIndex, int roomPosition, int homeRoomIndex, int homeRoomPosition, char amphipod) {
        int steps = homeRoomPosition + 1 + roomPosition + 1;
        int exit = rooomIndex * 2 + 2;
        int entrance = homeRoomIndex * 2 + 2;
        if (entrance > exit) {
            steps += entrance - exit;
        } else {
            steps += exit - entrance;
        }

        this.usedEnergy += (long) steps * getStepEnergy(amphipod);
    }

    private int getStepEnergy(char amphipod) {
        if (amphipod == 'A') {
            return 1;
        } else if (amphipod == 'B') {
            return 10;
        } else if (amphipod == 'C') {
            return 100;
        } else {
            return 1000;
        }
    }

    private void checkIfFinished() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < roomDepth; j++) {
                char amphipod = sideRooms[i][j];
                if (i == 0 && amphipod != 'A') {
                    return;
                } else if (i == 1 && amphipod != 'B') {
                    return;
                } else if (i == 2 && amphipod != 'C') {
                    return;
                } else if (i == 3 && amphipod != 'D') {
                    return;
                }
            }
        }
        finished = true;
    }
}
