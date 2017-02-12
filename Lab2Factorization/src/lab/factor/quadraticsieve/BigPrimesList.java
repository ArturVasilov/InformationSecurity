package lab.factor.quadraticsieve;

import java.util.*;

/**
 * @author Artur Vasilov
 */
public class BigPrimesList {

    private final List<List<VectorData>> primes = new LinkedList<>();
    private final Map<Long, List<VectorData>> primesHash = new HashMap<>();

    private int primesFound;

    public void add(long prime, VectorData bigPrime) {
        List<VectorData> vectorDatas = primesHash.get(prime);
        if (vectorDatas == null) {
            vectorDatas = new LinkedList<>();
            primesHash.put(prime, vectorDatas);
            primes.add(vectorDatas);
        }
        vectorDatas.add(bigPrime);
        if (vectorDatas.size() > 1) {
            primesFound++;
        }
    }

    public int getPrimesFound() {
        return primesFound;
    }

    public List<List<VectorData>> getBigPrimes() {
        List<List<VectorData>> primeList = new ArrayList<>();
        for (List<VectorData> prime : primes) {
            if (prime.size() > 1) {
                primeList.add(prime);
            }
        }

        return primeList;
    }
}
