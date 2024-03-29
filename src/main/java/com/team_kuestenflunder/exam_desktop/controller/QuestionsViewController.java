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
    private final JsonHandler jsonHandler = new JsonHandler();


    @FXML
    Button bt_newQuestion, bt_updateQuestion, bt_deleteQuestion;

    @FXML
    TableView<Question> tableView;

    @FXML
    MenuItem
            mi_newQuestionList,
            mi_loadQuestionFromJson,
            mi_saveQuestionsAsJson,
            mi_mergeQuestions,
            mi_createExam,
            mi_examEvaluation,
            mi_CreateIndividualExam,
            mi_deleteJson;

    @Inject
    public QuestionsViewController(QuestionsViewServiceImpl questionsViewService) {
        this.questionsViewService = questionsViewService;
    }

    public void onNewQuestionClick(ActionEvent event) {
        try {
            SceneManager.switchSceneToQuestionForm(event, new Question());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpdateButtonClick(ActionEvent event) {
        try {
            SceneManager.switchSceneToQuestionForm(event, tableView.getSelectionModel().getSelectedItem());
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
            SceneManager.addPdfCreationPopUp(event);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCreateIndividualExamClick(ActionEvent event){
        try {
            SceneManager.switchSceneToStudentsView(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onMergeJsonClick(ActionEvent event) {
        jsonHandler.mergeJsonFiles(
                SceneManager.addFileChooserDialogMultiple(event, "JSON", "*.json"),
                SceneManager.addFileSaveDialog(event,"JSON","*.json"));
    }

    public void onSaveQuestionAsJsonClick(ActionEvent event) {
        try {
            jsonHandler.writeQuestionJsonToInnerStorage(questionsViewService.getQuestions(), SceneManager.addFileSaveDialog(event,"JSON","*.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLoadQuestionFromJsonClick(ActionEvent event) {
        // get the file that should be loaded
        File file = SceneManager.addFileChooserDialogSingle(event,"JSON","*.json");
        // update the listview with the new list --> add questionViewService.addQustions
        try {
            ObservableList<Question> questions = jsonHandler.readJsonFromFile(file);
            questionsViewService.addQuestions(questions);
            questionsViewService.createViewTable(tableView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onDeleteJsonClick(ActionEvent event) {
        try {
            File fileToDelete = SceneManager.addFileChooserDialogSingle(event,"JSON","*.json");
            Alert alert = alertMessage(
                     Alert.AlertType.WARNING
                    ,toString()
                    ,"Sind sie Sicher, dass sie diese Datei " + fileToDelete.getName() + " löschen wollen?");
            if (alert.getResult() == ButtonType.OK) {
                java.nio.file.Files.delete(fileToDelete.toPath());
            }
            System.out.println("Die Datei " + fileToDelete.getName() + " wurde erfolgreich gelöscht.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onExamEvaluationClick(ActionEvent event) {
        try {
            SceneManager.switchSceneToExamValidationView(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onNewQuestionListClick(ActionEvent event) {

        File file = SceneManager.addNewFileSaveDialog(event);
        if (file != null) {
            System.out.println("Neue Datei erzeugt: " + file.getAbsolutePath());
        } else {
            System.out.println("File creation was cancelled or failed.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionsViewService.createViewTable(tableView);
    }
}



