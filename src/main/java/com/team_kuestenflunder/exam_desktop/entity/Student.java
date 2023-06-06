package com.team_kuestenflunder.exam_desktop.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Student {
    String uuid;
    String name;
    String surname;
    String mailingAddress;
    List<Student> studentTestList = new ArrayList<Student>();

    public Student() {
        uuid = UUID.randomUUID().toString();
    }

    public Student(String name, String surname, String mailingAddress) {
        uuid = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.mailingAddress = mailingAddress;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public List<Student> getStudentTestList() {
        return studentTestList;
    }

    public void setStudentTestList(List<Student> studentTestList) {
        this.studentTestList = studentTestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(uuid, student.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Student{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", mailingAddress='" + mailingAddress + '\'' +
                ", studentTestList=" + studentTestList +
                '}';
    }
}

