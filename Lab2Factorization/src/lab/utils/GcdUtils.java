package lab.utils;

import java.math.BigInteger;

/**
 * @author Artur Vasilov
 */
public final class GcdUtils {

    private GcdUtils() {
    }

    public static BigInteger gcd(BigInteger first, BigInteger second) {
        /*BigInteger a = first.compareTo(second) > 0 ? first : second;
        BigInteger b = first.compareTo(second) > 0 ? second : first;
        if (b.equals(BigInteger.ZERO)) {
            return a;
        }
        BigInteger mod = a.mod(b);
        while (!mod.equals(BigInteger.ZERO)) {
            a = b;
            b = mod;
            mod = a.mod(b);
        }
        return b;*/
        return first.gcd(second);
    }

    public static BigInteger inverse(BigInteger modulus, BigInteger element) {
        BigInteger x = BigInteger.ZERO;
        BigInteger lastx = BigInteger.ONE;
        BigInteger y = BigInteger.ONE;
        BigInteger lasty = BigInteger.ZERO;
        BigInteger a = modulus;
        BigInteger b = element;

        while (!b.equals(BigInteger.ZERO)) {
            BigInteger[] quotientAndRemainder = a.divideAndRemainder(b);
            BigInteger quotient = quotientAndRemainder[0];

            a = b;
            b = quotientAndRemainder[1];

            BigInteger temp = x;
            x = lastx.subtract(quotient.multiply(x));
            lastx = temp;

            temp = y;
            y = lasty.subtract(quotient.multiply(y));
            lasty = temp;
        }

        if (lasty.compareTo(BigInteger.ZERO) < 0) {
            lasty = lasty.add(modulus);
        }
        return lasty;
    }

}
