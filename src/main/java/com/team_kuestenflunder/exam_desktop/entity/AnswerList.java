package com.team_kuestenflunder.exam_desktop.entity;

import java.util.ArrayList;
import java.util.List;

public class AnswerList {

    private static int correctAnswers; // TODO statische Methode für Erfassung der korrekten Antwortanzahl
    public List<Answer> answers;


    public AnswerList(){
    }

    public AnswerList(List<Answer> answers) {
        this.answers = answers;
    }

    public void setCorrectAnswers() {
        AnswerList.correctAnswers++;
    }

    public void addToList(Answer answer){
        this.answers.add(answer);
    }


}
