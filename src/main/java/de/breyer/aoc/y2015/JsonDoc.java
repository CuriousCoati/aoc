package de.breyer.aoc.y2015;

public class JsonDoc {

    private JsonArray array;
    private JsonObject object;

    public Object getRoot() {
        return null != array ? array : object;
    }

    public void setArray(JsonArray array) {
        ensureEmpty();
        this.array = array;
    }

    public void setObject(JsonObject object) {
        ensureEmpty();
        this.object = object;
    }

    private void ensureEmpty() {
        if (null != this.array || null != this.object) {
            throw new IllegalStateException("should be empty");
        }
    }

    @Override
    public String toString() {
        return getRoot().toString();
    }
}
