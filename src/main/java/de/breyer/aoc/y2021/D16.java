package de.breyer.aoc.y2021;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2021_16")
public class D16 extends AbstractAocPuzzle {

    private static final Map<String, String> digiMap = new HashMap<>();

    static {
        digiMap.put("0", "0000");
        digiMap.put("1", "0001");
        digiMap.put("2", "0010");
        digiMap.put("3", "0011");
        digiMap.put("4", "0100");
        digiMap.put("5", "0101");
        digiMap.put("6", "0110");
        digiMap.put("7", "0111");
        digiMap.put("8", "1000");
        digiMap.put("9", "1001");
        digiMap.put("A", "1010");
        digiMap.put("B", "1011");
        digiMap.put("C", "1100");
        digiMap.put("D", "1101");
        digiMap.put("E", "1110");
        digiMap.put("F", "1111");
    }

    private String binaryString;
    private Packet outmostPacket;

    @Override
    protected void partOne() {
        convertToBinaryString();
        parsePacket(null,0);
        sumVersions();
    }

    @Override
    protected void partTwo() {
        calculateValues();
    }

    private void convertToBinaryString() {
        char[] hex = lines.get(0).toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char h : hex) {
            builder.append(digiMap.get(String.valueOf(h)));
        }
        binaryString = builder.toString();
    }

    private int parsePacket(Packet parent, int offset) {
        int begin = offset;
        int version = Integer.parseInt(binaryString.substring(offset, offset + 3), 2);
        offset += 3;
        int type = Integer.parseInt(binaryString.substring(offset, offset + 3), 2);
        offset += 3;

        Packet packet = new Packet(version, type);

        if (type == 4) {
            boolean lastGroup = false;
            StringBuilder literalValue = new StringBuilder();
            do {
                String prefixBit = binaryString.substring(offset, offset+1);
                offset += 1;

                if ("0".equals(prefixBit)) {
                    lastGroup = true;
                }

                literalValue.append(binaryString, offset, offset + 4);
                offset += 4;
            } while(!lastGroup);
            packet.setLiteralValue(Long.parseLong(literalValue.toString(), 2));
            packet.setLength(offset - begin);
        } else {
            String lengthTypeId = binaryString.substring(offset, offset+1);
            packet.setLengthTypeId(lengthTypeId);
            offset += 1;

            if ("0".equals(lengthTypeId)) {
                long subPacketLength = Long.parseLong(binaryString.substring(offset, offset + 15), 2);
                offset += 15;
                packet.setSubPacketLength(subPacketLength);

                int offsetStart = offset;
                do {
                    offset = parsePacket(packet, offset);
                } while(offset < offsetStart + subPacketLength);
                packet.setLength(offset - begin);

            } else if ("1".equals(lengthTypeId)) {
                int subPacketCount = Integer.parseInt(binaryString.substring(offset, offset + 11), 2);
                offset += 11;
                packet.setSubPacketCount(subPacketCount);

                for (int i = 0; i < subPacketCount; i++) {
                    offset = parsePacket(packet, offset);
                }
                packet.setLength(offset - begin);

            } else {
                throw new RuntimeException("illegal length type ID: " + lengthTypeId);
            }

        }

        if (null == parent) {
            outmostPacket = packet;
        } else {
            parent.addSubPacket(packet);
        }
        return offset;
    }

    private void sumVersions() {
        int versionSum = sumSubPacketVersions(outmostPacket, 0);
        System.out.println("sum of version numbers: " + versionSum);
    }

    private int sumSubPacketVersions(Packet packet, int sum) {
        sum += packet.getVersion();
        for (Packet subPacket : packet.getSubPackets()) {
            sum = sumSubPacketVersions(subPacket, sum);
        }
        return sum;
    }

    private void calculateValues() {
        BigInteger value = getValueOfPacket(outmostPacket);
        System.out.println("value: " + value);
    }

    private BigInteger getValueOfPacket(Packet packet) {
        if (0 == packet.getType()) {
            BigInteger sum = BigInteger.ZERO;
            for (Packet subPacket : packet.getSubPackets()) {
                sum = sum.add(getValueOfPacket(subPacket));
            }
            packet.setValue(sum);
        } else if (1 == packet.getType()) {
            BigInteger product = BigInteger.ONE;
            for (Packet subPacket : packet.getSubPackets()) {
                product = product.multiply(getValueOfPacket(subPacket));
            }
            packet.setValue(product);
        } else if (2 == packet.getType()) {
            BigInteger min = null;
            for (Packet subPacket : packet.getSubPackets()) {
                BigInteger spValue = getValueOfPacket(subPacket);
                if (null == min || min.compareTo(spValue) == 1) {
                    min = spValue;
                }
            }
            packet.setValue(min);
        } else if (3 == packet.getType()) {
            BigInteger max = null;
            for (Packet subPacket : packet.getSubPackets()) {
                BigInteger spValue = getValueOfPacket(subPacket);
                if (null == max || max.compareTo(spValue) == -1) {
                    max = spValue;
                }
            }
            packet.setValue(max);
        } else if (5 == packet.getType()) {
            BigInteger valueSp1 = getValueOfPacket(packet.getSubPackets().get(0));
            BigInteger valueSp2 = getValueOfPacket(packet.getSubPackets().get(1));
            packet.setValue(valueSp1.compareTo(valueSp2) == 1 ? BigInteger.ONE : BigInteger.ZERO);
        } else if (6 == packet.getType()) {
            BigInteger valueSp1 = getValueOfPacket(packet.getSubPackets().get(0));
            BigInteger valueSp2 = getValueOfPacket(packet.getSubPackets().get(1));
            packet.setValue(valueSp1.compareTo(valueSp2) == -1 ? BigInteger.ONE : BigInteger.ZERO);
        } else if (7 == packet.getType()) {
            BigInteger valueSp1 = getValueOfPacket(packet.getSubPackets().get(0));
            BigInteger valueSp2 = getValueOfPacket(packet.getSubPackets().get(1));
            packet.setValue(valueSp1.equals(valueSp2) ? BigInteger.ONE : BigInteger.ZERO);
        }

        return packet.getValue();
    }
}
