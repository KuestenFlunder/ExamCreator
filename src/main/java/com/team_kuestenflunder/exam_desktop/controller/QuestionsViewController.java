package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;

public class QuestionsViewController {
    QuestionsViewServiceImpl questionsViewService;

    @Inject
    public QuestionsViewController(QuestionsViewServiceImpl questionsViewService) {
        this.questionsViewService = questionsViewService;
    }



}
