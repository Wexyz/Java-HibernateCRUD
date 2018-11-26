package com.rsvp.Gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Scene1.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Main GUI");
        primaryStage.setScene(new Scene(root));

        primaryStage.setOnCloseRequest(e -> {Platform.exit();System.exit(0);});

        primaryStage.show();
        System.out.println(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
