package com.team_kuestenflunder.exam_desktop.controller;


import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.entity.Topics;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuestionFormController implements Initializable {
    private final QuestionFormServiceImpl questionFormService;
    private final SceneManager sceneManager = new SceneManager();
    Stage stage;
    Question question;

    @FXML
    Label l_uuid, l_creationDate;
    @FXML
    TextField tf_questionTitle;
    @FXML
    TextArea ta_questionText, ta_questionCode;
    @FXML
    Button bt_submit;
    @FXML
    ChoiceBox<Topics> cb_topic;


    @Inject
    public QuestionFormController(QuestionFormServiceImpl questionFormService) {
        this.questionFormService = questionFormService;

    }

    private void fillChoiceBox(ChoiceBox<Topics> choiceBox) {
        System.out.println("stage = " + stage);
        for (Topics topic : Topics.values()) {
            choiceBox.getItems().add(topic);
        }
    }


    public void onFormSaveClick(ActionEvent event){
        try {
        sceneManager.switchSceneToQuestionView(event);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    //Called by the Scene Manager to Pass Data to the Model
    public void setQuestionData(Question question) {
        l_uuid.setText(question.getId());
        l_creationDate.setText(question.getCreationDate().toString());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Choicebox
        fillChoiceBox(cb_topic);
        cb_topic.setValue(Topics.No_Topic);
        cb_topic.setConverter(new StringConverter<Topics>() {
            @Override
            public String toString(Topics topic) {
                // Map the displayed value to a different string for the user to see
                return switch (topic) {
                    case No_Topic -> "Thema wählen";
                    case Java_Basics -> "Java Basics";
                    case Java_Data_Types -> "Java Data Types";
                    case Operators_and_Decision_Constructs -> "Operators and Decision Constructs";
                    case Creating_and_Using_Arrays -> "Creating and Using Arrays";
                    case Using_Loop_Constructs -> "Using Loop Constructs";
                    case Methods_and_Encapsulation -> "Methods and Encapsulation";
                    case Inheritance -> "Inheritance";
                    case Handling_Exceptions -> "Handling Exceptions";
                    case Java_API -> "Java API";
                    default -> null;
                };
            }

            @Override
            public Topics fromString(String string) {
                // Map the user's selected value back to the original value
                return switch (string) {
                    case "Thema wählen" -> Topics.No_Topic;
                    case "Java Basics" -> Topics.Java_Basics;
                    case "Java Data Types" -> Topics.Java_Data_Types;
                    case "Operators and Decision Constructs" -> Topics.Operators_and_Decision_Constructs;
                    case "Creating and Using Arrays" -> Topics.Creating_and_Using_Arrays;
                    case "Using Loop Constructs" -> Topics.Using_Loop_Constructs;
                    case "Methods and Encapsulation" -> Topics.Methods_and_Encapsulation;
                    case "Inheritance" -> Topics.Inheritance;
                    case "Handling Exceptions" -> Topics.Handling_Exceptions;
                    case "Java API" -> Topics.Java_API;
                    default -> null;
                };
            }
        });


            //!Remove if JSON Repo is implemented
        questionFormService.initList();

        }

}