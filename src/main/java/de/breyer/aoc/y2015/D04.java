package de.breyer.aoc.y2015;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import de.breyer.aoc.app.AbstractAocPuzzle;
import de.breyer.aoc.app.AocPuzzle;

@AocPuzzle("2015_04")
public class D04 extends AbstractAocPuzzle {

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    @Override
    protected void partOne() {
        findLowestNumberForHashPrefix("00000");
    }

    private void findLowestNumberForHashPrefix(String hashPrefix) {
        for (int i = 0; true; i++) {
            String password = lines.get(0) + i;
            String hash = generateHash(password);

            if (hash.startsWith(hashPrefix)) {
                System.out.println("lowest number to produces hash with " + hashPrefix + " prefix: " + i);
                break;
            }
        }
    }

    private String generateHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return bytesToHex(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return password;
    }

    @Override
    protected void partTwo() {
        findLowestNumberForHashPrefix("000000");
    }

}
