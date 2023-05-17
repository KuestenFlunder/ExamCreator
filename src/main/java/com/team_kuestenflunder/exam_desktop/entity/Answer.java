package com.team_kuestenflunder.exam_desktop.entity;

public class Answer {
//eigenschaften
    private String answerText;
    private boolean isRight;
    private String commentText;

// konstruktor
    public Answer (){
    }

    public Answer (String answerText, boolean isRight, String commentText){
        this.answerText = answerText;
        this.isRight = isRight;
        this.commentText = commentText;
    }

//methoden
    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean getRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }



}
