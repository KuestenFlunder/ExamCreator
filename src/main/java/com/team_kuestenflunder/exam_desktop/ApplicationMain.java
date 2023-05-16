package com.team_kuestenflunder.exam_desktop;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.team_kuestenflunder.exam_desktop.moduls.DIConfigModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {
    Injector injector;
    private Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        injector = Guice.createInjector(new DIConfigModule());
        switchToQuestionForm();
        primaryStage.show();


    }

    private void switchToQuestionForm() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("questionForm.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Question Form");
        stage.setScene(scene);
    }

    private void switchToQuestionView() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("questionsView.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Question View");
        stage.setScene(scene);
    }




}