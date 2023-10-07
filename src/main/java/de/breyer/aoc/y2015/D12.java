package de.breyer.aoc.y2015;

import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_12")
public class D12 extends AbstractAocPuzzle {

    @Override
    protected void partOne() {
        System.out.println("sum: " + sumNumbers(lines.get(0)));
    }

    private long sumNumbers(String text) {
        long sum = 0;

        StringBuilder sb = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isDigit(c) || '-' == c) {
                sb.append(c);
            } else {
                if (!sb.isEmpty()) {
                    sum += Long.parseLong(sb.toString());
                    sb.setLength(0);
                }
            }
        }

        return sum;
    }

    @Override
    protected void partTwo() {
        System.out.println("sum: " + sumWithoutRed(lines.get(0)));
    }

    private long sumWithoutRed(String text) {
        JsonDoc jsonDoc = parseJson(text);
        Object root = jsonDoc.getRoot();
        return root instanceof JsonArray ? sum((JsonArray) root) : sum((JsonObject) root);
    }

    private long sum(JsonArray array) {
        long sum = 0;
        for (Object obj : array.getItems()) {
            if (obj instanceof Long) {
                sum += (long) obj;
            } else if (obj instanceof JsonArray) {
                sum += sum((JsonArray) obj);
            } else if (obj instanceof JsonObject) {
                sum += sum((JsonObject) obj);
            }
        }
        return sum;
    }

    private long sum(JsonObject object) {
        if (object.hasRedValue()) {
            return 0;
        }

        long sum = 0;
        for (JsonProperty property : object.getProperties()) {
            Object obj = property.getValue();
            if (obj instanceof Long) {
                sum += (long) obj;
            } else if (obj instanceof JsonArray) {
                sum += sum((JsonArray) obj);
            } else if (obj instanceof JsonObject) {
                sum += sum((JsonObject) obj);
            }
        }
        return sum;
    }

    private JsonDoc parseJson(String text) {
        JsonDoc jsonDoc = new JsonDoc();
        char c = text.charAt(0);

        if ('[' == c) {
            JsonArray array = new JsonArray();
            jsonDoc.setArray(array);
            parseArray(array, text, 1);
        } else if ('{' == c) {
            JsonObject object = new JsonObject();
            jsonDoc.setObject(object);
            parseObject(object, text, 1);
        } else {
            throw new IllegalArgumentException("json string shouldn't start with '%s'".formatted(c));
        }

        return jsonDoc;
    }

    private int parseArray(JsonArray array, String text, int index) {
        StringBuilder builder = new StringBuilder();
        boolean numeric = false;
        boolean exit = false;

        do {
            char c = text.charAt(index);

            if ('[' == c) {
                JsonArray subArray = new JsonArray();
                array.add(subArray);
                index++;
                index = parseArray(subArray, text, index);
            } else if ('{' == c) {
                JsonObject subObject = new JsonObject();
                array.add(subObject);
                index++;
                index = parseObject(subObject, text, index);
            } else if (',' == c) {
                if (!builder.isEmpty()) {
                    if (numeric) {
                        array.add(Long.parseLong(builder.toString()));
                        numeric = false;
                    } else {
                        array.add(builder.toString());
                    }
                    builder.setLength(0);
                }
                index++;
            } else if (']' == c) {
                if (!builder.isEmpty()) {
                    if (numeric) {
                        array.add(Long.parseLong(builder.toString()));
                        numeric = false;
                    } else {
                        array.add(builder.toString());
                    }
                    builder.setLength(0);
                }
                exit = true;
                index++;
            } else {
                if (builder.isEmpty()) {
                    numeric = c != '\"';
                }
                builder.append(c);
                index++;
            }
        } while (!exit);

        return index;
    }

    private int parseObject(JsonObject object, String text, int index) {
        StringBuilder builder = new StringBuilder();
        JsonProperty property = new JsonProperty();
        object.addProperty(property);
        boolean numeric = false;
        boolean exit = false;

        do {
            char c = text.charAt(index);

            if ('[' == c) {
                JsonArray subArray = new JsonArray();
                property.setArray(subArray);
                index++;
                index = parseArray(subArray, text, index);
            } else if ('{' == c) {
                JsonObject subObject = new JsonObject();
                property.setObject(subObject);
                index++;
                index = parseObject(subObject, text, index);
            } else if (',' == c) {
                if (!builder.isEmpty()) {
                    if (numeric) {
                        property.setLongValue(Long.parseLong(builder.toString()));
                        numeric = false;
                    } else {
                        property.setStringValue(builder.toString());
                    }

                    builder.setLength(0);
                }

                property = new JsonProperty();
                object.addProperty(property);
                index++;
            } else if (':' == c) {
                property.setName(builder.toString());
                builder.setLength(0);
                index++;
            } else if ('}' == c) {
                if (!builder.isEmpty()) {
                    if (numeric) {
                        property.setLongValue(Long.parseLong(builder.toString()));
                        numeric = false;
                    } else {
                        property.setStringValue(builder.toString());
                    }
                    builder.setLength(0);
                }
                exit = true;
                index++;
            } else {
                if (builder.isEmpty()) {
                    numeric = c != '\"';
                }
                builder.append(c);
                index++;
            }
        } while (!exit);

        return index;
    }

}
