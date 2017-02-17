package lab.factor.quadraticsieve;

import lab.factor.BaseFactorization;
import lab.factor.FactorizationFunction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public class QuadraticSieveFactorization extends BaseFactorization {

    private static final int B_SMOOTH = 5000;
    private static final double MINIMUM_LOG = 0.0000001;

    private double minimumBigPrimeLog;
    private int sieveVectorBound;

    private BigInteger factorBase[];
    private final List<Vector> bSmoothVectors = new ArrayList<>();

    private BigInteger N;
    private final Wheel wheels[] = new Wheel[B_SMOOTH];

    private BigInteger root;

    private double baseLog;
    private final BigPrimesList bigPrimesList = new BigPrimesList();

    public static FactorizationFunction newInstance() {
        return new QuadraticSieveFactorization();
    }

    @Override
    protected BigInteger findDivider(BigInteger number) {
        N = number;
        root = MathUtils.bigIntSqRootCeil(number);

        fillFactorBase();
        BigInteger highestPrime = factorBase[factorBase.length - 1];
        sieveVectorBound = highestPrime.intValue();
        minimumBigPrimeLog = Math.log(highestPrime.pow(2).doubleValue());

        initSieveWheels();

        long position = 0;
        long step = sieveVectorBound;

        while (true) {
            baseLog = calculateBaseLog(position);
            position += step;
            boolean sieve = sieve(position);

            BigInteger solution = tryToSolve();
            if (sieve && solution != null) {
                return solution;
            }
        }
    }

    private double calculateBaseLog(long position) {
        double target = root.add(BigInteger.valueOf(position)).pow(2).subtract(N).doubleValue();
        return Math.log(target);
    }

    private void initSieveWheels() {
        for (int i = 0; i < wheels.length; i++) {
            wheels[i] = new Wheel();
            wheels[i].init(factorBase[i], N, root);
        }
    }

    private boolean sieve(long destination) {
        boolean vectorsFound = false;
        Double[] logs = new Double[sieveVectorBound];
        Double[] trueLogs = new Double[sieveVectorBound];
        Vector[] vectors = new Vector[sieveVectorBound];

        for (int i = 0; i < factorBase.length; i++) {
            Wheel wheel = wheels[i];
            wheel.savePosition();
            while (wheel.testMove(destination)) {
                double log = wheel.nextLog();
                long position = wheel.move();
                int index = (int) (position % sieveVectorBound);
                if (logs[index] == null) {
                    logs[index] = 0d;
                }
                logs[index] += log;
                incrementOperations();
            }
            wheel.restorePosition();
        }

        for (int i = factorBase.length - 1; i >= 0; i--) {
            Wheel wheel = wheels[i];
            while (wheel.testMove(destination)) {
                wheel.nextLog();
                long position = wheel.move();
                int index = (int) (position % sieveVectorBound);

                if (trueLogs[index] == null) {
                    if (baseLog - logs[index] > minimumBigPrimeLog) {
                        continue;
                    }
                    trueLogs[index] = calculateBaseLog(position);
                }

                double reminderLog = trueLogs[index] - logs[index];
                if (reminderLog > minimumBigPrimeLog) {
                    continue;
                }

                boolean bigPrime = reminderLog > MINIMUM_LOG;

                if (vectors[index] == null) {
                    Vector vector = new Vector(new BitSet(i), index + destination - sieveVectorBound);
                    vectors[index] = vector;
                    if (bigPrime) {
                        long prime = Math.round(Math.pow(Math.E, reminderLog));
                        bigPrimesList.add(prime, vector);
                    } else {
                        bSmoothVectors.add(vector);
                    }
                    vectorsFound = true;
                }
                int powers = wheel.getPowers();
                if (powers % 2 != 0) {
                    vectors[index].vector.set(i);
                }
                incrementOperations();
            }
        }

        return vectorsFound;
    }

    private BigInteger tryToSolve() {
        if (bSmoothVectors.size() + bigPrimesList.getPrimesFound() < B_SMOOTH) {
            return null;
        }

        BitMatrix bitMatrix = new BitMatrix(N, root, factorBase.length);
        List<List<Vector>> solutions = bitMatrix.solve(bSmoothVectors, bigPrimesList);

        for (List<Vector> solution : solutions) {
            BigInteger y = BigInteger.ONE;
            BigInteger x = BigInteger.ONE;

            for (Vector vector : solution) {
                BigInteger savedX, savedY;
                if (vector.x != null) {
                    savedX = vector.x;
                    savedY = vector.y;
                } else {
                    savedX = root.add(BigInteger.valueOf(vector.position));
                    savedY = savedX.pow(2).subtract(N);
                }
                x = x.multiply(savedX).mod(N);
                y = y.multiply(savedY);
            }

            incrementOperations();
            y = MathUtils.bigIntSqRootFloor(y);
            BigInteger gcd = N.gcd(x.add(y));
            if (!gcd.equals(BigInteger.ONE) && !gcd.equals(N)) {
                return gcd;
            }
        }
        return null;
    }


    private void fillFactorBase() {
        factorBase = new BigInteger[B_SMOOTH];
        BigInteger prime = BigInteger.ONE;

        int i = 0;
        while (i < B_SMOOTH) {
            prime = prime.nextProbablePrime();
            if (MathUtils.isRootInQuadraticResidues(N, prime)) {
                factorBase[i] = prime;
                i++;
            }
        }
    }
}
