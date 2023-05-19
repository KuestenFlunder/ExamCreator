package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class JsonRepository implements QuestionRepository{
    public final ObservableList<Question> questions = FXCollections.observableArrayList();

    @Override
    public void addQuestion(Question question) {

    }

    @Override
    public void updateQuestion(int index, Question question) {

    }

    @Override
    public Question getQuestion(int index) {
        return null;
    }

    @Override
    public List<Question> getQuestions() {
        return null;
    }


    @Override
    public void deleteQuestion(int index) {

    }


}
