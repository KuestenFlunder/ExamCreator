package com.team_kuestenflunder.exam_desktop;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.team_kuestenflunder.exam_desktop.controller.QuestionFormController;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.moduls.DIConfigModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    Injector injector = Guice.createInjector(new DIConfigModule());
    private Scene scene;
    private Stage stage;
    private Parent root;

    public void switchSceneToQuestionForm(ActionEvent event, Question question) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("questionForm.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        root = fxmlLoader.load();
        QuestionFormController questionFormController = fxmlLoader.getController();
        questionFormController.setQuestionData(question);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Fragen Editor");
        stage.show();
    }

    public void switchSceneToQuestionView(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("questionsView.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Fragen Liste");
        stage.show();
    }

    public void addPdfCreationPopUp(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pdfCreationPopUp.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner( (Stage) ((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();

    }


    // used to inizialise the first view
    public Scene getQuestionView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("questionsView.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        Parent formParent = fxmlLoader.load();
        //Create a new sceen from the root node of the fxml
        return new Scene(formParent);
    }


}
