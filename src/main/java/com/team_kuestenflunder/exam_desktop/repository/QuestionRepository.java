package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Question;

import java.util.List;

public interface QuestionRepository {

    void addQuestion(Question question);
    void updateQuestion(int index,Question question);
    Question getQuestion(int index);
    List<Question> getQuestions();

    void deleteQuestion(int index);

}
