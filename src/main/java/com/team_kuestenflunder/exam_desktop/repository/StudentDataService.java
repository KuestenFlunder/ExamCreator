package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDataService {

    private static StudentDataService instance;

    private ObservableList<Student> students = FXCollections.observableArrayList();



    public static StudentDataService getInstance() {
        if (instance == null) {
            instance = new StudentDataService();
        }
        return instance;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void setStudents(ObservableList<Student> questions) {
        this.students = questions;
    }


}
