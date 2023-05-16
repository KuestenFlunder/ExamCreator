package com.team_kuestenflunder.exam_desktop.controller;


import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionFormController implements Initializable {
    private final QuestionFormServiceImpl questionFormService;

    @Inject
    public QuestionFormController(QuestionFormServiceImpl questionFormService) {
        this.questionFormService = questionFormService;
    }

    @FXML
    Label label;

    @FXML
    Button button;

    @FXML
    void onChangeValueClick(){
        label.setText(questionFormService.getQuestions().get(1).getId());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //!Remove if JSON Repo is implemented
        questionFormService.initList();
    }
}