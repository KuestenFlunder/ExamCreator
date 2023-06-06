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

    public Student() {

    }

    public Student(String name, String surname, String mailingAddress) {
        uuid = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.mailingAddress = mailingAddress;
    }

    List<Student> studentTestList = new ArrayList<Student>();


}
