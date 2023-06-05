package com.team_kuestenflunder.exam_desktop.services;


import com.team_kuestenflunder.exam_desktop.Utils.AlertMessage;
import com.team_kuestenflunder.exam_desktop.entity.ExamResult;
import com.team_kuestenflunder.exam_desktop.entity.ExamValues;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import javafx.scene.control.Alert;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExamEvaluationViewService {

    QuestionRepositoryImpl questionRepository;

//TODO Add Injection
    public ExamEvaluationViewService() {
        this.questionRepository = new QuestionRepositoryImpl();
    }

    public ExamResult evaluateExam(ExamValues examValues) {
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
                AlertMessage.alertMessage(Alert.AlertType.ERROR, "unzulässiges Dokument", "Bitte übergeben Sie ein gültiges TestPDF");
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0,00");
        double percentageOfCorrectAnswers = (double) (100 * counter) / examValues.getNumberOfQuestions();
        String percent = decimalFormat.format(percentageOfCorrectAnswers);

        String testResult = percentageOfCorrectAnswers >= 65 ? "Bestanden" : "Nicht bestanden";

        return new ExamResult (
                examValues.getStudentName(),
                examValues.getStudentSurname(),
                examValues.getDateOfTest(),
                examValues.getNumberOfQuestions(),
                percent,
                testResult,
                resultList
                );
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





}
