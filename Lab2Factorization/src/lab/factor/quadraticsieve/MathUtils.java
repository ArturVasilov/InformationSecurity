package lab.factor.quadraticsieve;

import java.math.BigInteger;

/**
 * @author Artur Vasilov
 */
public final class MathUtils {

    private MathUtils() {
    }

    public static BigInteger bigIntSqRootFloor(BigInteger value) {
        if (value == null || value.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid argument, value=" + value);
        }
        if (value.equals(BigInteger.ZERO) || value.equals(BigInteger.ONE)) {
            return value;
        }

        if (value.bitLength() < 64) { // Can be cast to long
            double sqrt = Math.sqrt(value.longValue());
            return BigInteger.valueOf((long) sqrt);
        }

        BigInteger two = BigInteger.valueOf(2);
        BigInteger y = two.pow(value.bitLength() / 2);
        BigInteger result = value.divide(y);
        while (!y.subtract(result).abs().equals(BigInteger.ONE) && !y.subtract(result).equals(BigInteger.ZERO)) {
            y = result.add(y).divide(two);
            result = value.divide(y);
        }
        return y;
    }

    public static BigInteger bigIntSqRootCeil(BigInteger x) {
        BigInteger y = bigIntSqRootFloor(x);
        if (x.compareTo(y.multiply(y)) == 0) {
            return y;
        }
        return y.add(BigInteger.ONE);
    }

    public static boolean isRootInQuadraticResidues(BigInteger n, BigInteger p) {
        BigInteger two = BigInteger.valueOf(2);
        BigInteger x = n.mod(p);
        if (p.equals(two)) {
            return x.mod(two).equals(BigInteger.ONE);
        }
        BigInteger exponent = p.subtract(BigInteger.ONE).divide(two);
        return x.modPow(exponent, p).equals(BigInteger.ONE);
    }

}
