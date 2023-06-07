package com.team_kuestenflunder.exam_desktop.services;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.Utils.AlertMessage;
import com.team_kuestenflunder.exam_desktop.Utils.PDFHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.entity.Student;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PdfCreationPopUpService {
    private final SceneManager sceneManager = SceneManager.getInstance();
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

    public void createExamPDF(TextField tf_testTitle, TextField tf_numberOfQuestions, TextField tf_testDuration, File outputFile) throws IOException {
        Set<Question> examQuestions = getRandomExamQuestions(Integer.parseInt(tf_numberOfQuestions.getText()));
        int testDuration = Integer.parseInt(tf_testDuration.getText());
        String testTitel = tf_testTitle.getText();
        PDFHandler.createExamPDF(testTitel, examQuestions, testDuration, outputFile);
    }

    public void createIndividualPdfExams(TextField tf_testTitle, TextField tf_numberOfQuestions, TextField tf_testDuration, File directory) throws IOException {
        System.out.println(directory);
        int testDuration = Integer.parseInt(tf_testDuration.getText());
        String testTitel = tf_testTitle.getText();
        StudentViewService studentViewService = new StudentViewService();
        for (Student student : studentViewService.getStudents()) {
            Set<Question> examQuestions = getRandomExamQuestions(Integer.parseInt(tf_numberOfQuestions.getText()));

            PDFHandler.createPersonalExamTest(
                    testTitel,
                    examQuestions,
                    testDuration,
                    student.getName(),
                    student.getSurname(),
                    directory);
        }
    }
}
