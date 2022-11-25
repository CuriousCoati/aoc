package de.breyer.aoc.y2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Packet {

    private final int version;
    private final int type;
    private long literalValue;
    private String lengthTypeId;
    private long subPacketLength;
    private int subPacketCount;
    private final List<Packet> subPackets = new ArrayList<>();
    private BigInteger value;
    private int length;

    public int getVersion() {
        return version;
    }

    public int getType() {
        return type;
    }

    public void setLiteralValue(long literalValue) {
        this.literalValue = literalValue;
    }

    public long getLiteralValue() {
        return this.literalValue;
    }

    public void setLengthTypeId(String lengthTypeId) {
        this.lengthTypeId = lengthTypeId;
    }

    public void setSubPacketLength(long subPacketLength) {
        this.subPacketLength = subPacketLength;
    }

    public void setSubPacketCount(int subPacketCount) {
        this.subPacketCount = subPacketCount;
    }

    public void addSubPacket(Packet packet) {
        subPackets.add(packet);
    }

    public List<Packet> getSubPackets() {
        return subPackets;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public BigInteger getValue() {
        if (4 == type) {
            return BigInteger.valueOf(literalValue);
        } else {
            return value;
        }
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Packet(int version, int type) {
        this.version = version;
        this.type = type;
    }
}
