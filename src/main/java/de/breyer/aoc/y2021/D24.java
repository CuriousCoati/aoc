package de.breyer.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2021_24")
public class D24 extends AbstractAocPuzzle {

    private final Alu alu = new Alu();
    private final List<Parameters> parameters = new ArrayList<>();

    @Override
    protected void partOne() {
        convertInput();
        findModelNumber(true);
    }

    @Override
    protected void partTwo() {
        findModelNumber(false);
    }

    private void convertInput() {
        int idx = 0;
        int xAddend = -1;
        for (String line : lines) {
            alu.addInstruction(line);
            if (idx == 18) {
                idx = 0;
            } else if (idx == 5) {
                xAddend = Integer.parseInt(line.split(" ")[2]);
            } else if (idx == 15) {
                int yAddend = Integer.parseInt(line.split(" ")[2]);
                parameters.add(new Parameters(xAddend, yAddend));
            }
            idx++;
        }
    }

    private void findModelNumber(boolean largest) {
        Stack<StackItem> queue = new Stack<>();
        int[] digits = new int[14];

        for (int i = 0; i < parameters.size(); i++) {

            Parameters parameter = parameters.get(i);

            if (parameter.getXAddend() >= 10) {
                queue.add(new StackItem(i, parameter.getYAddend()));
            } else {
                StackItem popped = queue.pop();
                int addend = popped.getAddend() + parameter.getXAddend();
                int digit = -1;
                if (largest) {
                    for (int j = 9; j >= 1; j--) {
                        int sum = j + addend;
                        if (sum >=1 && sum <= 9) {
                            digit = j;
                            break;
                        }
                    }
                } else {
                    for (int j = 1; j <= 9; j++) {
                        int sum = j + addend;
                        if (sum >=1 && sum <= 9) {
                            digit = j;
                            break;
                        }
                    }
                }
                digits[popped.getDigitIndex()] = digit;
                digits[i] = digit + addend;
            }
        }

        for (int digit : digits) {
            System.out.print(digit);
        }
        System.out.println();
    }

    private static class Parameters {

        private final int xAddend;
        private final int yAddend;

        public int getXAddend() {
            return xAddend;
        }

        public int getYAddend() {
            return yAddend;
        }

        public Parameters(int xAddend, int yAddend)  {
            this.xAddend = xAddend;
            this.yAddend = yAddend;
        }

    }

    private static class StackItem {

        private final int digitIndex;
        private final int addend;

        public int getDigitIndex() {
            return digitIndex;
        }

        public int getAddend() {
            return addend;
        }

        public StackItem(int digitIndex, int addend) {
            this.digitIndex = digitIndex;
            this.addend = addend;
        }
    }

}
