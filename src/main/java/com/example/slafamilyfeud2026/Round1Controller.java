package com.example.slafamilyfeud2026;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Round1Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
