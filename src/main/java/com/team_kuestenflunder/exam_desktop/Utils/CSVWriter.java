package com.team_kuestenflunder.exam_desktop.Utils;

import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;
import com.team_kuestenflunder.exam_desktop.services.QuestionFormServiceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.util.List;


public class CSVWriter {

    private static final String CSV_SEPARATOR = ",";



    public void writeCSV(Question question) {

        String csvFile = "questions.csv";
        boolean isFileEmpty = isFileEmpty(csvFile);
        try (FileWriter writer =new FileWriter(csvFile, true)) {
            // Write header
            if(isFileEmpty) {
                writer.append("ID");
                writer.append(CSV_SEPARATOR);
                writer.append("Creation Date");
                writer.append(CSV_SEPARATOR);
                writer.append("Question Title");
                writer.append(CSV_SEPARATOR);
                writer.append("Topic");
                writer.append(CSV_SEPARATOR);
                writer.append("Question Text");
                writer.append(CSV_SEPARATOR);
                writer.append("Code");
                writer.append(CSV_SEPARATOR);
                writer.append("Answers");
                writer.append("\n");
            }
            // Write data
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                writer.append(question.getId());
                writer.append(CSV_SEPARATOR);
                writer.append(formatter.format(question.getCreationDate()));
                writer.append(CSV_SEPARATOR);
                writer.append(question.getQuestionTitle());
                writer.append(CSV_SEPARATOR);
                writer.append(String.valueOf(question.getTopic()));
                writer.append(CSV_SEPARATOR);
                writer.append(question.getQuestionText());
                writer.append(CSV_SEPARATOR);
                writer.append(question.getCode());
                writer.append(CSV_SEPARATOR);
                writer.append(question.getAnswerList().toString());
                writer.append("\n");


            System.out.println("CSV file has been created successfully.");
        } catch (IOException e) {
            System.out.println("Error while writing CSV file: " + e.getMessage());
        }
    }
    private static boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return file.length() == 0;
    }
}
