package com.team_kuestenflunder.exam_desktop.services;


import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;

public class ExamEvaluationViewService {

    QuestionRepositoryImpl questionRepository;

//TODO Add Injection
    public ExamEvaluationViewService() {
        this.questionRepository = new QuestionRepositoryImpl();
    }
}
