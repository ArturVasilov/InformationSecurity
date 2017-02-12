package lab.factor.quadraticsieve;

import java.util.*;

/**
 * @author Artur Vasilov
 */
public class BitMatrix {

    private BitSet rows[];
    private BitSet solutionRows[];
    private List<VectorData> vectorDatas;

    public List<List<VectorData>> solve(List<VectorData> vectorDatas) {
        this.vectorDatas = vectorDatas;

        Map<Integer, Object> map = new HashMap<>();
        rows = new BitSet[vectorDatas.size()];
        solutionRows = new BitSet[vectorDatas.size()];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = vectorDatas.get(i).vector;
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

        List<List<VectorData>> solutions = new ArrayList<>();
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].isEmpty()) {
                solutions.add(createSolution(i));
            }
        }

        return solutions;
    }

    private List<VectorData> createSolution(int row) {
        List<VectorData> solution = new ArrayList<>();
        for (int column = 0; column < rows.length; column++) {
            if (solutionRows[row].get(column)) {
                solution.add(vectorDatas.get(column));
            }
        }
        return solution;
    }

    private void xor(int rowA, int rowB) {
        rows[rowA].xor(rows[rowB]);
        solutionRows[rowA].xor(solutionRows[rowB]);
    }
}
