package com.team_kuestenflunder.exam_desktop.entity;

import java.util.ArrayList;
import java.util.List;

public class Answers {

    private static int correctAnswers; // TODO statische Methode für Erfassung der korrekten Antwortanzahl
    private List<Answer> answers;


    public Answers(){
        this.answers = new ArrayList<>();
    }
    public void setCorrectAnswers() {
        correctAnswers++;
    }
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Answers{" +
                "correctAnswers: " +
                correctAnswers +
                "answers=" + answers +
                '}';
    }
}
