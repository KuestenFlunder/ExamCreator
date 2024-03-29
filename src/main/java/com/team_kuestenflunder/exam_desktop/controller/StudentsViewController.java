package com.team_kuestenflunder.exam_desktop.controller;

import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.Utils.PDFHandler;
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
    private final StudentViewService studentViewService = new StudentViewService();

    private Student actualStudent;


    @FXML
    Button
            bt_backToQuestionView,
            bt_addStudent,

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
            SceneManager.switchSceneToQuestionView(event);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    public void onAddStudentClick() {
        if (actualStudent == null) {
            createNewStudent();
        } else {
            updateExistingQuestion();
        }
        studentViewService.addStudent(actualStudent);
        studentViewService.createViewTable(tv_students);
        clearAll();
    }

    private void createNewStudent() {
        actualStudent = new Student(
                tf_studentName.getText(),
                tf_studentSurname.getText(),
                tf_studentMailAddress.getText());
    }

    private void updateExistingQuestion() {
        actualStudent.setName(tf_studentName.getText());
        actualStudent.setSurname(tf_studentSurname.getText());
        actualStudent.setMailingAddress(tf_studentMailAddress.getText());
    }

    private void clearAll() {
        tf_studentName.clear();
        tf_studentSurname.clear();
        tf_studentMailAddress.clear();
        actualStudent = null;
        tv_students.getSelectionModel().clearSelection();
    }

    public void onDeleteStudentClick() {
        if(actualStudent != null){
            studentViewService.deleteStudent(actualStudent);
        }
        clearAll();
    }

    public void onCreateIndividualPdfExams(ActionEvent event) {
        try {
            studentViewService.createIndividualExams(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tv_students.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                actualStudent = newSelection;
                tf_studentName.setText(newSelection.getName());
                tf_studentSurname.setText(newSelection.getSurname());
                tf_studentMailAddress.setText(newSelection.getMailingAddress());
            }
        });
        studentViewService.createViewTable(tv_students);
    }
}
