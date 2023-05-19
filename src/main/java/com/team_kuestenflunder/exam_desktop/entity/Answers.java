package com.team_kuestenflunder.exam_desktop.entity;

import java.util.List;

public class Answers {

    static int correctAnswers; // TODO statische Methode für Erfassung der korrekten Antwortanzahl
    private List<Answer> answers;


    public Answers(){

    }
    public Answers(List<Answer> answers) {
        this.answers = answers;
    }



}
