package com.team_kuestenflunder.exam_desktop.controller;


import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.entity.Answer;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.entity.Topics;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class QuestionFormController implements Initializable {
    private final QuestionFormServiceImpl questionFormService;
    private final SceneManager sceneManager = new SceneManager();
    @FXML
    Label l_uuid, l_creationDate;
    @FXML
    TextField tf_questionTitle;
    @FXML
    TextArea ta_questionText, ta_questionCode, ta_answerText_0, ta_answerText_1, ta_answerText_2, ta_answerText_3,
            ta_answerText_4, ta_answerText_5, ta_answerText_6, ta_answerText_7, ta_answerDescription_0, ta_answerDescription_1, ta_answerDescription_2, ta_answerDescription_3,
            ta_answerDescription_4, ta_answerDescription_5, ta_answerDescription_6, ta_answerDescription_7;
    @FXML
    Button bt_submit;
    @FXML
    ChoiceBox<Topics> cb_topic;
    @FXML
    CheckBox chb_correctAnswer_0, chb_correctAnswer_1, chb_correctAnswer_2, chb_correctAnswer_3,
            chb_correctAnswer_4, chb_correctAnswer_5, chb_correctAnswer_6, chb_correctAnswer_7;
    private List<TextArea> answerTexts;
    private List<TextArea> answerDescriptions;
    private List<CheckBox> answerCheckboxes;
    private Question question;


    @Inject
    public QuestionFormController(QuestionFormServiceImpl questionFormService) {
        this.questionFormService = questionFormService;

    }

    private void fillChoiceBox(ChoiceBox<Topics> choiceBox) {

        for (Topics topic : Topics.values()) {
            choiceBox.getItems().add(topic);
        }
    }


    public void onFormSaveClick(ActionEvent event) {
        try {
            question.setQuestionTitle(tf_questionTitle.getText());
            question.setQuestionText(ta_questionText.getText());
            question.setTopic(cb_topic.getValue());
            question.setQuestionText(ta_questionText.getText());
            question.setCode(ta_questionCode.getText());

            //question.setAnswerList(new Answers());
            for (int i = 0; i < 8; i++) {
                String answerTextValue = answerTexts.get(i).getText();
                boolean answerCorrectValue = answerCheckboxes.get(i).isSelected();
                String answerDescriptionValue = answerDescriptions.get(i).getText();

                Answer answer = new Answer();
                answer.setAnswerText(answerTextValue);
                answer.setCorrectAnswer(answerCorrectValue);
                answer.setAnswerDescription(answerDescriptionValue);

                question.getAnswers().addAnswer(answer);
            }

            questionFormService.addQuestion(question);

            sceneManager.switchSceneToQuestionView(event);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setNewQuestionData(Question question) {
        this.question = question;
        l_uuid.setText(question.getId());
        l_creationDate.setText(question.getCreationDate().toString());
    }

    //Called by the Scene Manager to Pass Data to the Model
    public void setQuestionData(Question question) {
        this.question = question;
        if (questionFormService.getQuestions().contains(question)) {
            l_uuid.setText(question.getId());
            l_creationDate.setText(question.getCreationDate().toString());
            cb_topic.setValue(question.getTopic());
            tf_questionTitle.setText(question.getQuestionTitle());
            ta_questionText.setText(question.getQuestionText());
            ta_questionCode.setText(question.getCode());

            for (int i = 0; i < 8; i++) {
                answerTexts.get(i).setText(question.getAnswers().getAnswerList().get(i).getAnswerText());
                answerCheckboxes.get(i).setSelected(question.getAnswers().getAnswerList().get(i).isCorrectAnswer());
                answerDescriptions.get(i).setText(question.getAnswers().getAnswerList().get(i).getAnswerDescription());
            }
        } else {
            l_uuid.setText(question.getId());
            l_creationDate.setText(question.getCreationDate().toString());
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        answerTexts = Arrays.asList(ta_answerText_0, ta_answerText_1, ta_answerText_2, ta_answerText_3,
                ta_answerText_4, ta_answerText_5, ta_answerText_6, ta_answerText_7);

        answerCheckboxes = Arrays.asList(chb_correctAnswer_0, chb_correctAnswer_1, chb_correctAnswer_2, chb_correctAnswer_3,
                chb_correctAnswer_4, chb_correctAnswer_5, chb_correctAnswer_6, chb_correctAnswer_7);

        answerDescriptions = Arrays.asList(ta_answerDescription_0, ta_answerDescription_1, ta_answerDescription_2, ta_answerDescription_3,
                ta_answerDescription_4, ta_answerDescription_5, ta_answerDescription_6, ta_answerDescription_7);
        //Choice box
        fillChoiceBox(cb_topic);
        cb_topic.setValue(Topics.No_Topic);
        cb_topic.setConverter(new StringConverter<>() {
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

    }

}

