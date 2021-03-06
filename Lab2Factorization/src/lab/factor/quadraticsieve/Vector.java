package lab.factor.quadraticsieve;

import java.math.BigInteger;
import java.util.BitSet;

/**
 * @author Artur Vasilov
 */
public class Vector {

    public final BitSet vector;
    public final long position;
    public int bigPrimeIndex = -1;

    public BigInteger x;
    public BigInteger y;

    public Vector(BitSet vector, long position) {
        this.vector = vector;
        this.position = position;
    }

    @Override
    public String toString() {
        return vector.toString();
    }
}
