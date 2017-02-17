package lab.factor.quadraticsieve;

import java.util.*;

/**
 * @author Artur Vasilov
 */
public class BigPrimesList {

    private final List<List<Vector>> primes = new LinkedList<>();
    private final Map<Long, List<Vector>> primesHash = new HashMap<>();

    private int primesFound;

    public void add(long prime, Vector bigPrime) {
        List<Vector> vectors = primesHash.get(prime);
        if (vectors == null) {
            vectors = new LinkedList<>();
            primesHash.put(prime, vectors);
            primes.add(vectors);
        }
        vectors.add(bigPrime);
        if (vectors.size() > 1) {
            primesFound++;
        }
    }

    public int getPrimesFound() {
        return primesFound;
    }

    public List<List<Vector>> getBigPrimes() {
        List<List<Vector>> primeList = new ArrayList<>();
        for (List<Vector> prime : primes) {
            if (prime.size() > 1) {
                primeList.add(prime);
            }
        }

        return primeList;
    }
}
