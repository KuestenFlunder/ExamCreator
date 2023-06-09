package com.team_kuestenflunder.exam_desktop.controller;


import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.Utils.CSVHandler;
import com.team_kuestenflunder.exam_desktop.Utils.PDFHandler;
import com.team_kuestenflunder.exam_desktop.entity.ExamResult;
import com.team_kuestenflunder.exam_desktop.services.ExamEvaluationViewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExamEvaluationViewController {

    ExamEvaluationViewService examEvaluationViewService;
    ObservableList<ExamResult> examResults = FXCollections.observableArrayList();
    @FXML
    Button bt_backToQuestionView, bt_exportResultsAsPdf;
    @FXML
    MenuItem mi_evaluateExams, mi_evaluateExam;
    @FXML
    TableView<ExamResult> tv_examResults;

    //TODO dependency Injection
    public ExamEvaluationViewController() {
        this.examEvaluationViewService = new ExamEvaluationViewService();
    }

    public void onBackToQuestionViewClick(ActionEvent event) {
        try {
            SceneManager.switchSceneToQuestionView(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onEvaluateMultiExamsClick(ActionEvent event) {

        List<File> examFiles = SceneManager.addFileChooserDialogMultiple(event, "PDF", "*.pdf");

        for (File file : examFiles) {
            try {
                examResults.add(examEvaluationViewService.evaluateExam(PDFHandler.getValuesFromTest(file)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        examEvaluationViewService.createViewTable(tv_examResults, examResults);

    }

    public void onEvaluateExamsClick(ActionEvent event) {
        File singleExam = SceneManager.addFileChooserDialogSingle(event, "PDF", "*.pdf");
        try {
            examResults.add(examEvaluationViewService.evaluateExam(PDFHandler.getValuesFromTest(singleExam)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        examEvaluationViewService.createViewTable(tv_examResults, examResults);
    }


    public void onExportResultsAsPdfClick(ActionEvent event) {
        File saveCsvTo = SceneManager.addFileSaveDialogFromButton(event, "Csv", "*.csv");
        CSVHandler.writeCSV(examResults, saveCsvTo);

    }

}
