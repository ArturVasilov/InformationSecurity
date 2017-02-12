package lab.factor;

import java.math.BigInteger;

/**
 * @author Artur Vasilov
 */
public class FermatFactorization extends BaseFactorization {

    private FermatFactorization() {
    }

    public static FactorizationFunction newInstance() {
        return new FermatFactorization();
    }

    @Override
    protected BigInteger findDivider(BigInteger number) {
        BigInteger[] results = squareRootAndRemainder(number);
        final BigInteger m = results[0];
        for (int x = 0; x < 1_000_000_000; x++) {
            incrementOperations();
            BigInteger q = m.add(BigInteger.valueOf(x)).pow(2).subtract(number);
            results = squareRootAndRemainder(q);
            if (results[1].equals(BigInteger.ZERO)) {
                BigInteger b = results[0];
                BigInteger a = m.add(BigInteger.valueOf(x));
                return a.add(b);
            }
        }
        return null;
    }

    private BigInteger[] squareRootAndRemainder(BigInteger number) {
        BigInteger[] results = new BigInteger[2];

        BigInteger a = BigInteger.ONE;
        BigInteger b = number.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(number) > 0) {
                b = mid.subtract(BigInteger.ONE);
            }
            else {
                a = mid.add(BigInteger.ONE);
            }
        }

        BigInteger root = a.subtract(BigInteger.ONE);
        BigInteger remainder = number.subtract(root.multiply(root));
        results[0] = root;
        results[1] = remainder;
        return results;
    }
}
