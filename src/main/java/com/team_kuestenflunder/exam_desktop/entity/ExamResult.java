package com.team_kuestenflunder.exam_desktop.entity;

import java.util.List;

public class ExamResult {

    private final String studentName;
    private final String studentSurname;
    private final String dateOfExam;
    private final int numberOfQuestions;
    private final String percent;
    private final String examResult;
    private final List<String> resultList;


    public ExamResult(String studentName, String studentSurname, String dateOfExam, int numberOfQuestions,
                      String percent, String examResult, List<String> resultList) {
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.dateOfExam = dateOfExam;
        this.numberOfQuestions = numberOfQuestions;
        this.percent = percent;
        this.examResult = examResult;
        this.resultList = resultList;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public String getDateOfExam() {
        return dateOfExam;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public String getPercent() {
        return percent;
    }

    public String getExamResult() {
        return examResult;
    }

    public List<String> getResultList() {
        return resultList;
    }

    @Override
    public String toString() {
        return "ExamResult{" +
                "studentName='" + studentName + '\'' +
                ", studentSurname='" + studentSurname + '\'' +
                ", dateOfExam='" + dateOfExam + '\'' +
                ", numberOfQuestions=" + numberOfQuestions +
                ", percent='" + percent + '\'' +
                ", examResult='" + examResult + '\'' +
                ", resultList=" + resultList +
                '}';
    }
}