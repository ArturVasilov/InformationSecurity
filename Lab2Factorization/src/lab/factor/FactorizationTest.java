package lab.factor;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Artur Vasilov
 */
public class FactorizationTest {

    private final List<FactorizationFunction> functions = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        functions.add(RoPollardFactorization.newInstance());
        functions.add(FermatFactorization.newInstance());
    }

    @Test
    public void testFactor77() throws Exception {
        testFactorization(77, 7, 11);
    }

    @Test
    public void testFactor9991() throws Exception {
        testFactorization(9991, 97, 103);
    }

    @Test
    public void testFactor10001() throws Exception {
        testFactorization(10_001, 73, 137);
    }

    @Test
    public void testLargeFactor() throws Exception {
        testFactorization(27678208807L, 157523, 175709);
    }

    private void testFactorization(long number, long... primes) {
        for (FactorizationFunction function : functions) {
            List<BigInteger> result = function.factor(BigInteger.valueOf(number));
            for (int i = 0; i < result.size(); i++) {
                assertEquals(primes[i], result.get(i).longValue());
            }
        }
    }

}