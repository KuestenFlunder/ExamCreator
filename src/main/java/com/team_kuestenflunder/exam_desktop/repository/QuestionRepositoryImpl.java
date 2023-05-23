package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.Utils.CSVHandler;
import com.team_kuestenflunder.exam_desktop.Utils.JsonHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;

import java.io.IOException;
import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository{

    DataService dataService = DataService.getInstance();

    @Override
    public void addQuestion(Question question)  {
        dataService.getQuestions().add(question);
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


}
