package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionsViewController implements Initializable {

    private final QuestionsViewServiceImpl questionsViewService;
    private final SceneManager sceneManager = new SceneManager();

    Question question;

    @FXML
    Button newQuestion_btn, bt_updateQuestion, bt_deleteQuestion;

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


    public Question questionMarkUp(){
        return lstw_QuestionList.getSelectionModel().getSelectedItem();
    }

    public void onDeleteButtonClick(ActionEvent event) {
        Alert alert;
        try{
            if (questionMarkUp() != null) {
                String questionID = questionMarkUp().getId();
                int index = questionsViewService.getQuestionIndexByID(questionID);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setContentText("MÖCHTEN SIE DIESE FRAGE UNWIEDERRUFLICH LÖSCHEN ?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK){
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("SIND SIE 100% SICHER ?");
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK){
                        questionsViewService.getQuestions().remove(index);; // TODO
                        System.out.println("Die Frage wurde gelöscht");
                    }
                }
            }else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("UM ZU LÖSCHEN WÄHLEN SIE ERSTMAL EINE FRAGE");
                alert.showAndWait();
            }

        }catch (Exception e) {
            e.printStackTrace();}
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
