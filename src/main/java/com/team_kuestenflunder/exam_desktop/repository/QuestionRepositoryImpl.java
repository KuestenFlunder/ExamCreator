package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.Utils.CSVWriter;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository{

    DataService dataService = DataService.getInstance();
    CSVWriter csvWriter = new CSVWriter();

    //* List to store Questions for the first attempts


    @Override
    public void addQuestion(Question question) {
        dataService.getQuestions().add(question);
        csvWriter.writeCSV(question);
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


    //TODO Remove when saving data is ready
    //!Only for testing and first trys
    public static void initList(){
        DataService.getInstance().getQuestions().add(new Question());
        DataService.getInstance().getQuestions().add(new Question());
        DataService.getInstance().getQuestions().add(new Question());

    }
}
