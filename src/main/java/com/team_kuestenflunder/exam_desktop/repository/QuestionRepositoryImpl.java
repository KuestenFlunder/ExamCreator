package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository{

    //* List to store Questions for the first attempts
    private List<Question> questions = new ArrayList<>();

    @Override
    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public void updateQuestion(int index, Question question) {
        questions.set(index, question);
    }

    @Override
    public Question getQuestion(int index) {

        return questions.get(index);
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
