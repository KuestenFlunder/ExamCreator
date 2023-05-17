package com.team_kuestenflunder.exam_desktop.controller;


import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.entity.Topics;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionFormController implements Initializable {
    private final QuestionFormServiceImpl questionFormService;
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

    private void populateChoiceBox(ChoiceBox<Topics> choiceBox) {
        for (Topics topic : Topics.values()) {
            choiceBox.getItems().add(topic);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateChoiceBox(cb_topic);
        cb_topic.setValue(Topics.No_Topic);
        cb_topic.setConverter(new StringConverter<Topics>() {
            @Override
            public String toString(Topics topic) {
                // Map the displayed value to a different string for the user to see
                switch (topic) {
                    case No_Topic:
                        return "Kein Thema";
                    case Java_Basics:
                        return "Java Basics";
                    case Java_Data_Types:
                        return "Java Data Types";
                    case Operators_and_Decision_Constructs:
                        return "Operators and Decision Constructs";
                    case Creating_and_Using_Arrays:
                        return "Creating and Using Arrays";
                    case Using_Loop_Constructs:
                        return "Using Loop Constructs";
                    case Methods_and_Encapsulation:
                        return "Methods and Encapsulation";
                    case Inheritance:
                        return "Inheritance";
                    case Handling_Exceptions:
                        return "Handling Exceptions";
                    case Java_API:
                        return "Java API";
                    default:
                        return null;
                }
            }

            @Override
            public Topics fromString(String string) {
                // Map the user's selected value back to the original value
                switch (string) {
                    case "Kein Thema":
                        return Topics.No_Topic;
                    case "Java Basics":
                        return Topics.Java_Basics;
                    case "Java Data Types":
                        return Topics.Java_Data_Types;
                    case "Operators and Decision Constructs":
                        return Topics.Operators_and_Decision_Constructs;
                    case "Creating and Using Arrays":
                        return Topics.Creating_and_Using_Arrays;
                    case "Using Loop Constructs":
                        return Topics.Using_Loop_Constructs;
                    case "Methods and Encapsulation":
                        return Topics.Methods_and_Encapsulation;
                    case "Inheritance":
                        return Topics.Inheritance;
                    case "Handling Exceptions":
                        return Topics.Handling_Exceptions;
                    case "Java API":
                        return Topics.Java_API;
                    default:
                        return null;
                }
            }
        });

        //TODO ? Add checkbox.addConverter() to differ the shown Value and the field Text.

            //!Remove if JSON Repo is implemented
        questionFormService.initList();

        }
    }