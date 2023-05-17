package com.team_kuestenflunder.exam_desktop;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.team_kuestenflunder.exam_desktop.moduls.DIConfigModule;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationMain extends Application {
    Injector injector = Guice.createInjector(new DIConfigModule());
    private final SceneManager sceneManager = new SceneManager();
    private Scene scene;

    public static void main(String[] args) {
        launch();
    }
    // Stage > Scene > Pane
    @Override
    public void start(Stage stage) throws Exception {

        scene = sceneManager.getQuestionView();
        //set a Stage Title
        stage.setTitle("Testballon ");
        stage.setScene(scene);
        stage.show();
    }




}