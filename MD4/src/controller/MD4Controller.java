package controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.md4.MD4;
import main.md4.MD4Attack;

import java.net.URL;
import java.util.ResourceBundle;

public class MD4Controller implements Initializable {

    public TextField textFieldMessage;
    public TextField textFieldHash;
    public TextField textFieldCollision;

    public Button calculateHashButton;
    public Button calculateCollisionButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calculateHashButton.setOnAction(event -> {
            String message = textFieldMessage.getText();
            String hash = MD4.hash(message);
            textFieldHash.setText(hash);
        });

        calculateCollisionButton.setOnAction(event -> {
            String hash = textFieldHash.getText();
            String collision = MD4Attack.findCollisionMessage(hash);
            textFieldCollision.setText(collision);
        });
    }
}
