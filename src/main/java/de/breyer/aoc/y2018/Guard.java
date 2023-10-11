package de.breyer.aoc.y2018;

public class Guard {

    private final String id;
    private final int[] sleepDiary = new int[60];
    private int totalSleepTime;
    private int mostAsleepMinute = -1;

    public String getId() {
        return id;
    }

    public Guard(String id) {
        this.id = id;
    }

    public int getNumericId() {
        return Integer.parseInt(id.substring(1));
    }

    public void addSleepTime(int start, int end) {
        for (int i = start; i < end; i++) {
            sleepDiary[i]++;
            totalSleepTime++;
        }
    }

    public int getTotalSleepTime() {
        return totalSleepTime;
    }

    public int mostAsleepMinute() {
        if (mostAsleepMinute == -1) {
            int count = -1;
            for (int i = 0; i < 60; i++) {
                if (count == -1) {
                    count = sleepDiary[i];
                    mostAsleepMinute = i;
                } else if (count < sleepDiary[i]) {
                    count = sleepDiary[i];
                    mostAsleepMinute = i;
                }
            }
        }

        return mostAsleepMinute;
    }

    public int daysSleptAtMinute(int minute) {
        return sleepDiary[minute];
    }

    @Override
    public String toString() {
        return getId() + " (ID) * " + mostAsleepMinute() + " (minute) = " + getNumericId() * mostAsleepMinute();
    }
}
