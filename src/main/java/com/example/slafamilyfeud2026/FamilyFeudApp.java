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
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(FamilyFeudApp.class.getResource("Round1View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        mainStage.setTitle("Family Feud");
        mainStage.setScene(scene);
        Round1Controller controller = fxmlLoader.getController();
        controller.setupHandlers();
        mainStage.setFullScreen(true);
        mainStage.show();
    }

    public static void Round1() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FamilyFeudApp.class.getResource("Round1View.fxml"));
        mainStage.getScene().setRoot(fxmlLoader.load());
        mainStage.setFullScreen(true);
        Round1Controller controller1 = fxmlLoader.getController();
        controller1.setupHandlers();
        mainStage.show();
    }

    public static void Round2() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FamilyFeudApp.class.getResource("Round2View.fxml"));
        mainStage.getScene().setRoot(fxmlLoader.load());
        mainStage.setFullScreen(true);
        Round2Controller controller2 = fxmlLoader.getController();
        controller2.setupHandlers();
        mainStage.show();
    }
}

