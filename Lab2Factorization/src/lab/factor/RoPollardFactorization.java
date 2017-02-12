package lab.factor;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Artur Vasilov
 */
public final class RoPollardFactorization extends BaseFactorization {

    private static final int MAX_STAGE = 536870912;

    private static final BigInteger MAX_K = BigInteger.valueOf(2048);

    private final Random random;

    private RoPollardFactorization() {
        random = new SecureRandom();
    }

    public static FactorizationFunction newInstance() {
        return new RoPollardFactorization();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected BigInteger findDivider(BigInteger number) {
        BigInteger k = BigInteger.ONE;
        BigInteger a = generateRandom(BigInteger.ONE, number.subtract(BigInteger.ONE));
        BigInteger x = a;
        BigInteger y = BigInteger.ONE;

        int i = 0;
        int stage = 2;

        BigInteger gcd = number.gcd(x.subtract(y).abs());
        while (gcd.equals(BigInteger.ONE)) {
            if (stage > MAX_STAGE) {
                k = k.add(BigInteger.ONE);
                x = a;
                y = BigInteger.ONE;
                i = 0;
                stage = 2;
            }

            if (k.equals(MAX_K)) {
                k = BigInteger.ONE;
                a = generateRandom(BigInteger.ONE, number.subtract(BigInteger.ONE));
                x = a;
                y = BigInteger.ONE;
                i = 0;
                stage = 2;
            }

            if (i == stage) {
                y = x;
                stage *= 2;
            }
            x = x.multiply(x).add(k).mod(number);
            i++;

            incrementOperations();
            gcd = number.gcd(x.subtract(y).abs());
        }
        return gcd;
    }

    private BigInteger generateRandom(BigInteger from, BigInteger to) {
        BigInteger number;
        do {
            int bits = random.nextInt(to.bitLength() - from.bitLength() + 1) + from.bitLength();
            number = new BigInteger(bits, random);
        } while (number.compareTo(from) < 0 || number.compareTo(to) >= 0);
        return number;
    }
}
