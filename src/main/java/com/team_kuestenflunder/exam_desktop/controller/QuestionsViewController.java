package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.Utils.JsonHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.team_kuestenflunder.exam_desktop.Utils.AlertMessage.alertMessage;

public class QuestionsViewController implements Initializable {

    private final QuestionsViewServiceImpl questionsViewService;
    private final SceneManager sceneManager = SceneManager.getInstance();
    private final JsonHandler jsonHandler = new JsonHandler();

    @FXML
    Button newQuestion_btn, bt_updateQuestion, bt_deleteQuestion, bt_createExam, bt_mergeJson, bt_saveQuestionsAsJson, bt_loadQuestionsFromJson;

    @FXML
    ListView<Question> lstw_QuestionList;

    @Inject
    public QuestionsViewController(QuestionsViewServiceImpl questionsViewService) {
        this.questionsViewService = questionsViewService;
    }


    public void onNewQuestionClick(ActionEvent event) {
        try {
            sceneManager.switchSceneToQuestionForm(event, new Question());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpdateButtonClick(ActionEvent event) {
        try {
            sceneManager.switchSceneToQuestionForm(event, lstw_QuestionList.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            alertMessage(Alert.AlertType.INFORMATION, "Keine Frage gewählt", "Bitte wählen sie eine Frage die sie bearbeiten wollen.");

        }
    }

    public void onDeleteButtonClick() {

        Question selectedQuestion = lstw_QuestionList.getSelectionModel().getSelectedItem();
        try {
            if (selectedQuestion != null) {
                Alert alert = alertMessage(Alert.AlertType.WARNING, "Frage löschen", "MÖCHTEN SIE DIESE FRAGE UNWIEDERRUFLICH LÖSCHEN ?");
                if (alert.getResult() == ButtonType.OK) {
                    questionsViewService.deleteQuestion(selectedQuestion);
                }
            } else {
                alertMessage(Alert.AlertType.INFORMATION, "Bitte Frage wählen", "UM ZU LÖSCHEN WÄHLEN SIE ERSTMAL EINE FRAGE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreateExamPdfClick(ActionEvent event) {
        try {
            sceneManager.addPdfCreationPopUp(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMergeJsonClick(ActionEvent event) {
        jsonHandler.mergeJsonFiles(
                sceneManager.addFileChooserDialogMultiple(event),
                sceneManager.addFileSaveDialog(event));
    }

    public void onSaveQuestionAsJsonClick(ActionEvent event)  {
      try {
        jsonHandler.writeJsonToFile( questionsViewService.getQuestions(),sceneManager.addFileSaveDialog(event));
      }catch (IOException e){
          e.printStackTrace();
      }
    }

    public void onLoadQuestionFromJsonClick(ActionEvent event) {
        // get the file that should be loaded
        File file = sceneManager.addFileChooserDialogSingle(event);
        // update the listview with the new list --> add questionViewService.addQustions

        try {
            ObservableList<Question> questions = jsonHandler.readJsonFromFile(file);
            questionsViewService.addQuestions(questions);
            createViewList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createViewList();

    }

    private void createViewList() {
        lstw_QuestionList.setItems((ObservableList<Question>) questionsViewService.getQuestions());
        lstw_QuestionList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Question question, boolean empty) {
                super.updateItem(question, empty);
                if (empty || question == null) {
                    setText(null);
                } else {
                    setText(question.getId() + " - " + question.getTopic() + " - " + question.getQuestionTitle() + "- richtige Antworten: " + question.getAnswers().getCorrectAnswers());
                }
            }
        });
    }


}


