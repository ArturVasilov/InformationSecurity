package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("discrete_log.fxml"));
        primaryStage.setTitle("Discrete log problem");
        primaryStage.setScene(new Scene(root, 650, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
