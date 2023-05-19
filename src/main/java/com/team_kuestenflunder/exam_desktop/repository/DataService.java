package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DataService {
    private static DataService instance;
    public final ObservableList<Question> questions = FXCollections.observableArrayList();

    private DataService() {
    }

    public static DataService getInstance() {
                if (instance == null) {
                    instance = new DataService();
                }
        return instance;
    }
}