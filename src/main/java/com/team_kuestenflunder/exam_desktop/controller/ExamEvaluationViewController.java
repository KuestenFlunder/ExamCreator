package com.team_kuestenflunder.exam_desktop.controller;


import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.services.ExamEvaluationViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExamEvaluationViewController {

    ExamEvaluationViewService examEvaluationViewService;
    SceneManager sceneManager = SceneManager.getInstance();

    public ExamEvaluationViewController() {
        this.examEvaluationViewService = new ExamEvaluationViewService();
    }

    @FXML
    Button bt_backToQuestionView, bt_evaluateExams;

    public void onBackToQuestionViewClick(ActionEvent event){
        try {
            sceneManager.switchSceneToQuestionView(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onEvaluateExamsClick(ActionEvent event){
       List<File> examFiles =  sceneManager.addFileChooserDialogMultiple(event,"PDF","*.pdf");
        System.out.println("examFiles = " + examFiles);
    }

}
