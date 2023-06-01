package com.team_kuestenflunder.exam_desktop.controller;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.Utils.JsonHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    Button newQuestion_btn, bt_updateQuestion, bt_deleteQuestion, bt_createExam, bt_mergeJson, bt_saveQuestionsAsJson, bt_loadQuestionsFromJson, bt_deleteJson,bt_examEvaluation;

    @FXML
    TableView<Question> tableView;

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
            sceneManager.switchSceneToQuestionForm(event, tableView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            alertMessage(Alert.AlertType.INFORMATION, "Keine Frage gewählt", "Bitte wählen sie eine Frage die sie bearbeiten wollen.");

        }
    }

    public void onDeleteButtonClick() {

        Question selectedQuestion = tableView.getSelectionModel().getSelectedItem();
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
                sceneManager.addFileChooserDialogMultiple(event,"JSON","*.json"),
                sceneManager.addFileSaveDialog(event));
    }

    public void onSaveQuestionAsJsonClick(ActionEvent event) {
        try {
            jsonHandler.writeJsonToFile(questionsViewService.getQuestions(), sceneManager.addFileSaveDialog(event));
        } catch (IOException e) {
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
            createViewTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onDeleteJsonClick(ActionEvent event) {
        try {
            File fileToDelete = sceneManager.addFileChooserDialogSingle(event);
            Alert alert = alertMessage(Alert.AlertType.WARNING, toString(), "Sind sie Sicher, dass sie diese Datei " + fileToDelete.getName() + " löschen wollen?");
            if (alert.getResult() == ButtonType.OK) {
                java.nio.file.Files.delete(fileToDelete.toPath());
            }
            System.out.println("Die Datei " + fileToDelete + " wurde erfolgreich gelöscht.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onExamEvaluationClick(ActionEvent event){
        try {
            sceneManager.switchSceneToExamValidationView(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       createViewTable();
    }

    /**
     * This method creates a new table view with four columns: UUID, Thema, Titel, and richtige Antworten.
     * It first creates each column and sets its cell value factory.
     * Then, it sets the table view's items using the list of questions from the question view service.
     * After that, it clears any existing columns in the table view.
     * Finally, it adds the new columns to the table view.
     * <p>
     * UUID column: contains the UUID of the question.
     * Thema column: contains the topic of the question.
     * Titel column: contains the title of the question.
     * richtige Antworten column: contains the number of correct answers of the question.
     * <p>
     * Note: this method does not check if the questions' properties corresponding to the columns exist.
     */
    private void createViewTable() {

        TableColumn<Question, String> idColumn = new TableColumn<>("UUID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Question, String> topicColumn = new TableColumn<>("Thema");
        topicColumn.setCellValueFactory(new PropertyValueFactory<>("topic"));

        TableColumn<Question, String> titleColumn = new TableColumn<>("Titel");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("questionTitle"));

        TableColumn<Question, Integer> correctAnswersColumn = new TableColumn<>("richtige Antworten");
        correctAnswersColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData
                        .getValue()
                        .getAnswers()
                        .getCorrectAnswers())
                        .asObject());

        tableView.setItems((ObservableList<Question>) questionsViewService.getQuestions());

        // Clear the existing columns
        tableView.getColumns().clear();

        // Add the columns
        tableView.getColumns().addAll(idColumn, topicColumn, titleColumn, correctAnswersColumn);
    }

}



