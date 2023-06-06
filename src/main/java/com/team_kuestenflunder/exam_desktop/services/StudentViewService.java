package com.team_kuestenflunder.exam_desktop.services;

import com.team_kuestenflunder.exam_desktop.Constants;
import com.team_kuestenflunder.exam_desktop.entity.Student;
import com.team_kuestenflunder.exam_desktop.repository.StudentRepository;

import java.util.List;

public class StudentViewService {
    private final StudentRepository studentRepository;

    public StudentViewService(StudentRepository studentRepository) {
        this.studentRepository = new StudentRepository();
    }

    public void addStudent(Student student) {
        int index = getStudentIndexByID(student.getUuid());
        if (index == Constants.NOT_FOUND) {
            studentRepository.addStudent(student);
        } else {
            studentRepository.updateStudent(index,student);
        }
    }

    public Student getStudent(String id) {
        int index = getStudentIndexByID(id);
        return studentRepository.getStudent(index);

    }

    public List<Student> getStudents() {
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
}
