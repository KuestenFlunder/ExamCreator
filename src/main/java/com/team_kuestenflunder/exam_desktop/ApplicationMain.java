package com.team_kuestenflunder.exam_desktop;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.team_kuestenflunder.exam_desktop.moduls.DIConfigModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {
    Injector injector;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        injector = Guice.createInjector(new DIConfigModule());
        FXMLLoader fxmlLoader = new FXMLLoader(
                ApplicationMain.class.getResource("questionForm.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        Scene scene = new Scene(fxmlLoader.load(), 940, 650);
        stage.setTitle("Question Form");
        stage.setScene(scene);
        stage.show();
    }



}