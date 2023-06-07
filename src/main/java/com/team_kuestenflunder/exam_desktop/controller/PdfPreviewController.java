package com.team_kuestenflunder.exam_desktop.controller;

import com.team_kuestenflunder.exam_desktop.SceneManager;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PdfPreviewController implements Initializable {
    SceneManager sceneManager = SceneManager.getInstance();

    @FXML
    ImageView iv_pdfPreview;
    @FXML
    Button bt_backToQuestionFrom;
    @FXML
    AnchorPane anchorPane;
    //! do not remove its used in SceneManagerClass
    private File pdfFile;
    private Question tempraryQuestion;


    public void setPdfFile(File pdfFile, Question tempraryQuestion) throws IOException {
        this.pdfFile = pdfFile;
        this.tempraryQuestion = tempraryQuestion;

        PDDocument document = PDDocument.load(pdfFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        // Render the first page to an image
        BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);

        // Convert the BufferedImage to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bim, "png", baos);

        // Create a JavaFX Image from the byte array
        Image fxImage = new Image(new ByteArrayInputStream(baos.toByteArray()));

        // Create an ImageView to display the JavaFX Image
        iv_pdfPreview.setImage(fxImage);
        iv_pdfPreview.fitWidthProperty().bind(anchorPane.widthProperty());
        iv_pdfPreview.fitHeightProperty().bind(anchorPane.heightProperty());

    }

    public void onBackToQuestionFormClick(ActionEvent event) {
        try {
            sceneManager.switchSceneToQuestionForm(event, tempraryQuestion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
