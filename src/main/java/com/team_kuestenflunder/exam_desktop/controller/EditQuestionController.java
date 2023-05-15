package com.team_kuestenflunder.exam_desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EditQuestionController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}