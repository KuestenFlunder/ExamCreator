package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.Utils.JsonHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;


public class DataService {
    private static DataService instance;

    private ObservableList<Question> questions = FXCollections.observableArrayList();



    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    public ObservableList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ObservableList<Question> questions) {
        this.questions = questions;
    }


}