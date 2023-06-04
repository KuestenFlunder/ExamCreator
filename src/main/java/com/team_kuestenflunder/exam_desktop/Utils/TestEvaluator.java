package com.team_kuestenflunder.exam_desktop.Utils;

import com.team_kuestenflunder.exam_desktop.entity.ExamValues;
import javafx.scene.control.Alert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TestEvaluator {

    //--------------------- MAIN ------------------
    public static void main(String[] args) {
        PDFHandler handler = new PDFHandler();
        ExamValues examValues = handler.getValuesFromTest(new File("src/main/Output/Test.pdf"));
        TestEvaluator estimator = new TestEvaluator();
        estimator.evaluateExam(examValues);

    }

    public String evaluateExam(ExamValues examValues) {
        int counter = 0;
        List<String> resultList = new ArrayList<>();

        for (int i = 1; i <= examValues.getNumberOfQuestions(); i++) {
            String keyID = "QuestionID_Field_" + i + 0;
            if (examValues.getUUID_Map().containsKey(keyID)) {
                if (evaluateSingleQuestion(examValues, i)) {
                    resultList.add(i + ". Frage : richtig");
                    counter++;
                } else {
                    resultList.add(i + ". Frage : falsch");
                }
            } else {
                AlertMessage.alertMessage(Alert.AlertType.ERROR, "unzulässiges Dokument", "Bitte übergeben sie ein gültiges TestPDF");
            }
        }
        double percentageOfcorrectAnswers = (double) (100 * counter) / examValues.getNumberOfQuestions();
        String testResult = percentageOfcorrectAnswers >= 65 ? "bestanden" : "nicht bestanden";
        printList(resultList);

        return testResult;
    }

    private boolean evaluateSingleQuestion(ExamValues examValues, int i) {
        int counter = 0;
        for (int j = 0; j < 6; j++) {
            String studentAnswer = examValues.getAnswer_Map().get("AnswerBox_" + i  + j);
            String rightAnswer = examValues.getAnswer_Map().get("CorrectAnswerBox_" + i  + j);
            if (studentAnswer.equals(rightAnswer)) {
                counter++;
            }
        }
        return counter == 6;
    }

    private void printList(List<String> results) {
        for (String result : results) {
            System.out.println(result);
        }
    }

}
