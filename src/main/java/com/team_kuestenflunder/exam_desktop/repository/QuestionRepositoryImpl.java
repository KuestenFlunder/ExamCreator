package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.Utils.CSVHandler;
import com.team_kuestenflunder.exam_desktop.Utils.JsonHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;

import java.io.IOException;
import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository{

    DataService dataService = DataService.getInstance();
    CSVHandler csvWriter = new CSVHandler();


    //* List to store Questions for the first attempts


    @Override
    public void addQuestion(Question question)  {
        dataService.getQuestions().add(question);
        try {
        JsonHandler.writeJson(dataService.getQuestions());
        }catch (IOException e) {e.printStackTrace();}
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
