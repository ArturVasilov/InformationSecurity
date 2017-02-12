package lab.factor;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public interface FactorizationFunction {

    List<BigInteger> factor(BigInteger number);

    int operationsCount();

    long workTimeMs();

}
