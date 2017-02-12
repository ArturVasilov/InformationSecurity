package lab.factor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public abstract class BaseFactorization implements FactorizationFunction {

    private int operations = 0;

    private long workTime = 0;

    @Override
    public List<BigInteger> factor(BigInteger number) {
        operations = 0;
        workTime = System.nanoTime();

        List<BigInteger> dividers = new ArrayList<>();
        BigInteger divider = findDivider(number);
        number = number.divide(divider);
        dividers.add(divider);
        dividers.add(number);
        Collections.sort(dividers);

        workTime = System.nanoTime() - workTime;
        return dividers;
    }

    protected abstract BigInteger findDivider(BigInteger number);

    @Override
    public int operationsCount() {
        return operations;
    }

    @Override
    public long workTimeMs() {
        return workTime / 1_000_000;
    }

    protected void incrementOperations() {
        operations++;
    }
}
