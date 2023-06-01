package com.team_kuestenflunder.exam_desktop.services;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;

public class ExamValidationViewService {
    @Inject
    QuestionRepositoryImpl questionRepository;


    public ExamValidationViewService(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = questionRepository;
    }
}
