package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/md4_layout.fxml"));
        primaryStage.setTitle("MD4 Application");
        primaryStage.setScene(new Scene(root, 650, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
