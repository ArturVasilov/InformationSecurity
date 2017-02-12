package lab.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class GcdUtilsTest {

    @Test
    public void testGcd1() throws Exception {
        testGcd(15, 45, 75);
    }

    @Test
    public void testGcd2() throws Exception {
        testGcd(1, 26, 19);
    }

    @Test
    public void testGcd3() throws Exception {
        testGcd(109, 981, 109);
    }

    @Test
    public void testGcd4() throws Exception {
        testGcd(12, 60, 84);
    }

    private void testGcd(int expected, int first, int second) {
        BigInteger gcd = GcdUtils.gcd(BigInteger.valueOf(first), BigInteger.valueOf(second));
        assertEquals(BigInteger.valueOf(expected), gcd);
    }
}