package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2022_07")
public class D07 extends AbstractAocPuzzle {

    private Directory rootDir;

    @Override
    protected void partOne() {
        parseInput();
        List<Directory> appropriateDirs = new ArrayList<>();
        checkDir(rootDir, appropriateDirs, totalSize -> totalSize <= 100000);
        long size = appropriateDirs.stream().map(Directory::getTotalSize).reduce(0L, Long::sum);
        System.out.println("total size of appropriate dirs: " + size);
    }

    private void parseInput() {
        Directory currentDir = null;
        for (String line : lines) {
            String[] split = line.split(" ");

            if ("$".equals(split[0])) {
                if (split[1].equals("cd")) {
                    if ("..".equals(split[2])) {
                        currentDir = currentDir.getParent();
                    } else {
                        Directory dir = new Directory(split[2], currentDir);

                        if ("/".equals(split[2])) {
                            rootDir = dir;
                        }

                        if (null != currentDir) {
                            currentDir.addDirectory(dir);
                        }

                        currentDir = dir;
                    }
                }
            } else if ("dir".equals(split[0])) {

            } else {
                currentDir.addFile(split[1], Long.parseLong(split[0]));
            }
        }
    }

    private void checkDir(Directory currentDir, List<Directory> appropriateDirs, Function<Long, Boolean> expression) {
        long totalSize = currentDir.getTotalSize();
        if (expression.apply(totalSize)) {
            appropriateDirs.add(currentDir);
        }
        currentDir.getDirectories().forEach(directory -> checkDir(directory, appropriateDirs, expression));
    }

    @Override
    protected void partTwo() {
        long freeSpace = 70000000 - rootDir.getTotalSize();
        long neededSpace = 30000000 - freeSpace;
        List<Directory> appropriateDirs = new ArrayList<>();
        checkDir(rootDir, appropriateDirs, totalSize -> totalSize >= neededSpace);
        Optional<Long> size = appropriateDirs.stream().map(Directory::getTotalSize).min(Long::compare);
        System.out.println("size of smallest dir big enough: " + size.get());
    }

}
