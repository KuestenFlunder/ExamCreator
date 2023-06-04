package com.team_kuestenflunder.exam_desktop.services;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.Utils.AlertMessage;
import com.team_kuestenflunder.exam_desktop.Utils.PDFHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PdfCreationPopUpService {
    QuestionRepositoryImpl questionRepository;

    @Inject
    public PdfCreationPopUpService(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = questionRepository;
    }


    public Set<Question> getRandomExamQuestions(int requestedNumberOfQuestions) {
//requested number must be lower than getQuestions.size
        Set<Question> examQuestions = new HashSet<>();
        Random random = new Random();
        if (requestedNumberOfQuestions > questionRepository.getQuestions().size()) {
            AlertMessage.alertMessage(
                    Alert.AlertType.ERROR,
                    "Nicht genügend Fragen vorhanden",
                    "Leider sind nicht genügend Fragen im Fragen-pool. Bitte stellen sie sicher, dass Sie den richtigen Fragen-pool verwenden,\n oder reduzieren Sie die Anzahl der Fragen im Test.");
            return null;
        } else {
            while (examQuestions.size() < requestedNumberOfQuestions) {
                int index = random.nextInt(questionRepository.getQuestions().size());
                examQuestions.add(questionRepository.getQuestions().get(index));
            }
            return examQuestions;
        }
    }

    public void createExamPDF(TextField tf_testTitle, TextField tf_numberOfQuestions, TextField tf_testDuration) {
        Set<Question> examQuestions = getRandomExamQuestions(Integer.parseInt(tf_numberOfQuestions.getText()));
        int testDuration = Integer.parseInt(tf_testDuration.getText());
        String testTitel = tf_testTitle.getText();
        //TODO should be refactored and pass in the random Questions to createPDF as parameter.
        //TODO Add a filepath to choose the right place to save the Exam to.

        PDFHandler.createExamPDF(testTitel,examQuestions, testDuration);
    }


}
