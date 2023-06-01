package com.team_kuestenflunder.exam_desktop.services;


import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;

public class ExamValidationViewService {

    QuestionRepositoryImpl questionRepository;

//TODO Add Injection
    public ExamValidationViewService(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = new QuestionRepositoryImpl();
    }
}
