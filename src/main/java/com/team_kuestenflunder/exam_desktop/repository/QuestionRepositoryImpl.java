package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.collections.ObservableList;

import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository {


    DataService dataService = DataService.getInstance();

    @Override
    public void addQuestion(Question question) {
        System.out.println(dataService.getQuestions());
        dataService.getQuestions().add(question);
        System.out.println("QuestionWasAdded");
        System.out.println(dataService.getQuestions());

    }

    @Override
    public void updateQuestion(int index, Question question) {
        dataService.getQuestions().set(index, question);
    }

    @Override
    public Question getQuestion(int index) {
        return dataService.getQuestions().get(index);
    }

    @Override
    public List<Question> getQuestions() {
        return dataService.getQuestions();
    }

    @Override
    public void deleteQuestion(int index) {
        dataService.getQuestions().remove(index);
    }

    public void addQuestions(ObservableList<Question> questions) {
        dataService.setQuestions(questions);
    }
}
