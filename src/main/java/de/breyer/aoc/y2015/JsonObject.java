package de.breyer.aoc.y2015;

import java.util.ArrayList;
import java.util.List;

public class JsonObject {

    private final List<JsonProperty> properties = new ArrayList<>();

    public List<JsonProperty> getProperties() {
        return properties;
    }

    public void addProperty(JsonProperty property) {
        properties.add(property);
    }

    public boolean hasRedValue() {
        for (JsonProperty property : properties) {
            if (property.getValue() instanceof String && "\"red\"".equals(property.getValue())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        for (JsonProperty property : properties) {
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append(property);
        }

        builder.append("}");
        return builder.toString();
    }
}
