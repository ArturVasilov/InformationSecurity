package lab.factor.quadraticsieve;

import java.math.BigInteger;
import java.util.*;

/**
 * @author Artur Vasilov
 */
public class BitMatrix {

    private final BigInteger number;
    private final BigInteger squareRoot;
    public int bigPrimesIndex;

    private BitSet rows[];
    private BitSet solutionRows[];
    private List<Vector> vectors;

    public BitMatrix(BigInteger number, BigInteger root, int biggestPrimeIndex) {
        this.number = number;
        this.squareRoot = root;
        this.bigPrimesIndex = biggestPrimeIndex;
    }

    public List<List<Vector>> solve(List<Vector> bSmoothVectors, BigPrimesList bigPrimesList) {
        List<Vector> vectors = shrink(bSmoothVectors, bigPrimesList);
        this.vectors = vectors;

        Map<Integer, Object> map = new HashMap<>();
        rows = new BitSet[vectors.size()];
        solutionRows = new BitSet[vectors.size()];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = vectors.get(i).vector;
            solutionRows[i] = new BitSet();
            solutionRows[i].set(i);
        }

        for (int column = 0; column < rows.length; column++) {
            int selectedRow = -1;
            for (int row = 0; row < rows.length; row++) {
                if (!rows[row].get(column)) {
                    continue;
                }
                if (selectedRow == -1 && !map.containsKey(row)) {
                    selectedRow = row;
                    map.put(row, row);
                    continue;
                }
                if (selectedRow != -1) {
                    xor(row, selectedRow);
                }
            }
            for (int row = 0; row < selectedRow; row++) {
                if (!rows[row].get(column)) {
                    continue;
                }
                xor(row, selectedRow);
            }
        }

        List<List<Vector>> solutions = new ArrayList<>();
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].isEmpty()) {
                solutions.add(createSolution(i));
            }
        }

        return solutions;
    }

    private List<Vector> createSolution(int row) {
        List<Vector> solution = new ArrayList<>();
        for (int column = 0; column < rows.length; column++) {
            if (solutionRows[row].get(column)) {
                solution.add(vectors.get(column));
            }
        }
        return solution;
    }

    private void xor(int rowA, int rowB) {
        rows[rowA].xor(rows[rowB]);
        solutionRows[rowA].xor(solutionRows[rowB]);
    }

    private List<Vector> shrink(List<Vector> bSmoothVectors, BigPrimesList bigPrimesList) {
        bSmoothVectors = new ArrayList<>(bSmoothVectors);
        List<List<Vector>> bigPrimes = bigPrimesList.getBigPrimes();

        for (int i = bigPrimes.size() - 1; i >= 0; i--) {
            List<Vector> vectors = bigPrimes.get(i);
            if (vectors.size() == 2) {
                bigPrimes.remove(i);
                Vector vector = vectors.get(0);
                merge(vector, vectors.get(1));
                bSmoothVectors.add(vector);
            }
        }

        for (List<Vector> bigPrimeList : bigPrimes) {
            boolean updateIndex = false;
            boolean firstVector = true;
            int bigPrimeIndex = -1;

            for (Vector vector : bigPrimeList) {
                if (firstVector && vector.bigPrimeIndex == -1) {
                    firstVector = false;
                    updateIndex = true;
                }
                if (vector.bigPrimeIndex == -1) {
                    if (bigPrimeIndex == -1) {
                        bigPrimeIndex = this.bigPrimesIndex;
                    }
                    vector.bigPrimeIndex = bigPrimeIndex;
                    vector.vector.set(bigPrimeIndex);
                }
                else {
                    bigPrimeIndex = vector.bigPrimeIndex;
                }
                bSmoothVectors.add(vector);
            }
            if (updateIndex) {
                this.bigPrimesIndex++;
            }
        }

        return bSmoothVectors;
    }

    private void merge(Vector result, Vector vectorToMerge) {
        result.vector.xor(vectorToMerge.vector);
        BigInteger x1 = calculateX(result.position);
        BigInteger x2 = calculateX(vectorToMerge.position);
        result.x = x1.multiply(x2);
        result.y = calculateY(x1).multiply(calculateY(x2));
    }

    private BigInteger calculateY(BigInteger x) {
        return x.pow(2).subtract(number);
    }

    private BigInteger calculateX(long position) {
        return squareRoot.add(BigInteger.valueOf(position));
    }
}
