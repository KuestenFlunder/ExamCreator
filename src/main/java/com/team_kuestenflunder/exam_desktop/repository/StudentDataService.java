package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDataService {

    private static StudentDataService instance;

    private ObservableList<Student> questions = FXCollections.observableArrayList();



    public static StudentDataService getInstance() {
        if (instance == null) {
            instance = new StudentDataService();
        }
        return instance;
    }

    public ObservableList<Student> getStudents() {
        return questions;
    }

    public void setStudents(ObservableList<Student> questions) {
        this.questions = questions;
    }


}
