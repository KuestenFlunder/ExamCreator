package com.team_kuestenflunder.exam_desktop.moduls;

import com.google.inject.AbstractModule;
import com.team_kuestenflunder.exam_desktop.controller.QuestionFormController;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;


    public class QuestionFormModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(QuestionFormController.class);
            bind(QuestionFormServiceImpl.class);
            bind(QuestionRepositoryImpl.class);
        }
    }

