package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;

public class JsonArray {

    private final List<Object> items = new ArrayList<>();

    public List<Object> getItems() {
        return items;
    }

    public void add(String string) {
        items.add(string);
    }

    public void add(long longValue) {
        items.add(longValue);
    }

    public void add(JsonObject object) {
        items.add(object);
    }

    public void add(JsonArray array) {
        items.add(array);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (Object item : items) {
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append(item);
        }

        builder.append("]");
        return builder.toString();
    }
}
