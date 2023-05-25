package com.team_kuestenflunder.exam_desktop.Utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;


public class PDFHandler_v1 {

    public static String name;
    public static String surname;
    private static String questionsSum = "35";
    private static String testDuration = "90";


    public static void main(String[] args) {
            try {
                PDDocument document = PDDocument.load(new File("page_titelLayot.pdf"));
                PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();


                PDField field = acroForm.getField("nameFild");
                field.setValue(name);

                field = acroForm.getField("surnameFild");
                field.setValue(surname);

                field = acroForm.getField("AnzahlFragenFild");
                field.setValue(questionsSum);

                field = acroForm.getField("testDutarionFild");
                field.setValue(testDuration);


                PDPage page2 = new PDPage();
                document.addPage(page2);



                document.save("TestForm.pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}


