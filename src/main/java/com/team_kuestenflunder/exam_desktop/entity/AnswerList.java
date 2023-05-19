package com.team_kuestenflunder.exam_desktop.entity;

import java.util.ArrayList;
import java.util.List;

public class AnswerList {

    private static int correctAnswers; // TODO statische Methode für Erfassung der korrekten Antwortanzahl
    private List<Answer> answers;


    public AnswerList(){
        answers = new ArrayList<>();
    }

    public AnswerList(List<Answer> answers) {
        this.answers = answers;
    }

    public void setCorrectAnswers() {
        AnswerList.correctAnswers++;
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
    }

    public List<Answer> getAnswers() {
        return answers;
    }


}
