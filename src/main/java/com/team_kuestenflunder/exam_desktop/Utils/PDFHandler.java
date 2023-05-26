package com.team_kuestenflunder.exam_desktop.Utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;


public class PDFHandler {


    public  String name = "Vorname";
    public  String surname;



    public  void createExamPDF(int numberOfQuestions, int testDuration) {
        //Set<Question> examQuestions = questionViewService.getRandomQuestions(numberOfQuestions)

            try {
                    //PDDocument document = PDDocument.load(new File("page_titelLayout.pdf"));


                PDDocument document = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/page_titelLayout.pdf"));

                PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();

                PDField field = acroForm.getField("nameFild");
                field.setValue(name);

                field= acroForm.getField("surnameFild");
                field.setValue(surname);

                field = acroForm.getField("AnzahlFragenFild");
                field.setValue(String.valueOf(numberOfQuestions));

                field = acroForm.getField("testDutarionFild");
                field.setValue(String.valueOf( testDuration));

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
        pdfHandler.createExamPDF(2,23);
    }

}


