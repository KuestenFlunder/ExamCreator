package com.team_kuestenflunder.exam_desktop.entity;

import java.util.ArrayList;
import java.util.List;

public class Answers {

    //! Darf nicht statisch sein, da sonst alle richtigen Antworten von allen Fragen gezählt werden.
    private int correctAnswers; // TODO nicht statisch machen.
    private List<Answer> answers;


    public Answers() {
        this.answers = new ArrayList<>();
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }


    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void addAnswer(Answer answer) {
        if (answer.isCorrectAnswer()){
            correctAnswers++;
        }

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
