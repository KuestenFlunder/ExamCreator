package com.team_kuestenflunder.exam_desktop.controller;


import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.services.ExamValidationViewService;

public class ExamValidationViewController {

    @Inject
    ExamValidationViewService examValidationViewService;


    public ExamValidationViewController(ExamValidationViewService examValidationViewService) {
        this.examValidationViewService = examValidationViewService;
    }


}
