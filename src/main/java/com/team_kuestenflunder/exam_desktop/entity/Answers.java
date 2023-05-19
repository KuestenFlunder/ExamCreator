package com.team_kuestenflunder.exam_desktop.entity;

import java.util.ArrayList;
import java.util.List;

public class Answers {

    //! Darf nicht statisch sein, da sonst alle richtigen Antworten von allen Fragen gezählt werden.
    private static int correctAnswers; // TODO nicht statisch machen.
    private List<Answer> answers;


    public Answers(){
        this.answers = new ArrayList<>();
    }
    public Answers(List<Answer> answers) {
        this.answers = answers;
    }
    public void setCorrectAnswers() {
        correctAnswers++;
    }
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void addAnswer(Answer answer){
        //TODO Add the correctAnswer++ here if Answer is correct
        this.answers.add(answer);
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
