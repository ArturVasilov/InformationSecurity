package lab.utils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Artur Vasilov
 */
public final class TextCoder {

    private static final BigInteger HUNDRED = BigInteger.valueOf(100);

    private static final Map<Integer, Character> LETTERS = new HashMap<>();

    static {
        int index = 16;
        char symbol = 'А';

        while (symbol <= 'Я') {
            LETTERS.put(index, symbol);
            symbol++;
            index++;
        }

        symbol = 'а';
        index = 48;

        while (symbol <= 'я') {
            LETTERS.put(index, symbol);
            symbol++;
            index++;
        }
    }

    private TextCoder() {
    }

    public static String decode(BigInteger number) {
        StringBuilder result = new StringBuilder();
        while (!number.equals(BigInteger.ZERO)) {
            int letter = number.mod(HUNDRED).intValue();
            number = number.divide(HUNDRED);
            result.append(LETTERS.get(letter));
        }
        return result.reverse().toString();
    }

}
