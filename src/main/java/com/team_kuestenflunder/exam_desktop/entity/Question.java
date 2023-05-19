package com.team_kuestenflunder.exam_desktop.entity;


import java.time.LocalDateTime;
import java.util.UUID;

public class Question {


    private final String id;

    private final LocalDateTime creationDate;

    private String questionTitle; //? Might be useful if we find meaningful titles
    private Topics topic;
    private String questionText;
    private String Code;
    private Answers answers;

    public Question() {
        this.creationDate = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
        this.answers = new Answers();
    }


    public String getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Answers getAnswerList() {
        return answers;
    }

    public void setAnswerList(Answers answers) {
        this.answers = answers;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public Topics getTopic() {
        return topic;
    }

    public void setTopic(Topics topic) {
        this.topic = topic;
    }






    @Override
    public String toString() {
        return "Question{" + "\n" +
                "id='" + id + '\'' +
                ", creationDate=" + creationDate +
                ", answers=" + answers.toString().replaceAll("\n", "") +
                ", Code='" + Code + '\'' +
                ", questionText='" + questionText + '\'' +
                ", questionTitle='" + questionTitle + '\'' +
                ", topic=" + topic +
                '}' + "\n";
    }
}
