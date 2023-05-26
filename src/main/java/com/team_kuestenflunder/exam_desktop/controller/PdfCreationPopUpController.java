package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.services.PdfCreationPopUpService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PdfCreationPopUpController {
    PdfCreationPopUpService pdfCreationPopUpService;

    @Inject
    public PdfCreationPopUpController(PdfCreationPopUpService pdfCreationPopUpService) {
        this.pdfCreationPopUpService = pdfCreationPopUpService;
    }

    @FXML
    Button bt_createExam,bt_cancel;
}
