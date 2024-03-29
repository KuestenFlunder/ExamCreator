package com.team_kuestenflunder.exam_desktop;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.team_kuestenflunder.exam_desktop.controller.PdfCreationPopUpController;
import com.team_kuestenflunder.exam_desktop.controller.PdfPreviewController;
import com.team_kuestenflunder.exam_desktop.controller.QuestionFormController;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.moduls.DIConfigModule;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * A SceneManager utility class for managing JavaFX scenes and stages, file choosing dialog and more.
 * Uses Singleton pattern to ensure only a single instance of SceneManager is instantiated.
 */
public class SceneManager {
    private static SceneManager instance;
    private static Injector injector = Guice.createInjector(new DIConfigModule());
    private static Parent root;
    private static Scene scene;
    private static Stage stage;


    private SceneManager() {
    }

    /**
     * Returns the singleton instance of the SceneManager class.
     *
     * @return the SceneManager instance.
     */
    public static SceneManager getInstance() {
        if (instance == null) return new SceneManager();
        return instance;
    }

    /**
     * Switches the current scene to the question form scene and populates it with the given question data.
     *
     * @param event    the action event that triggered the scene switch.
     * @param question the question to populate the form with.
     * @throws IOException if there's an error loading the FXML file.
     */
    public static void switchSceneToQuestionForm(ActionEvent event, Question question) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("questionForm.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        root = fxmlLoader.load();
        QuestionFormController questionFormController = fxmlLoader.getController();
        if (((Button) event.getSource()).getId().equals("bt_fromPreviewBackToQuestionFrom")) {
            questionFormController.setQuestionFromPreview(question);
        }
        if (((Button) event.getSource()).getId().equals("bt_updateQuestion") | ((Button) event.getSource()).getId().equals("bt_newQuestion")) {
            questionFormController.setQuestionData(question);
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Fragen Editor");
        stage.show();
    }


    public static void switchSceneToExamValidationView(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("examEvaluationView.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        root = fxmlLoader.load();
        stage = (Stage) ((MenuItem) event.getTarget())
                .getParentPopup()
                .getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Prüfungsauswertung");
        stage.show();
    }

    public static void switchSceneToStudentsView(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("studentAdministrationView.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        root = fxmlLoader.load();
        stage = (Stage) ((MenuItem) event.getTarget())
                .getParentPopup()
                .getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Studenten Verwaltung");
        stage.show();
    }


    /**
     * Switches the current scene to the question view scene.
     *
     * @param event the action event that triggered the scene switch.
     * @throws IOException if there's an error loading the FXML file.
     */
    public static void switchSceneToQuestionView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("questionsView.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Fragen Liste");
        stage.show();
    }

    public static void switchSceneToPdfPreview(ActionEvent event, File pdfFile, Question tempraryQuestion) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("pdfPreview.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        root = fxmlLoader.load();
        PdfPreviewController pdfPreviewController = fxmlLoader.getController();
        pdfPreviewController.setPdfFile(pdfFile, tempraryQuestion);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pdf Vorschau");
        stage.show();

    }

    /**
     * Adds a modal popup for PDF creation.
     *
     * @param event the action event that triggered the popup.
     * @throws IOException if there's an error loading the FXML file.
     */
    public static void addPdfCreationPopUp(Event event) throws IOException {
        System.out.println((event.getSource() instanceof Button));
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("pdfCreationPopUp.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        PdfCreationPopUpController pdfCreationPopUpController = fxmlLoader.getController();
        if (event.getSource() instanceof Button) {
            pdfCreationPopUpController.setCallerID(((Button) (event.getSource())).getId());
        }
        if (event.getSource() instanceof MenuItem) {
            pdfCreationPopUpController.setCallerID(((MenuItem) (event.getSource())).getId());
        }

        if (event.getSource().getClass().toString().equals("class javafx.scene.control.MenuItem")) {
            stage.initOwner(((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow());
        }

        if (event.getSource().getClass().toString().equals("class javafx.scene.control.Button")) {
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Opens a file chooser dialog for selecting multiple JSON files.
     *
     * @param event the action event that triggered the file chooser.
     * @return a list of the selected files.
     */
    public static List<java.io.File> addFileChooserDialogMultiple(Event event, String filterDescription, String FilterExtensions) {
        Stage stage = new Stage();
        stage.initOwner(((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Json wählen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(filterDescription, FilterExtensions));
        return fileChooser.showOpenMultipleDialog(stage);
    }

    /**
     * Opens a file chooser dialog for selecting a single JSON file.
     *
     * @param event the action event that triggered the file chooser.
     * @return the selected file.
     */
    public static File addFileChooserDialogSingle(Event event, String filterDescription, String FilterExtensions) {
        Stage stage = new Stage();
        stage.initOwner(((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Json wählen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(filterDescription, FilterExtensions));
        return fileChooser.showOpenDialog(stage);
    }

    /**
     * Opens a file save dialog for choosing where to save a JSON file.
     *
     * @param event the event that triggered the file save dialog.
     * @return the selected file.
     */

    public static File addFileSaveDialog(Event event, String filterDescription, String filterExtension) {
        Stage stage = new Stage();
        stage.initOwner(((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Datei speichern");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(filterDescription, filterExtension));

        return fileChooser.showSaveDialog(stage);
    }

    public static File addFileSaveDialogFromButton(ActionEvent event, String filterDescription, String filterExtension) {
        Stage stage = new Stage();
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Datei speichern");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(filterDescription, filterExtension));

        return fileChooser.showSaveDialog(stage);
    }


    public static File chooseDirectory(ActionEvent event) {
        Stage stage = new Stage();
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Verzeichnis auswählen");

        return directoryChooser.showDialog(stage);
    }


    public static File addNewFileSaveDialog(Event event) {
        Stage stage = new Stage();
        stage.initOwner(((MenuItem) event.getTarget()).getParentPopup().getOwnerWindow());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wählen sie den Ordner und geben sie den Dateinamen an");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));

        File file = fileChooser.showSaveDialog(stage);

        // If a file is chosen and it's not null
        if (file != null) {
            try {
                if (file.createNewFile()) {
                    // Write an empty JSON object to the file
                    try (PrintWriter out = new PrintWriter(file)) {
                        out.write("[ ]");
                    }
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }
        return file;
    }

    /**
     * Initializes the first view of the application, which is the question view.
     *
     * @return a new scene containing the question view.
     * @throws IOException if there's an error loading the FXML file.
     */
    public static Scene getQuestionView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource("questionsView.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        Parent formParent = fxmlLoader.load();
        //Create a new sceen from the root node of the fxml
        return new Scene(formParent);
    }


}
