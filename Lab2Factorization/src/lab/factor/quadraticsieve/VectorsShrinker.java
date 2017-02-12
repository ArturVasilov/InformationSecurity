package lab.factor.quadraticsieve;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public class VectorsShrinker {

    private BigInteger N;
    private BigInteger root;
    public int bigPrimesIndex;

    public void init(BigInteger root, int biggestPrimeIndex, BigInteger N) {
        this.root = root;
        this.bigPrimesIndex = biggestPrimeIndex;
        this.N = N;
    }

    @SuppressWarnings("unchecked")
    public List<VectorData> shrink(List<VectorData> bSmoothVectors, BigPrimesList bigPrimesList) {
        bSmoothVectors = new ArrayList<>(bSmoothVectors);
        List<List<VectorData>> bigPrimes = bigPrimesList.getBigPrimes();

        for (int i = bigPrimes.size() - 1; i >= 0; i--) {
            List<VectorData> vectorDatas = bigPrimes.get(i);
            if (vectorDatas.size() == 2) {
                bigPrimes.remove(i);
                VectorData vectorData = vectorDatas.get(0);
                merge(vectorData, vectorDatas.get(1));
                bSmoothVectors.add(vectorData);
            }
        }

        for (List<VectorData> bigPrimeList : bigPrimes) {
            boolean updateIndex = false;
            boolean firstVector = true;
            int bigPrimeIndex = -1;

            for (VectorData vectorData : bigPrimeList) {
                if (firstVector && vectorData.bigPrimeIndex == -1) {
                    firstVector = false;
                    updateIndex = true;
                }
                if (vectorData.bigPrimeIndex == -1) {
                    if (bigPrimeIndex == -1) {
                        bigPrimeIndex = this.bigPrimesIndex;
                    }
                    vectorData.bigPrimeIndex = bigPrimeIndex;
                    vectorData.vector.set(bigPrimeIndex);
                }
                else {
                    bigPrimeIndex = vectorData.bigPrimeIndex;
                }
                bSmoothVectors.add(vectorData);
            }
            if (updateIndex) {
                this.bigPrimesIndex++;
            }
        }

        return bSmoothVectors;
    }

    private void merge(VectorData result, VectorData vectorToMerge) {
        result.vector.xor(vectorToMerge.vector);
        BigInteger x1 = calculateX(result.position);
        BigInteger x2 = calculateX(vectorToMerge.position);
        result.x = x1.multiply(x2);
        result.y = calculateY(x1).multiply(calculateY(x2));
    }

    private BigInteger calculateY(BigInteger x) {
        return x.pow(2).subtract(N);
    }

    private BigInteger calculateX(long position) {
        return root.add(BigInteger.valueOf(position));
    }


}
