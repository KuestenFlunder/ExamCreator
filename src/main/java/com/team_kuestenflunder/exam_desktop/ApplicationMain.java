package com.team_kuestenflunder.exam_desktop;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.team_kuestenflunder.exam_desktop.moduls.QuestionFormModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {
    Injector questionFormInjector;
    Inject questionViewInjector;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        questionFormInjector = Guice.createInjector(new QuestionFormModule());
        FXMLLoader fxmlLoader = new FXMLLoader(
                ApplicationMain.class.getResource("questionForm.fxml"));
        fxmlLoader.setControllerFactory(questionFormInjector::getInstance);
        Scene scene = new Scene(fxmlLoader.load(), 940, 650);
        stage.setTitle("Question Form");
        stage.setScene(scene);
        stage.show();
    }



}