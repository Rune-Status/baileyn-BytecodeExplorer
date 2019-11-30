package com.njbailey.explorer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        root.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());

        Scene scene = new Scene(root);

        stage.setTitle("Bytecode Explorer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            Application.launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
