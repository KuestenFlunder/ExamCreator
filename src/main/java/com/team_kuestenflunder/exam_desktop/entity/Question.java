package com.team_kuestenflunder.exam_desktop.entity;

import java.time.LocalDate;
import java.util.UUID;

public class Question {
    private final String id;
    private final LocalDate creationDate;
    private AnswerList answerList;
    private ChangeLog changeLog;
    private String Code;
    private String questionText;
    private String questionTitle; //? Might be useful if we find meaningful titles
    private Topics topic;
    //! Maybe use Markdown for textfileds


    public Question() {

        this.creationDate = LocalDate.now();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public AnswerList getAnswerList() {
        return answerList;
    }

    public void setAnswerList(AnswerList answerList) {
        this.answerList = answerList;
    }

    public ChangeLog getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(ChangeLog changeLog) {
        this.changeLog = changeLog;
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
        return "Question{" +
                "id='" + id + '\'' +
                ", creationDate=" + creationDate +
                ", answerList=" + answerList +
                ", changeLog=" + changeLog +
                ", Code='" + Code + '\'' +
                ", questionText='" + questionText + '\'' +
                ", questionTitle='" + questionTitle + '\'' +
                ", topic=" + topic +
                '}';
    }
}
