package com.team_kuestenflunder.exam_desktop.controller;


import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.entity.Answer;
import com.team_kuestenflunder.exam_desktop.entity.Topics;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
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

    @FXML
    private RadioButton rdb_AnswerActivate;
    @FXML
    TextArea ta_answerText;
    @FXML
    CheckBox chb_isRight;
    @FXML
    TextArea ta_commentText;


    @Inject
    public QuestionFormController(QuestionFormServiceImpl questionFormService) {
        this.questionFormService = questionFormService;

    }

    private void fillChoiceBox(ChoiceBox<Topics> choiceBox) {
        for (Topics topic : Topics.values()) {
            choiceBox.getItems().add(topic);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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


    @FXML
    public void radioButtunClick(ActionEvent event) {
        List<Answer> answerList = new ArrayList<>();
        Answer answer = new Answer();
        answer.setAnswerText(ta_answerText.getText());
        answer.setRight(chb_isRight.isSelected());
        answer.setCommentText(ta_commentText.getText());
        answerList.add(answer);

        System.out.println(answer.getAnswerText().toString());
        System.out.println(String.valueOf(answer.getRight()));
        System.out.println(answer.getCommentText().toString());
    }

}