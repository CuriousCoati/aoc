package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2022_13")
public class D13 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        List<List> packages = parseInput();
        int indicesSum = evaluatePairs(packages);
        System.out.println("sum of indices: " + indicesSum);
    }

    private List<List> parseInput() {
        List<List> packages = new ArrayList<>();

        for (String line : lines) {
            if (!line.isEmpty()) {
                StringBuilder numberBuilder = new StringBuilder();
                Stack<List> lists = new Stack<>();

                for (char character : line.toCharArray()) {
                    if (character == '[') {
                        List newList = new ArrayList();

                        if (lists.isEmpty()) {
                            packages.add(newList);
                        } else {
                            lists.peek().add(newList);
                        }
                        lists.push(newList);
                    } else if (character == ']') {
                        buildNumber(numberBuilder, lists);
                        numberBuilder = new StringBuilder();
                        lists.pop();
                    } else if (character == ',') {
                        buildNumber(numberBuilder, lists);
                        numberBuilder = new StringBuilder();
                    } else {
                        numberBuilder.append(character);
                    }
                }
            }
        }

        return packages;
    }

    private void buildNumber(StringBuilder numberBuilder, Stack<List> lists) {
        if (!numberBuilder.isEmpty()) {
            Integer number = Integer.parseInt(numberBuilder.toString());
            lists.peek().add(number);
        }
    }

    private int evaluatePairs(List<List> packages) {
        int indicesSum = 0;

        for (int i = 0; i < packages.size(); i += 2) {
            List first = packages.get(i);
            List second = packages.get(i + 1);

            int result = compareList(first, second);
            if (result <= 0) {
                indicesSum += (i / 2) + 1;
            }
        }

        return indicesSum;
    }

    private int compareList(List first, List second) {
        int rightOrder = 0;
        for (int i = 0; i < first.size(); i++) {

            if (second.size() <= i) {
                rightOrder = 1;
                break;
            }

            Object firstContent = first.get(i);
            Object secondContent = second.get(i);

            boolean firstIsInteger = firstContent instanceof Integer;
            boolean secondIsInteger = secondContent instanceof Integer;
            boolean firstIsList = firstContent instanceof List;
            boolean secondIsList = secondContent instanceof List;

            if (firstIsInteger && secondIsInteger) {
                if ((Integer) firstContent < (Integer) secondContent) {
                    rightOrder = -1;
                    break;
                } else if ((Integer) firstContent > (Integer) secondContent) {
                    rightOrder = 1;
                    break;
                }
            } else if (firstIsList && secondIsList) {
                int result = compareList((List) firstContent, (List) secondContent);
                if (result < 0) {
                    rightOrder = -1;
                    break;
                } else if (result > 0) {
                    rightOrder = 1;
                    break;
                }
            } else if (firstIsInteger && secondIsList) {
                List firstAsList = new ArrayList();
                firstAsList.add(firstContent);
                int result = compareList(firstAsList, (List) secondContent);
                if (result < 0) {
                    rightOrder = -1;
                    break;
                } else if (result > 0) {
                    rightOrder = 1;
                    break;
                }
            } else if (firstIsList && secondIsInteger) {
                List secondAsList = new ArrayList();
                secondAsList.add(secondContent);
                int result = compareList((List) firstContent, secondAsList);
                if (result < 0) {
                    rightOrder = -1;
                    break;
                } else if (result > 0) {
                    rightOrder = 1;
                    break;
                }
            }

        }

        if (rightOrder == 0 && first.size() < second.size()) {
            rightOrder = -1;
        }

        return rightOrder;
    }

    @Override
    protected void partTwo() {
        List<List> packages = parseInput();

        List subList = new ArrayList();
        subList.add(2);

        ArrayList dividerOne = new ArrayList();
        dividerOne.add(subList);

        subList = new ArrayList();
        subList.add(6);

        ArrayList dividerTwo = new ArrayList();
        dividerTwo.add(subList);

        packages.add(dividerOne);
        packages.add(dividerTwo);

        packages.sort(this::compareList);

        int indexDividerOne = packages.indexOf(dividerOne) + 1;
        int indexDividerTwo = packages.indexOf(dividerTwo) + 1;
        int decoderKey = indexDividerOne * indexDividerTwo;

        System.out.println("sum of indices: " + decoderKey);
    }

}
