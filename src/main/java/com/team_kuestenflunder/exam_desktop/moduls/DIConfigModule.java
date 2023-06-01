package com.team_kuestenflunder.exam_desktop.moduls;

import com.google.inject.AbstractModule;
import com.team_kuestenflunder.exam_desktop.controller.ExamValidationViewController;
import com.team_kuestenflunder.exam_desktop.controller.PdfCreationPopUpController;
import com.team_kuestenflunder.exam_desktop.controller.QuestionFormController;
import com.team_kuestenflunder.exam_desktop.controller.QuestionsViewController;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.services.PdfCreationPopUpService;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;


public class DIConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        // bind Repository
        bind(QuestionRepositoryImpl.class);


        // bind form dependency's
        bind(QuestionFormController.class);
        bind(QuestionFormServiceImpl.class);


        // bind view dependency's
        bind(QuestionsViewController.class);
        bind(QuestionsViewServiceImpl.class);

        // bind Pdf Pop Up dependency's
        bind(PdfCreationPopUpController.class);
        bind(PdfCreationPopUpService.class);

        bind(ExamValidationViewController.class);
        bind(ExamValidationViewController.class);

        //bind(DataService.class).in(Singleton.class);

    }
}

