package com.team_kuestenflunder.exam_desktop.Utils;

import java.io.*;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.COURIER;



public class PDFHandler {

    public static void main(String[]args) throws IOException {

        PDDocument document = new PDDocument();
// ----- FONTS
        PDType0Font fontArial = PDType0Font.load(document, new File("fonts\\arial.ttf"));
        PDType0Font fontCourialNewBold = PDType0Font.load(document, new File("fonts\\courbd.ttf"));

// ----- PAGE 1
        PDPage page1 = new PDPage(PDRectangle.A4);
        document.addPage(page1);

        PDPageContentStream contentStream = new PDPageContentStream(document, page1);
    // Beschrieftung oben
        contentStream.beginText();
        contentStream.setFont(fontArial, 15);
        contentStream.setNonStrokingColor(102,102,102);
        contentStream.newLineAtOffset(130, 810);
        contentStream.showText("Java Entwickler Oracle Certified Professional SE");
        contentStream.endText();

    // Beschrieftung unten
        contentStream.beginText();
        contentStream.setFont(fontArial, 15);
        contentStream.setNonStrokingColor(102,102,102);
        contentStream.newLineAtOffset(130, 20);
        contentStream.showText("Java Entwickler Oracle Certified Professional SE");
        contentStream.endText();

    // Docu Benennung
        contentStream.beginText();
        contentStream.setFont(fontCourialNewBold, 38);
        contentStream.setNonStrokingColor(0,0,0);
        contentStream.newLineAtOffset(240, 680);
        contentStream.showText("TEST");
        contentStream.endText();

    // VORNAME field
        contentStream.beginText();
        contentStream.setFont(fontArial, 9);
        contentStream.setNonStrokingColor(102,102,102);
        contentStream.newLineAtOffset(268, 580);
        contentStream.showText("Vorname");
        contentStream.endText();

    // NACHNAME field
        contentStream.beginText();
        contentStream.setFont(fontArial, 9);
        contentStream.setNonStrokingColor(102,102,102);
        contentStream.newLineAtOffset(265, 520);
        contentStream.showText("Nachname");
        contentStream.endText();


        contentStream.close();
        document.save("TestForm.pdf");
        document.close();

        PDPage page2 = new PDPage(PDRectangle.A4);
        document.addPage(page2);

//        contentStream.moveTo(100, 600); contentStream.lineTo(300, 600); contentStream.stroke();
//        contentStream.addRect(100, 500, 200, 100); contentStream.stroke();



    }


    private void textSetzen(PDDocument doc, PDPage page) {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        contentStream.beginText();
        contentStream.setFont(fontArial, 9);
        contentStream.setNonStrokingColor(102, 102, 102);
        contentStream.newLineAtOffset(265, 520);
        contentStream.showText("Nachname");
        contentStream.endText();
    }


}
