package com.team_kuestenflunder.exam_desktop.controller;

import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.entity.Student;
import com.team_kuestenflunder.exam_desktop.repository.StudentRepository;
import com.team_kuestenflunder.exam_desktop.services.StudentViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentsViewController implements Initializable {

    //TODO add dependency injection
    StudentViewService studentViewService = new StudentViewService(new StudentRepository());
    SceneManager sceneManager = SceneManager.getInstance();


    @FXML
    Button
            bt_backToQuestionView,
            bt_addStudent,
            bt_editStudent,
            bt_deleteStudent,
            bt_createIndividualPdfExams;

    @FXML
    TextField
            tf_studentName,
            tf_studentSurname,
            tf_studentMailAddress;

    @FXML
    TableView<Student> tv_students;

    public void onBackToQuestionViewClick(ActionEvent event) {
        try {
            sceneManager.switchSceneToQuestionView(event);
        } catch (IOException e) {
            System.out.println(e.getCause());
            throw new RuntimeException(e);
        }
    }

    public void onAddStudentClick() {
        studentViewService.addStudent(
                new Student(
                        tf_studentName.getText(),
                        tf_studentSurname.getText(),
                        tf_studentMailAddress.getText()
                ));
        studentViewService.createViewTable(tv_students,studentViewService.getStudents());
        tf_studentName.clear();
        tf_studentSurname.clear();
        tf_studentMailAddress.clear();
    }

    public void onEditStudentClick() {
    }

    public void onDeleteStudentClick() {
    }

    public void onCreateIndividualPdfExams() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tv_students.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tf_studentName.setText(newSelection.getName());
                tf_studentSurname.setText(newSelection.getSurname());
                tf_studentMailAddress.setText(newSelection.getMailingAddress());
            }
        });
    }
}
