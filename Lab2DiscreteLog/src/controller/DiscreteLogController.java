package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class DiscreteLogController implements Initializable {

    public TextField textFieldA;
    public TextField textFieldB;
    public TextField textFieldN;
    public Button calculateButton;
    public TextArea resultTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calculateButton.setOnAction(event -> {
            long a;
            try {
                a = Long.parseLong(textFieldA.getText());
            } catch (NumberFormatException e) {
                textFieldA.setText("");
                return;
            }

            long b;
            try {
                b = Long.parseLong(textFieldB.getText());
            } catch (NumberFormatException e) {
                textFieldB.setText("");
                return;
            }

            long n;
            try {
                n = Long.parseLong(textFieldN.getText());
            } catch (NumberFormatException e) {
                textFieldN.setText("");
                return;
            }

            StringBuilder builder = new StringBuilder();
            builder.append(a).append("^x (mod ").append(n).append(") = ").append(b).append("\n");
            builder.append("Calculating discrete log...\n");
            resultTextArea.setText(builder.toString());

            long startTime = System.currentTimeMillis();
            long x = calculateDiscreteLog(a, b, n);
            long time = System.currentTimeMillis() - startTime;
            builder.append("x = ").append(x).append("\n");
            if (BigInteger.valueOf(a).modPow(BigInteger.valueOf(x), BigInteger.valueOf(n)).longValue() == b) {
                builder.append("Correct answer!\n");
            } else {
                builder.append("Incorrect answer :(\n");
            }
            builder.append("Time spent: ").append(time).append("ms");
            resultTextArea.setText(builder.toString());
        });

        System.out.println("Example for discrete log:");
        for (int i = 1; i < 6; i++) {
            printRandomSample(i);
        }
    }

    private long calculateDiscreteLog(long a, long b, long n) {
        //a^x (mod n) = b  ==> find x
        long m = (long) (Math.sqrt(n) + 1);
        Map<Long, Long> largeStepValues = new HashMap<>();
        Map<Long, Long> smallStepValues = new HashMap<>();

        int iterationValues = 5;
        long u = -1;
        long v = -1;
        while (u < m || v < m) {
            for (int i = 0; i < iterationValues; i++) {
                u++;
                long largeStep = calculateLargeStepValue(a, u, m, n);
                largeStepValues.put(largeStep, u);

                if (smallStepValues.containsKey(largeStep)) {
                    long resultV = smallStepValues.get(largeStep);
                    return (u * m - resultV) % n;
                }
            }

            for (int i = 0; i < iterationValues; i++) {
                v++;
                long smallStep = calculateSmallStepValue(a, b, v, n);
                smallStepValues.put(smallStep, v);

                if (largeStepValues.containsKey(smallStep)) {
                    long resultU = largeStepValues.get(smallStep);
                    return (resultU * m - v) % n;
                }
            }
        }

        return 0;
    }

    private long calculateLargeStepValue(long a, long u, long m, long n) {
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger bigU = BigInteger.valueOf(u);
        BigInteger bigM = BigInteger.valueOf(m);
        BigInteger bigN = BigInteger.valueOf(n);
        return bigA.modPow(bigU.multiply(bigM), bigN).longValue();
    }

    private long calculateSmallStepValue(long a, long b, long v, long n) {
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger bigB = BigInteger.valueOf(b);
        BigInteger bigV = BigInteger.valueOf(v);
        BigInteger bigN = BigInteger.valueOf(n);
        return bigB.multiply(bigA.modPow(bigV, bigN)).mod(bigN).longValue();
    }

    private void printRandomSample(int index) {
        Random random = new SecureRandom();
        int maxValue = (int) Math.pow(10, index);
        int n = BigInteger.probablePrime(index * 5, random).intValue();
        int a = (random.nextInt(maxValue) + 1) % n;
        int x = (random.nextInt(maxValue) + 1) % n;
        long b = BigInteger.valueOf(a).modPow(BigInteger.valueOf(x), BigInteger.valueOf(n)).longValue();
        System.out.println(a + "^" + x + " (mod " + n + ") = " + b);
    }
}
