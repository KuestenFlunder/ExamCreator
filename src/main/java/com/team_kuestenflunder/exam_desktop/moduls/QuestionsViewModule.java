package com.team_kuestenflunder.exam_desktop.moduls;

import com.google.inject.AbstractModule;
import com.team_kuestenflunder.exam_desktop.controller.QuestionsViewController;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;

public class QuestionsViewModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(QuestionsViewController.class);
        bind(QuestionsViewServiceImpl.class);
        bind(QuestionRepositoryImpl.class);
    }

}
