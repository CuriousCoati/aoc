package de.breyer.aoc.y2015;

public class JsonProperty {

    private String name;
    private String stringValue;
    private Long longValue;
    private JsonObject object;
    private JsonArray array;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public void setObject(JsonObject object) {
        this.object = object;
    }

    public void setArray(JsonArray array) {
        this.array = array;
    }

    public Object getValue() {
        if (null != stringValue) {
            return stringValue;
        } else if (null != longValue) {
            return longValue;
        } else if (null != object) {
            return object;
        } else if (null != array) {
            return array;
        }

        throw new IllegalStateException("not filled");
    }

    @Override
    public String toString() {
        return name + ":" + getValue();
    }
}
