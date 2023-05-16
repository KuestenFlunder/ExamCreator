package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.ApplicationMain;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class QuestionsViewController {
    private final QuestionsViewServiceImpl questionsViewService;

    @FXML
    Button newQuestion_btn;

    @Inject
    public QuestionsViewController(QuestionsViewServiceImpl questionsViewService, ApplicationMain applicationMain) {
        this.questionsViewService = questionsViewService;

    }


    public void onNewQuestionClick() {

    }

}
