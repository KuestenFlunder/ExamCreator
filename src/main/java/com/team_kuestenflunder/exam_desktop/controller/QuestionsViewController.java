package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.ApplicationMain;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.repository.DataService;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuestionsViewController implements Initializable {
    private final QuestionsViewServiceImpl questionsViewService;
    private final SceneManager sceneManager = new SceneManager();

    Question question;

    @FXML
    Button newQuestion_btn, bt_updateQuestion;

    @FXML
    ListView<Question> lstw_QuestionList;

    @Inject
    public QuestionsViewController(QuestionsViewServiceImpl questionsViewService) {
        this.questionsViewService = questionsViewService;
    }



    public void onNewQuestionClick(ActionEvent event) {
        try {
            sceneManager.switchSceneToQuestionForm(event,new Question());}
        catch (Exception e) {e.printStackTrace();}
    }
    public void onUpdateButtonClick(ActionEvent event) {
        System.out.println("\"on updateClick\" = " + "on updateClick");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lstw_QuestionList.setItems((ObservableList<Question>) questionsViewService.getQuestions());
        lstw_QuestionList.setCellFactory(param -> new ListCell<Question>() {
                @Override
                protected void updateItem(Question question, boolean empty) {
                    super.updateItem(question, empty);
                    if (empty || question == null) {
                        setText(null);
                    } else {
                        setText(question.getId() + " - " + question.getTopic()  + " - " + question.getQuestionTitle() );
                    }
                }
            });


        //questionsViewService.initList();

    }





}
