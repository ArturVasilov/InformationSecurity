package lab.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lab.factor.FactorizationFunction;
import lab.factor.quadraticsieve.QuadraticSieveFactorization;

import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.*;

public class FactorizationController implements Initializable {

    public TextField textFieldN;
    public Button calculateButton;
    public TextArea resultTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Random random = new SecureRandom();
        BigInteger first = BigInteger.probablePrime(60, random);
        BigInteger second = BigInteger.probablePrime(70, random);
        BigInteger number = first.multiply(second);

        textFieldN.setText(number.toString());
        calculateButton.setOnAction(event -> {
            BigInteger n;
            try {
                n = new BigInteger(textFieldN.getText());
            } catch (NumberFormatException e) {
                textFieldN.setText("");
                return;
            }

            FactorizationFunction factorizationFunction = QuadraticSieveFactorization.newInstance();
            List<BigInteger> primes = factorizationFunction.factor(n);
            StringBuilder text = new StringBuilder("Result:\n");
            text.append(n).append(" = ");
            for (int i = 0; i < primes.size() - 1; i++) {
                text.append(primes.get(i)).append(" * ");
            }
            text.append(primes.get(primes.size() - 1)).append("\n");

            boolean correct = primes.get(0).equals(first.min(second)) &&
                    primes.get(1).equals(first.max(second));
            if (correct) {
                text.append("Result is correct!");
            }
            else {
                text.append("Result is incorrect :(\nCorrect result is:\n");
                text.append(n).append(" = ").append(first).append(" * ").append(second);
            }

            text.append("\n\n");
            text.append("operations = ").append(factorizationFunction.operationsCount()).append("\n");
            text.append("time = ").append(factorizationFunction.workTimeMs() / 1000).append("s");

            resultTextArea.setText(text.toString());
        });
    }
}
