package com.team_kuestenflunder.exam_desktop.controller;


import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class QuestionFormController {

    @FXML
    Label label;

    @FXML
    Button button;


    @FXML
    void onChangeValueClick(){
        label.setText("Hallo Welt.");
    }


}