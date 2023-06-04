package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.services.PdfCreationPopUpService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class PdfCreationPopUpController {
    PdfCreationPopUpService pdfCreationPopUpService;


    @FXML
    Button bt_createExam, bt_cancel;
    @FXML
    TextField
            tf_testTitle,
            tf_numberOfQuestions,
            tf_testDuration;

    @Inject
    public PdfCreationPopUpController(PdfCreationPopUpService pdfCreationPopUpService) {
        this.pdfCreationPopUpService = pdfCreationPopUpService;
    }

    private static void exitStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onCreateTestClick(ActionEvent event) {
        int requestedNumberOfQuestions = Integer.parseInt(tf_numberOfQuestions.getText());
        int testDuration = Integer.parseInt(tf_testDuration.getText());
        String testTitel = tf_testTitle.getText();
        PDFHandler pdfHandler = new PDFHandler(pdfCreationPopUpService);
        pdfHandler.createExamPDF(testTitel, requestedNumberOfQuestions, testDuration);
        exitStage(event);
    }


    public void onCancelClick(ActionEvent event) {
        exitStage(event);
    }
}
