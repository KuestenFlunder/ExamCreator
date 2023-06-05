package com.team_kuestenflunder.exam_desktop.controller;


import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.Utils.PDFHandler;
import com.team_kuestenflunder.exam_desktop.entity.ExamValues;
import com.team_kuestenflunder.exam_desktop.services.ExamEvaluationViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExamEvaluationViewController {

    ExamEvaluationViewService examEvaluationViewService;
    SceneManager sceneManager = SceneManager.getInstance();

    //TODO Dependency injection for both

    PDFHandler pdfHandler = new PDFHandler();

    //TODO dependency Injection
    public ExamEvaluationViewController() {
        this.examEvaluationViewService = new ExamEvaluationViewService();
    }

    @FXML
    Button bt_backToQuestionView;
    @FXML
    MenuItem mi_evaluateExams, mi_evaluateExam;

    public void onBackToQuestionViewClick(ActionEvent event){
        try {
            sceneManager.switchSceneToQuestionView(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void onEvaluateMultiExamsClick(ActionEvent event){

        List<File> examFiles =  sceneManager.addFileChooserDialogMultiple(event,"PDF","*.pdf");
        //Stream for exam results

        System.out.println("examFiles = " + examFiles);
    }
    public void onEvaluateExamsClick(ActionEvent event){
        File singleExam =  sceneManager.addFileChooserDialogSingle(event,"PDF","*.pdf");

        ExamValues examValues = PDFHandler.getValuesFromTest(singleExam);
        System.out.println(examEvaluationViewService.evaluateExam(examValues));
    }

}
