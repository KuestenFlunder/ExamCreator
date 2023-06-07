package com.team_kuestenflunder.exam_desktop.controller;


import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.Utils.PDFHandler;
import com.team_kuestenflunder.exam_desktop.entity.Answer;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.entity.Topics;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
//import java.awt.Desktop;  //TODO


public class QuestionFormController implements Initializable {
    private final QuestionFormServiceImpl questionFormService;
    private final SceneManager sceneManager = SceneManager.getInstance();
    @FXML
    Label l_uuid, l_creationDate;
    @FXML
    TextField tf_questionTitle;
    @FXML
    TextArea ta_questionText, ta_questionCode,
            ta_answerText_0, ta_answerText_1, ta_answerText_2, ta_answerText_3, ta_answerText_4, ta_answerText_5,
            ta_answerCode_0, ta_answerCode_1, ta_answerCode_2, ta_answerCode_3, ta_answerCode_4, ta_answerCode_5;
    @FXML
    Button
            bt_submit,
            bt_cancel,
            bt_preview,
            bt_backToQuestionForm;
    @FXML
    ChoiceBox<Topics> cb_topic;
    @FXML
    CheckBox chb_correctAnswer_0, chb_correctAnswer_1, chb_correctAnswer_2, chb_correctAnswer_3, chb_correctAnswer_4, chb_correctAnswer_5;

    @FXML
    ImageView iv_pdfPreview;
    private List<TextArea> answerTexts;
    private List<TextArea> answerCodes;
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
            question.setQuestionCode(ta_questionCode.getText());
            //reset correct Answers do omit doubles and clean of AnswerList
            question.getAnswers().setCorrectAnswers(0);
            question.getAnswers().setAnswerList(new ArrayList<>());

            for (int i = 0; i < 6; i++) {
                String answerTextValue = answerTexts.get(i).getText();
                boolean answerCorrectValue = answerCheckboxes.get(i).isSelected();
                String answerCodeValue = answerCodes.get(i).getText();
                Answer answer;

                if (i < question.getAnswers().getAnswerList().size()) {
                    // Update existing answer
                    answer = question.getAnswers().getAnswerList().get(i);
                    answer.setAnswerText(answerTextValue);
                    answer.setAnswerCode(answerCodeValue);
                    answer.setCorrectAnswer(answerCorrectValue);

                } else {
                    // Create new answer
                    answer = new Answer();
                    answer.setAnswerText(answerTextValue);
                    answer.setCorrectAnswer(answerCorrectValue);
                    answer.setAnswerCode(answerCodeValue);
                }

                question.getAnswers().addAnswer(answer);
            }
            questionFormService.addQuestion(question);
            sceneManager.switchSceneToQuestionView(event);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onCancelClick(ActionEvent event) {
        try {
            sceneManager.switchSceneToQuestionView(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onPreviewClick(ActionEvent event) {
        try {
            Question onQuestion = new Question();
            onQuestion.setId(l_uuid.getText());
            onQuestion.setQuestionTitle(tf_questionTitle.getText());
            onQuestion.setQuestionText(ta_questionText.getText());
            onQuestion.setTopic(cb_topic.getValue());
            onQuestion.setQuestionText(ta_questionText.getText());
            onQuestion.setQuestionCode(ta_questionCode.getText());
            int counter = 0;
            List<Answer> answerList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                String answerTextValue = answerTexts.get(i).getText();
                String answerCodeValue = answerCodes.get(i).getText();
                boolean answerCorrectValue = answerCheckboxes.get(i).isSelected();
                if (answerCorrectValue) counter++;
                Answer answer = new Answer();
                answer.setAnswerText(answerTextValue);
                answer.setCorrectAnswer(answerCorrectValue);
                answer.setAnswerCode(answerCodeValue);
                answerList.add(answer);
            }
            onQuestion.getAnswers().setAnswerList(answerList);
            onQuestion.getAnswers().setCorrectAnswers(counter);

            String temporaryFilePath = PDFHandler.createQuestionPage(0, onQuestion, 0);
            File temporaryPDFFile = new File(temporaryFilePath);
            openTemporaryPDFFile(temporaryPDFFile, event);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            ta_questionCode.setText(question.getQuestionCode());

            for (int i = 0; i < 6; i++) {
                answerTexts.get(i).setText(question.getAnswers().getAnswerList().get(i).getAnswerText());
                answerCheckboxes.get(i).setSelected(question.getAnswers().getAnswerList().get(i).isCorrectAnswer());
                answerCodes.get(i).setText(question.getAnswers().getAnswerList().get(i).getAnswerCode());
            }
        } else {
            l_uuid.setText(question.getId());
            l_creationDate.setText(question.getCreationDate().toString());
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        answerTexts = Arrays.asList(ta_answerText_0, ta_answerText_1, ta_answerText_2, ta_answerText_3, ta_answerText_4, ta_answerText_5);
        answerCheckboxes = Arrays.asList(chb_correctAnswer_0, chb_correctAnswer_1, chb_correctAnswer_2, chb_correctAnswer_3, chb_correctAnswer_4, chb_correctAnswer_5);
        answerCodes = Arrays.asList(ta_answerCode_0, ta_answerCode_1, ta_answerCode_2, ta_answerCode_3, ta_answerCode_4, ta_answerCode_5);

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


    private void openTemporaryPDFFileForWindows(File file) throws IOException {
        try {
            if ((new File("C:\\Users\\pc arbeit\\IdeaProjects\\exam_desktop\\src\\main\\Output\\temporaryPage0.pdf")).exists()) {
                System.out.println("File ist vorhanden");
                Process process = Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler C:\\Users\\pc arbeit\\IdeaProjects\\exam_desktop\\src\\main\\Output\\temporaryPage0.pdf");
                process.waitFor();
            } else {
                System.out.println("File existirt nicht");
            }
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }

    //TODO  AWT dependensy einfügen, so  das die PDF-Datei Cross Platform erzeugt werden kann
//    private void openTemporaryPDFFile (File file) {
//        try {
//            File pdfFiel = new File("C:\\Users\\pc arbeit\\IdeaProjects\\exam_desktop\\src\\main\\Output\\temporaryPage0.pdf");
//            if (pdfFiel.exists()){
//                if (Decktop.isDesktopSupported()){
//                    Desktop.getDesktop().open(pdfFiel);
//                } else {
//                    System.out.println("Awt Desktop is not supported");
//                }
//            } else {
//                System.out.println("File is not exist");
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
public void onBackToQuestionFormClick(ActionEvent event){
    try {
        sceneManager.switchSceneToQuestionForm(event,question);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
    private void openTemporaryPDFFile(File file, ActionEvent event) throws IOException {
        try {
            PDDocument document = PDDocument.load(file);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Render the first page to an image
            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);


            // Convert the BufferedImage to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bim, "png", baos);

            // Create a JavaFX Image from the byte array
            Image fxImage = new Image(new ByteArrayInputStream(baos.toByteArray()));

            // Create an ImageView to display the JavaFX Image
            iv_pdfPreview = new ImageView(fxImage);
            iv_pdfPreview.setFitWidth(1200);
            iv_pdfPreview.setFitHeight(800);

            // Enable preserving image ratio (optional)
            iv_pdfPreview.setPreserveRatio(true);

            // Add the ImageView to a StackPane
          sceneManager.switchSceneToPdfPreview(event);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

