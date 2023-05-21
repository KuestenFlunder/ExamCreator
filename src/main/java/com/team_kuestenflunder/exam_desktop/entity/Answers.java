package com.team_kuestenflunder.exam_desktop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Answers implements Serializable {

    private  List<Answer> answers;
    private int correctAnswers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }


    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        if (answer.isCorrectAnswer()) {
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
