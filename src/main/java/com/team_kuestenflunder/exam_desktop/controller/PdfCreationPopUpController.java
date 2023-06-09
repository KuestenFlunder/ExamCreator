package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.services.PdfCreationPopUpService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PdfCreationPopUpController {
    private final PdfCreationPopUpService pdfCreationPopUpService;

    private String callerID;

    @FXML
    Button
            bt_createExam,
            bt_cancel;
    @FXML
    TextField
            tf_testTitle,
            tf_numberOfQuestions,
            tf_testDuration;



    public void setCallerID(String callerID) {
        this.callerID = callerID;
    }

    @Inject
    public PdfCreationPopUpController(PdfCreationPopUpService pdfCreationPopUpService) {
        this.pdfCreationPopUpService = pdfCreationPopUpService;
    }

    private static void exitStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onCreateTestClick(ActionEvent event) {
        if(callerID.equals("mi_createExam")){
        File outputFile = SceneManager.addFileSaveDialogFromButton(event, "PDF-Dateien (*.pdf)", "*.pdf");
            try {
                pdfCreationPopUpService.createExamPDF(
                        tf_testTitle,
                        tf_numberOfQuestions,
                        tf_testDuration,
                        outputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            exitStage(event);}
        if(callerID.equals("bt_createIndividualPdfExams")){
            File directory = SceneManager.chooseDirectory(event);
            try {
                pdfCreationPopUpService.createIndividualPdfExams(tf_testTitle,tf_numberOfQuestions,tf_testDuration,directory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            exitStage(event);

        }
    }


    public void onCancelClick(ActionEvent event) {
        exitStage(event);
    }
}
