package com.team_kuestenflunder.exam_desktop.entity;

import java.util.Map;


public class ExamResult {

    private String studentName;
    private String studentSurname;
    private String dateOfTest;
    private int numberOfQuestions;
    private Map<String, String> UUID_Map;
    private Map<String, String> correctAnswer_Map;
    private Map<String, String> answer_Map;
//    private Map<String, String> topics_Map; // nicht möglich. PDF-Dokument beinhaltet keine Topic-TextFields


    public ExamResult(){
    }


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public String getDateOfTest() {
        return dateOfTest;
    }

    public void setDateOfTest(String dateOfTest) {
        this.dateOfTest = dateOfTest;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Map<String, String> getUUID_Map() {
        return UUID_Map;
    }

    public void setUUID_Map(Map<String, String> UUID_Map) {
        this.UUID_Map = UUID_Map;
    }

    public Map<String, String> getCorrectAnswer_Map() {
        return correctAnswer_Map;
    }

    public void setCorrectAnswer_Map(Map<String, String> correctAnswer_Map) {
        this.correctAnswer_Map = correctAnswer_Map;
    }

    public Map<String, String> getAnswer_Map() {
        return answer_Map;
    }

    public void setAnswer_Map(Map<String, String> answer_Map) {
        this.answer_Map = answer_Map;
    }

    public String toString(){
        String string
                = "Student name          : " + getStudentName()
                + "\nStudent surname     : " + getStudentSurname()
                + "\nDate of test        : " + getDateOfTest()
                + "\nNumber of questions : " + getNumberOfQuestions()
                + "\nUUID_Map            : " + mapToString(getUUID_Map())
                + "\nCorrectAnswer_Map   : " + mapToString(getCorrectAnswer_Map())
                + "\nAnswer_Map          : " + mapToString(getAnswer_Map())
                ;
        return string;
    }

    private static String mapToString(Map<String, String> map){
        StringBuilder strb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()){
            strb.append(entry.getKey() +" : "+ entry.getValue());
        }
        return strb.toString();
    }


}
