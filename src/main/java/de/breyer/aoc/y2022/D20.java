package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_20")
public class D20 extends AbstractAocPuzzle {

    private Holder zeroHolder;

    @Override
    protected void partOne() {
        List<Holder> encryptedFile = parseInput();
        List<Holder> mixedList = mix(encryptedFile, 1);
        long sumOfCoordinates = sumCoordinates(mixedList);
        System.out.println("sum of coordinates: " + sumOfCoordinates);
    }

    private List<Holder> parseInput() {
        List<Holder> encryptedFile = lines.stream().map(Integer::parseInt).map(Holder::new).toList();
        zeroHolder = encryptedFile.stream().filter(holder -> holder.getNumber() == 0).findFirst().orElse(null);
        return encryptedFile;
    }

    private List<Holder> mix(List<Holder> encryptedFile, int rounds) {
        List<Holder> result = new ArrayList<>(encryptedFile);
        for (int i = 0; i < rounds; i++) {
            for (Holder holder : encryptedFile) {
                if (holder.getNumber() != 0) {
                    int idx = result.indexOf(holder);
                    result.remove(idx);
                    long resultIdx = idx + holder.getNumber();
                    int resultIdxInt;
                    if (resultIdx < 0) {
                        resultIdxInt = (int) (encryptedFile.size() - 1 - (Math.abs(resultIdx) % (encryptedFile.size() - 1)));
                    } else if (resultIdx >= encryptedFile.size()) {
                        resultIdxInt = (int) (resultIdx % (encryptedFile.size() - 1));
                    } else {
                        resultIdxInt = (int) resultIdx;
                    }

                    if (resultIdxInt == 0) {
                        resultIdxInt = encryptedFile.size() - 1;
                    }

                    result.add(resultIdxInt, holder);
                }
            }
        }
        return result;
    }

    private long sumCoordinates(List<Holder> mixedList) {
        long sum = 0;
        for (int i = 1; i <= 3; i++) {
            int idx = mixedList.indexOf(zeroHolder);
            int resultIndex = (i * 1000 + idx) % mixedList.size();
            sum += mixedList.get(resultIndex).getNumber();
        }
        return sum;
    }

    @Override
    protected void partTwo() {
        List<Holder> encryptedFile = parseInput();
        encryptedFile.forEach(holder -> holder.applyDecryptionKey(811589153));
        List<Holder> mixedList = mix(encryptedFile, 10);
        long sumOfCoordinates = sumCoordinates(mixedList);
        System.out.println("sum of coordinates: " + sumOfCoordinates);
    }

}
