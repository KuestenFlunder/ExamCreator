package com.team_kuestenflunder.exam_desktop.controller;


import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionFormController implements Initializable {
    @FXML
    private final QuestionRepositoryImpl questionRepository = new QuestionRepositoryImpl();
    @FXML
    private final QuestionFormServiceImpl questionFormService = new QuestionFormServiceImpl(questionRepository);


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
        questionRepository.initList();
    }
}