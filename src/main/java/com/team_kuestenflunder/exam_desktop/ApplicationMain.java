package com.team_kuestenflunder.exam_desktop;

import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {
    QuestionRepositoryImpl questionRepository = new QuestionRepositoryImpl();

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                ApplicationMain.class.getResource("QuestionsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 940, 650);
        stage.setTitle("Question List");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}