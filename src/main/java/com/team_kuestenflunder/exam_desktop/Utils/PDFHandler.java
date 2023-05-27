package com.team_kuestenflunder.exam_desktop.Utils;

import com.team_kuestenflunder.exam_desktop.entity.Question;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;


public class PDFHandler {

    Set<Question> setOfSelectedQuestions;
    public String name = "";
    public String surname = "";
    private int qsCounter = 0;


    public PDFHandler(){
    }

    public PDFHandler(Set<Question> setOfSelectedQuestions) {
        this.setOfSelectedQuestions = setOfSelectedQuestions;
    }


    public void createExamPDF(int numberOfQuestions, int testDuration) {

//        Set<Question> examQuestions = questionViewService.getRandomQuestions(numberOfQuestions)


// ---- Date and Time of Test
        LocalDateTime dateNow = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.GERMAN);
        String dateOfTest = dateNow.format(formatterDate);

        LocalDateTime testStartTime = LocalDateTime.now();
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH : mm");
        String startTime = testStartTime.format(formatterTime);


// ---- Test to PDF
        try {
            // Seite 1
            PDDocument document = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/page_titelLayout.pdf"));
            PDAcroForm acroFormTitelPage = document.getDocumentCatalog().getAcroForm();
            setValueToField(acroFormTitelPage, "nameField", name);
            setValueToField(acroFormTitelPage, "surnameField", surname);
            setValueToField(acroFormTitelPage, "dateOfTestField", dateOfTest);
            setValueToField(acroFormTitelPage, "numberOfQuestionsField", String.valueOf(numberOfQuestions));
            setValueToField(acroFormTitelPage, "testDutarionField", String.valueOf(testDuration));
            setValueToField(acroFormTitelPage, "timeOfTestStartField", startTime);

            // Seiten 2 bis n = 1 + numberOfQuestions
            for (Question question : setOfSelectedQuestions) {
                PDDocument questionPage = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/QuestionPage.pdf"));
                PDAcroForm acroQuestionPage = questionPage.getDocumentCatalog().getAcroForm();
                qsCounter++;
                setValueToField(acroQuestionPage, "questionNumberField", String.valueOf(qsCounter));
                setValueToField(acroQuestionPage, "numberOfQuestionsField", String.valueOf(numberOfQuestions));
                setValueToField(acroQuestionPage, "QuestionTextField", question.getQuestionText());
                setValueToField(acroQuestionPage, "QuestionCodeField", question.getQuestionCode());
                setValueToField(acroQuestionPage, "correctAnswersField", question.getAnswers().getCorrectAnswers();

                for (int i = 0; i < 7; i++) {
                    setValueToField(acroQuestionPage, "AnswerTextField_1", question.getAnswers().getAnswerList());
                }



            }



             /*   // for list of questions
                PDPage page2*//*i*//* = questionPage.getPage(0);
                PDField fieldPage2*//*i*//* = acroQuestionPage.getField("questonTitle");
                fieldPage2.setReadOnly(true);
                fieldPage2*//*i*//*.setValue(*//*DATENBANK.*//*questionTitelFromDB);

                document.addPage(page2*//*i*//*);
                //loop  ende
*/
            // PDDocument last page add


            document.save("src/main/Output/TestForm.pdf");
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setValueToField(PDAcroForm pdAcroForm,String fieldName, String value) throws IOException {
        PDField field = pdAcroForm.getField(fieldName);
        field.setValue(value);
    }



    public static void main(String[] args) {
        PDFHandler pdfHandler = new PDFHandler();
        pdfHandler.createExamPDF(56,120);
    }

}


