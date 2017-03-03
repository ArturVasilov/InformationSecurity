package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.md4.CollisionData;
import main.md4.MD4;
import main.md4.MD4Attack;

import java.net.URL;
import java.util.ResourceBundle;

public class MD4Controller implements Initializable {

    public TextField textFieldMessage;
    public TextField textFieldHash;

    public Button calculateHashButton;
    public Button preimageAttackButton;
    public Button collisionsAttackButton;

    public TextArea currentWorkerTextArea;

    private final StringBuilder currentText = new StringBuilder();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calculateHashButton.setOnAction(event -> {
            String message = textFieldMessage.getText();
            String hash = MD4.hash(message);
            textFieldHash.setText(hash);

            currentText.append("Calculating hash for message ").append(message).append("\n");
            currentText.append("Hash is ").append(hash).append("\n\n");
            currentWorkerTextArea.setText(currentText.toString());
        });

        preimageAttackButton.setOnAction(event -> {
            String hash = textFieldHash.getText();
            currentText.append("Trying first preimage attack for hash ").append(hash).append("...").append("\n");
            currentWorkerTextArea.setText(currentText.toString());

            String preimage = MD4Attack.tryPreimageAttack(hash);
            if (preimage == null || preimage.isEmpty()) {
                currentText.append("Preimage attack failed :(");
            }
            else {
                currentText.append("Preimage attack succeed!\n")
                        .append("Message ").append(preimage).append(" has the same hash!");
            }
            currentText.append("\n\n");
            currentWorkerTextArea.setText(currentText.toString());
        });

        collisionsAttackButton.setOnAction(event -> {
            currentText.append("Trying to find collisions for MD4").append("...").append("\n");
            currentWorkerTextArea.setText(currentText.toString());

            CollisionData collisionData = MD4Attack.findCollisions();
            currentText.append(collisionData).append("\n");
            currentWorkerTextArea.setText(currentText.toString());
        });
    }
}
