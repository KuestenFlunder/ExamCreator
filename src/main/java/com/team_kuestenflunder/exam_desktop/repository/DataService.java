package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DataService {
    private static DataService instance;
    public  static final ObservableList<Question> questions = FXCollections.observableArrayList();

    private DataService() {
    }

    public static DataService getInstance() {
        if (instance == null) {
            synchronized (DataService.class) {
                if (instance == null) {
                    instance = new DataService();
                }
            }
        }
        return instance;
    }

    public static ObservableList<Question> getQuestions() {
        return questions;
    }



}