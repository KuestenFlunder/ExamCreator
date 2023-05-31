package com.team_kuestenflunder.exam_desktop.Utils;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.services.PdfCreationPopUpService;
import javafx.event.ActionEvent;
import javafx.scene.input.SwipeEvent;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;
import org.apache.pdfbox.pdmodel.interactive.action.PDAnnotationAdditionalActions;
import org.apache.pdfbox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class PDFHandler {

    @Inject
    PdfCreationPopUpService popUpService;

    SceneManager sceneManager = SceneManager.getInstance();
    private String name, surname;
    private int qsCounter = 0;


    public PDFHandler(PdfCreationPopUpService popUpService) {
        this.popUpService = popUpService;
    }


    public void createExamPDF(int numberOfQuestions, int testDuration/*,File fileToSaveExamJson*/) {
        Set<Question> examQuestions = popUpService.getRandomExamQuestions(numberOfQuestions);


// ---- Date and Time of Test
        LocalDateTime dateNow = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.GERMAN);
        String dateOfTest = dateNow.format(formatterDate);

        LocalDateTime testStartTime = LocalDateTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH : mm");
        String startTime = testStartTime.format(formatterTime);

// ---- Test to PDF
        PDFMergerUtility pdfTest = new PDFMergerUtility();
        try {
            // Page 1
            PDDocument titelPage = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/page_titelLayout.pdf"));
            PDAcroForm acroFormTitelPage = titelPage.getDocumentCatalog().getAcroForm();
            setValueToField(acroFormTitelPage, "nameField", name);
            PDTextField requiredNameField = (PDTextField) acroFormTitelPage.getField("nameField");
            requiredNameField.setRequired(true);
            setValueToField(acroFormTitelPage, "surnameField", surname);
            PDTextField requiredSurnameField = (PDTextField) acroFormTitelPage.getField("surnameField");
            requiredSurnameField.setRequired(true);
            setValueToField(acroFormTitelPage, "dateOfTestField", dateOfTest);
            setValueToField(acroFormTitelPage, "numberOfQuestionsField", String.valueOf(numberOfQuestions));
            setValueToField(acroFormTitelPage, "testDutarionField", String.valueOf(testDuration));//TODO correct Typo
            setValueToField(acroFormTitelPage, "timeOfTestStartField", startTime);
            titelPage.save("src/main/Output/TitelPage.pdf");
            titelPage.close();
            pdfTest.addSource("src/main/Output/TitelPage.pdf");

            // page from 2 to (numberOfQuestions + 1)
            for (Question question : examQuestions) {
                PDDocument questionPage = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/QuestionLayout.pdf"));
                PDAcroForm acroQuestionPage = questionPage.getDocumentCatalog().getAcroForm();
                qsCounter++;
                setValueToField(acroQuestionPage, "QuestionID_Field", question.getId());
                setValueToField(acroQuestionPage, "questionNumberField", String.valueOf(qsCounter));
                setValueToField(acroQuestionPage, "numberOfQuestionsField", String.valueOf(numberOfQuestions));
                setValueToField(acroQuestionPage, "QuestionTextField", question.getQuestionText());
                setValueToField(acroQuestionPage, "QuestionCodeField", question.getQuestionCode());
                setValueToField(acroQuestionPage, "correctAnswersField", String.valueOf(question.getAnswers().getCorrectAnswers()));
                // Answer-Fields füllen
                for (int i = 0; i < question.getAnswers().getAnswerList().size(); i++) {
                    String answerFieldName = "AnswerTextField_" + i;
                    setValueToField(acroQuestionPage, answerFieldName, String.valueOf(question.getAnswers().getAnswerList().get(i).getAnswerText()));
                    String answerCodeFieldName = "AnswerCodeField_" + i;
                    setValueToField(acroQuestionPage, answerCodeFieldName, String.valueOf(question.getAnswers().getAnswerList().get(i).getAnswerDescription())); //TODO 'Description' durch 'AnswerCode' ersetzen
                    String checkboxFieldName = "CorrectAnswerBox_" + i;
                    //hidden checkbox to keep the correct answers
                    PDCheckBox checkbox = (PDCheckBox) acroQuestionPage.getField(checkboxFieldName);
                    if (question.getAnswers().getAnswerList().get(i).isCorrectAnswer()) {
                        checkbox.setValue(checkbox.getOnValue());
                    }
                    // hide the checkbox content
                    PDAnnotationWidget widget = checkbox.getWidgets().get(0);
                    widget.setHidden(true);
                }
                String pathName = "src/main/Output/qsPage" + qsCounter + ".pdf";
                questionPage.save(pathName);
                questionPage.close();
                pdfTest.addSource(pathName);
            }

            // last page
            PDDocument lastPage = PDDocument.load(
                    new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/LastPageLayout.pdf"));
            PDAcroForm acroFormLastPage = lastPage
                    .getDocumentCatalog()
                    .getAcroForm();
            setValueToField(acroFormLastPage, "ResultTextField", "BESTANDEN");
            setValueToField(acroFormLastPage, "numberOfQuestionsField", String.valueOf(numberOfQuestions));
            setValueToField(acroFormLastPage, "correctAnswersAmountField", "?");

//????????? TODO Funktion in JavaScript
            PDPushButton evaluateButton = (PDPushButton) acroFormLastPage.getField("evaluateTheTest_Button");
            PDActionJavaScript button_JavaScript = new PDActionJavaScript();
            String absoluteFilePath = "/Users/jan-hendrykpassler/IdeaProjects/exam_desktop/src/main/Output/Test.pdf";

            String javascriptCode = "app.launchURL('mailto:kuestenflunder@gmail.com?subject=');";

            button_JavaScript.setAction(javascriptCode);

            PDAnnotationWidget evaluateButtonWidget = evaluateButton.getWidgets().get(0);
            evaluateButtonWidget.setAction(button_JavaScript);
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


    private void setValueToField(PDAcroForm pdAcroForm, String fieldName, String value) throws IOException {
        PDField field = pdAcroForm.getField(fieldName);
        field.setValue(value);
    }


    public void pdfEvaluation(List<PDDocument> exams){
        //Find and compare questions from PDF with Questions from corresponding List.
        // Alternative: use the hidden correct Answer filed to evaluate --> seems to be more easy, field already exists
    }

}


