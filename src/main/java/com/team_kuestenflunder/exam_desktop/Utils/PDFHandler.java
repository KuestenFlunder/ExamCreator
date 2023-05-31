package com.team_kuestenflunder.exam_desktop.Utils;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.services.PdfCreationPopUpService;
import javafx.fxml.Initializable;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;
import org.apache.pdfbox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDPushButton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class PDFHandler {

    @Inject
    PdfCreationPopUpService popUpService;

    private String name, surname;
    private int qsCounter = 0;


    public PDFHandler(PdfCreationPopUpService popUpService) {
        this.popUpService = popUpService;
    }


    public void createExamPDF(int numberOfQuestions, int testDuration) {
        Set<Question> examQuestions = popUpService.getRandomExamQuestions(numberOfQuestions);

// ---- Date and Time of Test
        LocalDateTime dateNow = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.GERMAN);
        String dateOfTest = dateNow.format(formatterDate);

        LocalDateTime testStartTime = LocalDateTime.now(); //TODO Zeitanzeige in den Test integrieren
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH : mm");
        String startTime = testStartTime.format(formatterTime);

// ---- Test to PDF
        PDFMergerUtility pdfTest = new PDFMergerUtility();
        try {
            // Page 1
            PDDocument titelPage = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/page_titelLayout.pdf"));
            PDAcroForm acroFormTitelPage = titelPage.getDocumentCatalog().getAcroForm();
            setValueToField(acroFormTitelPage, "nameField", name);
            setValueToField(acroFormTitelPage, "surnameField", surname);
            setValueToField(acroFormTitelPage, "dateOfTestField", dateOfTest);
            setValueToField(acroFormTitelPage, "numberOfQuestionsField", String.valueOf(numberOfQuestions));
            setValueToField(acroFormTitelPage, "testDutarionField", String.valueOf(testDuration));
            setValueToField(acroFormTitelPage, "timeOfTestStartField", startTime);
            titelPage.save("src/main/Output/TitelPage.pdf");
            titelPage.close();
            pdfTest.addSource("src/main/Output/TitelPage.pdf");

            // page from 2 to (numberOfQuestions + 1)
            for (Question question : examQuestions) {
                PDDocument questionPage = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/QuestionLayout.pdf"));
                PDAcroForm acroQuestionPage = questionPage.getDocumentCatalog().getAcroForm();
                qsCounter++;
                setValueToField(acroQuestionPage, "questionNumberField", String.valueOf(qsCounter));
                setValueToField(acroQuestionPage, "numberOfQuestionsField", String.valueOf(numberOfQuestions));
                setValueToField(acroQuestionPage, "QuestionTextField", question.getQuestionText());
                setValueToField(acroQuestionPage, "QuestionCodeField", question.getQuestionCode());
                setValueToField(acroQuestionPage, "correctAnswersField", String.valueOf(question.getAnswers().getCorrectAnswers()));
                // Answer-Fields füllen
                for (int i = 1; i < question.getAnswers().getAnswerList().size(); i++) {
                    String answerFieldName = "AnswerTextField_" + i;
                        setValueToField(acroQuestionPage, answerFieldName, String.valueOf(question.getAnswers().getAnswerList().get(i).getAnswerText()));
                    String answerCodeFieldName = "AnswerCodeField_" + i;
                        setValueToField(acroQuestionPage, answerCodeFieldName, String.valueOf(question.getAnswers().getAnswerList().get(i).getAnswerDescription())); //TODO 'Description' durch 'AnswerCode' ersetzen
                    String checkboxFieldName = "checkboxFieldName_" + i;
                        PDCheckBox checkbox = (PDCheckBox) acroQuestionPage.getField(checkboxFieldName);
                            if (question.getAnswers().getAnswerList().get(i).isCorrectAnswer()) {
                                checkbox.setValue(checkbox.getOnValue());
                            }
//                        PDAnnotationWidget widget = checkbox.getWidgets().get(0);
//                            widget.setHidden(true);
//                            acroQuestionPage.refreshAppearances();
                }
                String pathName = "src/main/Output/qsPage" + qsCounter + ".pdf";
                questionPage.save(pathName);
                questionPage.close();
                pdfTest.addSource(pathName);
            }

            // last page
            PDDocument lastPage = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/LastPageLayout.pdf"));
            PDAcroForm acroFormLastPage = lastPage.getDocumentCatalog().getAcroForm();
            setValueToField(acroFormLastPage, "ResultTextField", "BESTANDEN");
            setValueToField(acroFormLastPage, "numberOfQuestionsField", String.valueOf(numberOfQuestions));
            setValueToField(acroFormLastPage, "correctAnswersAmountField", "?");

//????????? TODO Funktion in JAvaScript
            PDPushButton evaluateButton = (PDPushButton)acroFormLastPage.getField("evaluateTheTest_Button");
            PDFormFieldAdditionalActions action = new PDFormFieldAdditionalActions();
//            evaluateButton.setActions(action.setF());
//?????????
            lastPage.save("src/main/Output/LastPage.pdf");
            lastPage.close();
            pdfTest.addSource("src/main/Output/LastPage.pdf");

            // PDF zusammengefügt
            pdfTest.setDestinationFileName("src/main/Output/Test.pdf");
            pdfTest.mergeDocuments(null);
            System.out.println("PDF-Dokument erstellt");

            // Output-Ordner bereinigen
            File titelPageFile = new File("src/main/Output/TitelPage.pdf");
            titelPageFile.delete();
            File lastPageFile = new File("src/main/Output/LastPage.pdf");
            lastPageFile.delete();
            for (int i = 1; i <= examQuestions.size(); i++) {
                File questionFile = new File("src/main/Output/qsPage" + i + ".pdf");
                questionFile.delete();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Dokument ist erstellt");
    }


    private void setValueToField(PDAcroForm pdAcroForm,String fieldName, String value) throws IOException {
        PDField field = pdAcroForm.getField(fieldName);
        field.setValue(value);
    }


    public boolean evaluateTheTest (PDAcroForm acroform) {
        boolean testResult = false;



        return testResult;
    }


}


