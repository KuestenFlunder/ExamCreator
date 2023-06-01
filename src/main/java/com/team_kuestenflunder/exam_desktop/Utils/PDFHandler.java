package com.team_kuestenflunder.exam_desktop.Utils;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.services.PdfCreationPopUpService;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLine;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.*;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class PDFHandler {

    @Inject
    PdfCreationPopUpService popUpService;

    SceneManager sceneManager = SceneManager.getInstance();


    public PDFHandler(PdfCreationPopUpService popUpService) {
        this.popUpService = popUpService;
    }


    private static class Student {
        String studentName;
        String percentageRatioFromTest;
        String testResult;
    }


    public void createExamPDF(String testTitel, int numberOfQuestions, int testDuration/*,File fileToSaveExamJson*/) {
        Set<Question> examQuestions = popUpService.getRandomExamQuestions(numberOfQuestions);
        int qsCounter = 0;

// ---- Date and Time of Test //TODO entscheiden ob notwendig ist
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
            PDDocument titelPage = PDDocument.load(new File("src/main/resources/com/team_kuestenflunder/exam_desktop/templates/TitelPageLayout.pdf"));
            PDAcroForm acroFormTitelPage = titelPage.getDocumentCatalog().getAcroForm();

            PDTextField requiredNameField = (PDTextField) acroFormTitelPage.getField("nameField");
                        requiredNameField.setRequired(true);
            PDTextField requiredSurnameField = (PDTextField) acroFormTitelPage.getField("surnameField");
                        requiredSurnameField.setRequired(true);

            setValueToField(acroFormTitelPage, "courseNameField", "Java Entwickler Oracle Certified Professional SE");
            setValueToField(acroFormTitelPage, "testTitelField", testTitel);
            setValueToField(acroFormTitelPage, "dateOfTestField", dateOfTest);
            setValueToField(acroFormTitelPage, "numberOfQuestionsField", String.valueOf(numberOfQuestions));
            setValueToField(acroFormTitelPage, "testDutarionField", String.valueOf(testDuration));//TODO correct Typo
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


// ES FUNKTIONIERT NICHT
    public static List<Student> evaluationOf_SeveralTests (List<File> files){
        List<Student> studentList = new ArrayList<>(files.size());
        Student student = new Student();
        try {
            for (File pdfFile : files) {
                DecimalFormat decimalFormat = new DecimalFormat ("#0.00");
                student.studentName = getStudentNameFromFile(pdfFile);
                student.percentageRatioFromTest = decimalFormat.format(evaluationOf_PDFTest(pdfFile));
                student.testResult = Double.parseDouble(student.percentageRatioFromTest) >= 65 ? "Bestanden" : "Nicht bestanden";
                studentList.add(student);
            }
        }catch (IOException ioE){
            ioE.printStackTrace();
        }
        System.out.println(studentList);
        return studentList;
    }


    private static Double evaluationOf_PDFTest(File pdfFile) throws IOException {
        PDDocument pdfDocument = PDDocument.load(pdfFile);
        int counterOfTrueAnswers = 0;
        int questionsAmount = pdfDocument.getNumberOfPages() - 2;
            for (int i = 1; i <= questionsAmount; i++){
//                if (evaluationOf_QuestionPage(pdfDocument, pdfDocument.getPage(i))) {
                    counterOfTrueAnswers++;
                }
        double ratioOfCorrectAnswers = (double) (100 * counterOfTrueAnswers) / questionsAmount;
        return null; //ratioOfCorrectAnswers;
    }

// da alle interaktive Felder im Dockument zu gleicher AcroForm gehören, über Seiten-Nummer kann nicht an die Felder greifen
    private static boolean evaluationOf_QuestionPage(PDDocument document, PDPage page) throws IOException{
        int counter = 0;
        PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
        if (acroForm != null){
            for (int i = 0; i < 6; i++){
                PDCheckBox studentResponse = (PDCheckBox) acroForm.getField("AnswerBox_" + i);
                System.out.println(i + "studentResponse = " + studentResponse );
                PDCheckBox answerKey = (PDCheckBox) acroForm.getField("CorrectAnswerBox_" + i);
                String value = studentResponse.getValue();
                String keyValue = answerKey.getValue();
                if (value.equals(keyValue))  counter++;
            }
        }
        return counter == 6 ? true : false;
    }


    private static String getStudentNameFromFile(File pdfFile) throws IOException, RuntimeException{
        PDDocument document = PDDocument.load(pdfFile);
        PDAcroForm acroFormTitelPage = document.getDocumentCatalog().getAcroForm();
        PDTextField nameField = (PDTextField)acroFormTitelPage.getField("nameField");
        String name = nameField.getValue();
        PDTextField surnameField = (PDTextField)acroFormTitelPage.getField("surnameField");
        String surname = surnameField.getValue();
        return name +" "+ surname;
    }



//---------------------------- zum testen --------------------------------
    public static void main(String[] args) {

        try {
            processPDF();
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void processPDF() throws IOException {
        PDDocument document = PDDocument.load(new File("src/main/Output/Test.pdf"));
        PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
        List<PDField> fields = acroForm.getFields();


        for (PDField field : fields) {
           //System.out.println("field = " + field);
            //Title all +32 fields
            if (field.getFullyQualifiedName().equals("dummyFieldName160")) {
                String value = field.getValueAsString();
                System.out.println("Value of QuestionTextField: " + value);
            }
        }

        document.close();
    }

    }


