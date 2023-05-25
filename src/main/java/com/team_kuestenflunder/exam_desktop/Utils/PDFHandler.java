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
        PDType0Font fontCourialNew = PDType0Font.load(document, new File("fonts\\cour.ttf"));
        PDType0Font fontCourialNewBold = PDType0Font.load(document, new File("fonts\\courbd.ttf"));


// ----- PAGE 1
        PDPage page1 = new PDPage(PDRectangle.A4);
        document.addPage(page1);

        PDPageContentStream contentStream = new PDPageContentStream(document, page1);

        contentStream.beginText();  // Beschrieftung oben
        contentStream.setFont(fontArial, 15);
        contentStream.setNonStrokingColor(102,102,102);
        contentStream.newLineAtOffset(130, 810);
        contentStream.showText("Java Entwickler Oracle Certified Professional SE");
        contentStream.endText();

        contentStream.beginText();  // Beschrieftung unten
        contentStream.setFont(fontArial, 15);
        contentStream.setNonStrokingColor(102,102,102);
        contentStream.newLineAtOffset(130, 20);
        contentStream.showText("Java Entwickler Oracle Certified Professional SE");
        contentStream.endText();

        contentStream.beginText();   // Docu Benennung
        contentStream.setFont(fontCourialNewBold, 38);
        contentStream.setNonStrokingColor(0,0,0);
        contentStream.newLineAtOffset(240, 680);
        contentStream.showText("TEST");
        contentStream.endText();

    // VORNAME field to fill  TODO  TextField not make
        PDAcroForm acroForm1 = new PDAcroForm(document);
        document.getDocumentCatalog().setAcroForm(acroForm1);

        PDTextField nameField = new PDTextField(acroForm1);
        nameField.setPartialName("setPartialName");
        acroForm1.getFields().add(nameField);
        nameField.setValue("setValue");
        nameField.setMaxLen(20);

        page1.getAnnotations().add(nameField.getWidget());

        PDAnnotationWidget widget = nameField.getWidgets().get(0);
        PDRectangle rect = new PDRectangle(250, 600, 200, 30);
        widget.setRectangle(rect);
        widget.setPage(page1);
        widget.setPrinted(true);
        page1.getAnnotations().add(widget);



//        nameField.setValue("Sample Field");
//        nameField.setDefaultAppearance("/Helv 40 Tf");
//        nameField.setDefaultValue("DefaultValue");
//        nameField.setFileSelect(true);


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

    // Hinweis 1
        contentStream.beginText();
        contentStream.setFont(fontCourialNew, 15);
        contentStream.setNonStrokingColor(0,0,0);
        contentStream.newLineAtOffset(100, 320);
        contentStream.showText("Anzahl der Prüfungsfragen");
        contentStream.endText();

    // Hinweis 2
        contentStream.beginText();
        contentStream.setFont(fontCourialNew, 15);
        contentStream.setNonStrokingColor(0,0,0);
        contentStream.newLineAtOffset(100, 270);
        contentStream.showText("Prüfungsdauer in minuten");
        contentStream.endText();



        contentStream.close();



// ----- PAGE 2
        PDPage page2 = new PDPage(PDRectangle.A4);
        document.addPage(page2);
        PDPageContentStream contentStream2 = new PDPageContentStream(document, page2);


        contentStream2.close();



// ----- END
        document.save("TestForm.pdf");
        document.close();

    }





//  TODO  refactor this method
//        setText(document,page2, "ETWAS", fontArial, 20, 100, 15, 48, 250, 100);

    private static PDPageContentStream setText ( PDDocument doc, PDPage page, String text,
                           PDType0Font font, float fontSize, int r, int g, int b, float tx, float ty)
            throws IOException {
        PDPageContentStream contStream = new PDPageContentStream(doc, page);
        contStream.beginText();
        contStream.setFont(font, fontSize);
        contStream.setNonStrokingColor(r, g, b);
        contStream.newLineAtOffset(tx, ty);
        contStream.showText(text);
        contStream.endText();
        return contStream;
    }




}
