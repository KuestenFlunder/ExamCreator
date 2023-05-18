package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.ApplicationMain;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class QuestionsViewController {
    private final QuestionsViewServiceImpl questionsViewService;
    private final SceneManager sceneManager = new SceneManager();

    Question question;

    @FXML
    Button newQuestion_btn;

    @Inject
    public QuestionsViewController(QuestionsViewServiceImpl questionsViewService) {
        this.questionsViewService = questionsViewService;
    }



    public void onNewQuestionClick(ActionEvent event) {
        try {
        sceneManager.switchSceneToQuestionForm(event,new Question());}
        catch (Exception e) {e.printStackTrace();}
    }

}
