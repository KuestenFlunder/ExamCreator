package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.Utils.JsonHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;


public class DataService {
    private static DataService instance;
    private  ObservableList<Question> questions = FXCollections.observableArrayList();

    private DataService() {
    }

    public ObservableList<Question> getQuestions() {
        return questions;
    }

    public  void initDataService() {
        try {
        questions = JsonHandler.readJson();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static DataService getInstance() {
                if (instance == null) {
                    instance = new DataService();
                }
        return instance;
    }

   



}