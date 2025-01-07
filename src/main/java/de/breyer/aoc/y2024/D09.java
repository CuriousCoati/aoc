package de.breyer.aoc.y2024;

import java.util.ArrayList;
import java.util.List;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;
import lombok.Data;

@AocPuzzle("2024_09")
public class D09 extends AbstractAocPuzzle {

    private final List<FilesystemBlock> files = new ArrayList<>();
    private final List<FilesystemBlock> freeBlocks = new ArrayList<>();

    @Override
    protected void partOne() {
        var blockDisplay = diskMapToBlockDisplay();
        compactFileSystem(blockDisplay);
        var checksum = calculateChecksum(blockDisplay);
        System.out.println("checksum after compacting: " + checksum);
    }

    private List<String> diskMapToBlockDisplay() {
        var blockDisplay = new ArrayList<String>();
        var currentFile = 0;
        var file = true;

        for (var c : lines.get(0).toCharArray()) {
            var length = Integer.parseInt("" + c);

            for (int i = 0; i < length; i++) {
                if (file) {
                    blockDisplay.add("" + currentFile);
                } else {
                    blockDisplay.add(".");
                }
            }

            if (file) {
                currentFile++;
            }
            file = !file;
        }

        return blockDisplay;
    }

    private void compactFileSystem(List<String> blockDisplay) {
        var containsFreeSpace = true;

        do {
            var lastElement = blockDisplay.remove(blockDisplay.size() - 1);

            if (!lastElement.equals(".")) {
                var indexOfFreeSpace = blockDisplay.indexOf(".");
                blockDisplay.remove(indexOfFreeSpace);
                blockDisplay.add(indexOfFreeSpace, lastElement);
            }

            containsFreeSpace = blockDisplay.contains(".");

        } while (containsFreeSpace);
    }

    private Long calculateChecksum(List<String> blockDisplay) {
        var checksum = 0L;

        for (int i = 0; i < blockDisplay.size(); i++) {
            checksum += (long) i * Integer.parseInt(blockDisplay.get(i));
        }

        return checksum;
    }

    @Override
    protected void partTwo() {
        diskMapToBlocks();
        compactFileSystem();
        var checksum = calculateChecksum();
        System.out.println("checksum after compacting: " + checksum);
    }

    private void diskMapToBlocks() {
        files.clear();
        freeBlocks.clear();

        var currentFile = 0;
        var currentPosition = 0L;
        var file = true;

        for (var c : lines.get(0).toCharArray()) {
            var length = Integer.parseInt("" + c);

            if (file) {
                files.add(new FilesystemBlock(currentPosition, length, currentFile));
            } else {
                freeBlocks.add(new FilesystemBlock(currentPosition, length));
            }

            currentPosition += length;

            if (file) {
                currentFile++;
            }
            file = !file;
        }
    }

    private Long calculateChecksum() {
        var checksum = 0L;

        for (var file : files) {
            for (int i = 0; i < file.length; i++) {
                checksum += (file.position + i) * file.id;
            }
        }

        return checksum;
    }

    private void compactFileSystem() {
        for (var i = files.size() - 1; i >= 0; i--) {
            var file = files.get(i);

            for (var freeBlock : freeBlocks) {
                if (freeBlock.position > file.position) {
                    break;
                }

                if (freeBlock.length >= file.length) {
                    file.position = freeBlock.position;
                    freeBlock.length -= file.length;
                    freeBlock.position += file.length;
                }

            }
        }
    }

    @Data
    private static class FilesystemBlock {

        private long position;
        private long length;
        private long id;

        public FilesystemBlock(long position, long length, long id) {
            this(position, length);
            this.id = id;
        }

        public FilesystemBlock(long position, long length) {
            this.position = position;
            this.length = length;
        }

    }

}
