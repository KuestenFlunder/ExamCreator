package com.team_kuestenflunder.exam_desktop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Answers implements Serializable {

    private  List<Answer> answerList;
    private int correctAnswers;

    public Answers() {
        this.answerList = new ArrayList<>();
    }

    public Answers(List<Answer> answers) {
        this.answerList = answers;
    }


    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void addAnswer(Answer answer) {
        if (answer.isCorrectAnswer()) {
            correctAnswers++;
        }

        this.answerList.add(answer);
    }


    @Override
    public String toString() {
        return "Answers{" +
                "correctAnswers: " +
                correctAnswers +
                "answers=" + answerList +
                '}';
    }
}
