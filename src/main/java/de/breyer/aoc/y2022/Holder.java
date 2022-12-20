package de.breyer.aoc.y2022;

import java.util.Objects;
import java.util.UUID;

public class Holder {

    private final String uuid = UUID.randomUUID().toString();
    private long number;

    public Holder(long number) {
        this.number = number;
    }

    public long getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Holder holder = (Holder) o;
        return Objects.equals(uuid, holder.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public void applyDecryptionKey(int decryptionKey) {
        number *= decryptionKey;
    }

    @Override
    public String toString() {
        return "Holder{" +
                "uuid='" + uuid + '\'' +
                ", number=" + number +
                '}';
    }
}
