package com.team_kuestenflunder.exam_desktop.entity;

public class Answer {
    private String answerText;
    private boolean correctAnswer;
    private String answerCode;

    public Answer() {
    }

    public Answer(String answerText, boolean correctAnswer, String answerCode) {
        this.answerText = answerText;
        this.correctAnswer = correctAnswer;
        this.answerCode = answerCode;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerText='" + answerText + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", answerDescription='" + answerCode + '\'' +
                '}';
    }
}
