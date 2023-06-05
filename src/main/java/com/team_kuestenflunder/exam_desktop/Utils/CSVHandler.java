package com.team_kuestenflunder.exam_desktop.Utils;

import com.team_kuestenflunder.exam_desktop.entity.ExamResult;
import com.team_kuestenflunder.exam_desktop.entity.Question;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class CSVHandler {

    private static final String CSV_SEPARATOR = ",";

    private static boolean isFileEmpty(String filePath) {
        File file = new File(filePath);
        return file.length() == 0;
    }

    public static void writeCSV(List<ExamResult> examResults, File saveCsvTo) {

        boolean isFileEmpty = !saveCsvTo.exists() || saveCsvTo.length() == 0;

        try (FileWriter writer = new FileWriter(saveCsvTo,true)) {

            // Write header if file is empty
            if (isFileEmpty) {
                writer.append("Name");
                writer.append(CSV_SEPARATOR);
                writer.append("Surname");
                writer.append(CSV_SEPARATOR);
                writer.append("Date of exam");
                writer.append(CSV_SEPARATOR);
                writer.append("Number of questions");
                writer.append(CSV_SEPARATOR);
                writer.append("Percent");
                writer.append(CSV_SEPARATOR);
                writer.append("Exam result");
                // removed CSV_SEPARATOR here
                writer.append("\n");
            }

            // Write data for all exam results
            for (ExamResult result : examResults) {
                writer.append(result.getStudentName());
                writer.append(CSV_SEPARATOR);
                writer.append(result.getStudentSurname());
                writer.append(CSV_SEPARATOR);
                writer.append(result.getDateOfExam());
                writer.append(CSV_SEPARATOR);
                writer.append(String.valueOf(result.getNumberOfQuestions()));
                writer.append(CSV_SEPARATOR);
                writer.append(result.getPercent());
                writer.append(CSV_SEPARATOR);
                writer.append(result.getExamResult());
                // removed CSV_SEPARATOR here
                writer.append("\n");
            }

            System.out.println("CSV file has been created successfully.");
        } catch (IOException e) {
            System.out.println("Error while writing CSV file: " + e.getMessage());
        }
    }


}
