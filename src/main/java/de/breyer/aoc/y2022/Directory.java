package de.breyer.aoc.y2022;

import java.util.ArrayList;
import java.util.List;

public class Directory {

    private final String name;
    private final List<Directory> directories = new ArrayList<>();
    private final List<File> files = new ArrayList<>();
    private final Directory parent;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public Directory getParent() {
        return parent;
    }

    public void addFile(String name, long size) {
        files.add(new File(name, size));
    }

    public void addDirectory(Directory dir) {
        directories.add(dir);
    }

    public long getTotalSize() {
        long sizeFiles = files.stream().map(File::size).reduce(0L, Long::sum);
        long sizeDirectories = directories.stream().map(Directory::getTotalSize).reduce(0L, Long::sum);
        return sizeFiles + sizeDirectories;
    }
}
