package de.breyer.aoc.y2022;

import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_25")
public class D25 extends AbstractAocPuzzle {

    protected void partOne() {
        long sumDecimal = lines.stream().map(this::snafuNumberToDecimal).reduce(0L, Long::sum);
        System.out.println("sum: " + sumDecimal + " -> " + decimalNumberToSnafuNumber(sumDecimal));
    }

    public long snafuNumberToDecimal(String snafuNumber) {
        long decimalNumber = 0;
        for (int i = 0; i < snafuNumber.length(); i++) {
            char snafuDigit = snafuNumber.charAt(i);
            long decimalDigit = snafuDigitToDecimalDigit(snafuDigit);
            int power = snafuNumber.length() - i - 1;
            decimalNumber += decimalDigit * Math.pow(5, power);
        }
        return decimalNumber;
    }

    private long snafuDigitToDecimalDigit(char snafuDigit) {
        return switch (snafuDigit) {
            case '2' -> 2;
            case '1' -> 1;
            case '0' -> 0;
            case '-' -> -1;
            case '=' -> -2;
            default -> throw new IllegalStateException("Unexpected value: " + snafuDigit);
        };
    }

    private String decimalNumberToSnafuNumber(long decimal) {
        StringBuilder builder = new StringBuilder();
        long quotient = decimal;

        do {
            long result = quotient / 5;
            int rest = (int) (quotient % 5);
            char snafuDigit = quinaryDigitToSnafuDigit(rest);

            if (snafuDigit == '=' || snafuDigit == '-') {
                result += 1;
            }

            builder.insert(0, snafuDigit);
            quotient = result;
        } while (quotient != 0);

        return builder.toString();
    }

    private char quinaryDigitToSnafuDigit(int quinaryDigit) {
        return switch (quinaryDigit) {
            case 0 -> '0';
            case 1 -> '1';
            case 2 -> '2';
            case 3 -> '=';
            case 4 -> '-';
            default -> throw new IllegalStateException("Unexpected value: " + quinaryDigit);
        };
    }

    @Override
    protected void partTwo() {
    }

}
