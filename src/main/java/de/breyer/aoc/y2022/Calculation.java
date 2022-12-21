package de.breyer.aoc.y2022;

public class Calculation {

    private final String name;
    private String first;
    private String second;
    private String operation;
    private Long value = null;

    public Calculation(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public Calculation(String name, String first, String second, String operation) {
        this.name = name;
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public long getValue() {
        return value;
    }

    public void perform(long first, long second) {
        value = switch (operation) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "*" -> first * second;
            case "/" -> first / second;
            default -> throw new IllegalStateException("unknown operation " + operation);
        };
    }

    public long reverseCalc(Long result, long value, String knowValue) {
        if (null == result) {
            return value;
        }

        return switch (operation) {
            case "+" -> result - value;
            case "-" -> knowValue.equals(first) ? value - result : result + value;
            case "*" -> result / value;
            case "/" -> knowValue.equals(first) ? value / result : result * value;
            default -> throw new IllegalStateException("unknown operation " + operation);
        };
    }

    public boolean isSet() {
        return null != value;
    }
}
