package com.example.slafamilyfeud2026;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FamilyFeudApp extends Application {
    static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FamilyFeudApp.class.getResource("Round1View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Family Feud");
        stage.setScene(scene);
        Round1Controller controller = fxmlLoader.getController();
        controller.setupHandlers();
        stage.setFullScreen(true);
        stage.show();
        mainStage = stage;
    }

    public static void Round1() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FamilyFeudApp.class.getResource("Round1View.fxml"));
        mainStage.getScene().setRoot(fxmlLoader.load());
        mainStage.setFullScreen(true);
        Round1Controller controller = fxmlLoader.getController();
        controller.setupHandlers();
    }

    public static void Round2() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FamilyFeudApp.class.getResource("Round2View.fxml"));
        Round2Controller controller = fxmlLoader.getController();
        mainStage.getScene().setRoot(fxmlLoader.load());
        mainStage.setFullScreen(true);
        controller.setupHandlers();
    }
}

