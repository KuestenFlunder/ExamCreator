package com.team_kuestenflunder.exam_desktop.services;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.Constants;
import com.team_kuestenflunder.exam_desktop.Utils.JsonHandler;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class QuestionsViewServiceImpl implements Service {
    QuestionRepositoryImpl questionRepository;

    @Inject
    public QuestionsViewServiceImpl(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void addQuestion(Question question) {
    }

    @Override
    public Question getQuestion(String id) {
        return null;
    }

    @Override
    public List<Question> getQuestions() {
        return questionRepository.getQuestions();
    }

    public void addQuestions(ObservableList<Question> questions) {
        questionRepository.addQuestions(questions);
    }

    public int getQuestionIndexByID(String id) {
        for (int i = 0; i < questionRepository.getQuestions().size(); i++) {
            if (questionRepository.getQuestions().get(i).getId().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }


    public void deleteQuestion(Question question) {
        int index = getQuestionIndexByID(question.getId());
        questionRepository.deleteQuestion(index);
    }

    public void loadInnerStorage(){
        try {
            questionRepository.addQuestions(JsonHandler.readJsonFromInnerStorage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method creates a new table view with four columns: UUID, Thema, Titel, and richtige Antworten.
     * It first creates each column and sets its cell value factory.
     * Then, it sets the table view's items using the list of questions from the question view service.
     * After that, it clears any existing columns in the table view.
     * Finally, it adds the new columns to the table view.
     * <p>
     * UUID column: contains the UUID of the question.
     * Thema column: contains the topic of the question.
     * Titel column: contains the title of the question.
     * richtige Antworten column: contains the number of correct answers of the question.
     * <p>
     * Note: this method does not check if the questions' properties corresponding to the columns exist.
     */
    public void createViewTable(TableView<Question> tableView) {

        TableColumn<Question, String> idColumn = new TableColumn<>("UUID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Question, String> topicColumn = new TableColumn<>("Thema");
        topicColumn.setCellValueFactory(new PropertyValueFactory<>("topic"));

        TableColumn<Question, String> titleColumn = new TableColumn<>("Titel");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("questionTitle"));

        TableColumn<Question, Integer> correctAnswersColumn = new TableColumn<>("richtige Antworten");
        correctAnswersColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData
                        .getValue()
                        .getAnswers()
                        .getCorrectAnswers())
                        .asObject());

        tableView.setItems((ObservableList<Question>) questionRepository.getQuestions());

        // Clear the existing columns
        tableView.getColumns().clear();

        // Add the columns
        tableView.getColumns().addAll(idColumn, topicColumn, titleColumn, correctAnswersColumn);
    }

}
