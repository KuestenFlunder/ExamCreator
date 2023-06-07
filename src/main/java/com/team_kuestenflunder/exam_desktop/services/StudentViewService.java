package com.team_kuestenflunder.exam_desktop.services;

import com.team_kuestenflunder.exam_desktop.Constants;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.Utils.JsonHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.entity.Student;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.repository.StudentRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Set;

public class StudentViewService {
    private final StudentRepository studentRepository = new StudentRepository();
    private final PdfCreationPopUpService pdfCreationPopUpService = new PdfCreationPopUpService(new QuestionRepositoryImpl());
    private final SceneManager sceneManager = SceneManager.getInstance();


    public void addStudent(Student student) {
        int index = getStudentIndexByID(student.getUuid());
        if (index == Constants.NOT_FOUND) {
            studentRepository.addStudent(student);
        } else {
            studentRepository.updateStudent(index, student);
        }
    }

    public void deleteStudent(Student student) {
        int index = getStudentIndexByID(student.getUuid());
        studentRepository.deleteStudent(index);
    }

    public ObservableList<Student> getStudents() {
        return studentRepository.getStudents();
    }

    private int getStudentIndexByID(String id) {
        for (int i = 0; i < studentRepository.getStudents().size(); i++) {
            if (studentRepository.getStudents().get(i).getUuid().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }

    public void createViewTable(TableView<Student> tableView) {
        TableColumn<Student, String> idColumn = new TableColumn<>("UUID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("uuid"));

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> surnameColumn = new TableColumn<>("Nachname");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<Student, String> mailAddressColumn = new TableColumn<>("E-Mail Adresse");
        mailAddressColumn.setCellValueFactory(new PropertyValueFactory<>("mailingAddress"));

        tableView.setItems(studentRepository.getStudents());

        // Clear the existing columns
        tableView.getColumns().clear();

        // Add the columns
        tableView.getColumns().addAll(
                idColumn,
                nameColumn,
                surnameColumn,
                mailAddressColumn);
    }

    public void loadInnerStorage() {
        try {
            studentRepository.addStudents(JsonHandler.readStudentJsonFromInnerStorage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createIndividualExams(ActionEvent event) throws IOException {
        sceneManager.addPdfCreationPopUp(event);
        Set<Question> randomQuestions = pdfCreationPopUpService.getRandomExamQuestions(1);
    }
}
