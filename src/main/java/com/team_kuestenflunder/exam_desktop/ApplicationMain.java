package com.team_kuestenflunder.exam_desktop;

import com.team_kuestenflunder.exam_desktop.controller.QuestionFormController;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        //Initialise Repos/Services and Controllers
        QuestionRepositoryImpl questionRepository = new QuestionRepositoryImpl();
        QuestionFormServiceImpl questionFormService = new QuestionFormServiceImpl(questionRepository);
        QuestionFormController questionFormController =new QuestionFormController();
        questionRepository.initList();

        FXMLLoader fxmlLoader = new FXMLLoader(
                ApplicationMain.class.getResource("QuestionsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 940, 650);
        stage.setTitle("Question List");
        stage.setScene(scene);
        stage.show();
    }
}