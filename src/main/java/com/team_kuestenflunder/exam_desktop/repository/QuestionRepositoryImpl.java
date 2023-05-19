package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository{

    DataService dataService = DataService.getInstance();

    //* List to store Questions for the first attempts


    @Override
    public void addQuestion(Question question) {
        dataService.questions.add(question);
    }

    @Override
    public void updateQuestion(int index, Question question) {
        dataService.questions.set(index, question);
    }

    @Override
    public Question getQuestion(int index) {

        return dataService.questions.get(index);
    }

    @Override
    public List<Question> getQuestions() {
        return dataService.questions;
    }

    @Override
    public void deleteQuestion(int index) {
        dataService.questions.remove(index);
    }


    //TODO Remove when saving data is ready
    //!Only for testing and first trys
    public static void initList(){
        DataService.getInstance().questions.add(new Question());
        DataService.getInstance().questions.add(new Question());
        DataService.getInstance().questions.add(new Question());

    }
}
