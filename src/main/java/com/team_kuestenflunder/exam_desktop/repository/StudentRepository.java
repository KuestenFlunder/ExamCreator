package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.entity.Student;
import javafx.collections.ObservableList;



public class StudentRepository {
    StudentDataService studentDataService = StudentDataService.getInstance();

    public void updateStudent(int index, Student student) {
        studentDataService.getStudents().set(index, student);
    }

    public Student getStudent(int index) {
        return studentDataService.getStudents().get(index);
    }

    public ObservableList<Student> getStudents() {
        return studentDataService.getStudents();
    }

    public void deleteStudent(int index) {
        studentDataService.getStudents().remove(index);
    }

    public void addStudents(ObservableList<Student> student) {
        studentDataService.setStudents(student);
    }

    public void addStudent(Student student) {
        studentDataService.getStudents().add(student);

    }
}
