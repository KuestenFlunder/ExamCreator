package com.team_kuestenflunder.exam_desktop;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.team_kuestenflunder.exam_desktop.Utils.CSVHandler;
import com.team_kuestenflunder.exam_desktop.Utils.JsonHandler;
import com.team_kuestenflunder.exam_desktop.moduls.DIConfigModule;
import com.team_kuestenflunder.exam_desktop.repository.DataService;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.repository.StudentDataService;
import com.team_kuestenflunder.exam_desktop.repository.StudentRepository;
import com.team_kuestenflunder.exam_desktop.services.QuestionsViewServiceImpl;
import com.team_kuestenflunder.exam_desktop.services.StudentViewService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ApplicationMain extends Application {
    private final SceneManager sceneManager = SceneManager.getInstance();
    private final JsonHandler jsonHandler = new JsonHandler();
    private final QuestionsViewServiceImpl questionViewService= new QuestionsViewServiceImpl(new QuestionRepositoryImpl());
    private final StudentViewService studentViewService = new StudentViewService();
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        questionViewService.loadInnerStorage();
        studentViewService.loadInnerStorage();

        Scene scene = sceneManager.getQuestionView();
        stage.setTitle("Fragen Liste");
        stage.setScene(scene);
        stage.show();
    }

    public void stop() throws Exception {
        jsonHandler.writeQuestionJsonToInnerStorage(questionViewService.getQuestions(),null);
        jsonHandler.writeStudentsJsonToInnerStorage(studentViewService.getStudents(),null);
    }



}