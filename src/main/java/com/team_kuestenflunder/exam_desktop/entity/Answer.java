package com.team_kuestenflunder.exam_desktop.entity;

public class Answer {
    private String answerText;
    private boolean correctAnswer;
    private String answerDescription;

    public Answer() {
    }

    public Answer(String answerText, boolean correctAnswer, String answerDescription) {
        this.answerText = answerText;
        this.correctAnswer = correctAnswer;
        this.answerDescription = answerDescription;
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

    public String getAnswerDescription() {
        return answerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerText='" + answerText + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", answerDescription='" + answerDescription + '\'' +
                '}';
    }
}
