package com.team_kuestenflunder.exam_desktop.services;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PdfCreationPopUpService {
    QuestionRepositoryImpl questionRepository;

    @Inject
    public PdfCreationPopUpService(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = questionRepository;
    }




}
