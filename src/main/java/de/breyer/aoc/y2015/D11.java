package de.breyer.aoc.y2015;

import java.util.List;
import de.breyer.aoc.AbstractAocPuzzle;
import de.breyer.aoc.AocPuzzle;

@AocPuzzle("2015_11")
public class D11 extends AbstractAocPuzzle {

    private static final String OLD_PASSWORD = "cqjxjnds";
    private static final List<Character> FORBIDDEN_CHARS = List.of('i', 'o', 'l');

    private char[] password;

    @Override
    protected void partOne() {
        password = OLD_PASSWORD.toCharArray();
        calcNextPassword();
        System.out.println("new password: " + new String(password));
    }

    private void calcNextPassword() {
        do {
            increase(7);
        } while (!checkFirstRequirement() || !checkSecondRequirement() || !checkThirdRequirement());
    }

    private void increase(int index) {
        char character = password[index];
        if (character == 'z') {
            character = 'a';
            if (index > 0) {
                increase(index - 1);
            }
        } else {
            character += 1;
            if (FORBIDDEN_CHARS.contains(character)) {
                character += 1;
            }
        }
        password[index] = character;
    }

    private boolean checkFirstRequirement() {
        for (int i = 0; i < password.length - 2; i++) {
            if (password[i + 1] - password[i] == 1 && password[i + 2] - password[i] == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSecondRequirement() {
        for (char character : password) {
            if (FORBIDDEN_CHARS.stream().anyMatch(forbidden -> forbidden == character)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkThirdRequirement() {
        int found = 0;
        for (int i = 0; i < password.length - 1; i++) {
            if (password[i] == password[i + 1]) {
                found++;
                i++;
                if (found == 2) {
                    break;
                }
            }
        }
        return found >= 2;
    }

    @Override
    protected void partTwo() {
        calcNextPassword();
        System.out.println("new password: " + new String(password));
    }

}
