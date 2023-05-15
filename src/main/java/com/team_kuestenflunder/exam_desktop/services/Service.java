package com.team_kuestenflunder.exam_desktop.services;

import com.team_kuestenflunder.exam_desktop.entity.Question;

import java.util.List;

public interface Service {
    void addQuestion(Question question);

    Question getQuestion(String id);

    List<Question> getQuestions();
}
