package com.team_kuestenflunder.exam_desktop.Utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class PDFHandler {

    public  String name = "";
    public  String surname = "";




    public  void createExamPDF(int numberOfQuestions, int testDuration) {

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
                    //PDDocument document = PDDocument.load(new File("page_titelLayout.pdf"));
  // -----
                PDDocument document = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/page_titelLayout.pdf"));
                PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();

                PDField field = acroForm.getField("nameField");
                field.setValue(name);

                field= acroForm.getField("surnameField");
                field.setValue(surname);

                field= acroForm.getField("dateOfTestField");
                field.setValue(dateOfTest);

                field = acroForm.getField("numberOfQuestionsField");
                field.setValue(String.valueOf(numberOfQuestions));

                field = acroForm.getField("testDutarionField");
                field.setValue(String.valueOf(testDuration));

                field = acroForm.getField("timeOfTestStartField");
                field.setValue(startTime);

                PDDocument questionPage = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/QuestionPage.pdf"));
                PDAcroForm acroQuestionPage = questionPage.getDocumentCatalog().getAcroForm();


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



    public static void main(String[] args) {
        PDFHandler pdfHandler = new PDFHandler();
        pdfHandler.createExamPDF(56,120);
    }

}


