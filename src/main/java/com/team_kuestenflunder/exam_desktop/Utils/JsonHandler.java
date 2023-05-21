package com.team_kuestenflunder.exam_desktop.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonHandler {



    public static void writeJson(List<Question> questionList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.writeValue(new File("JsonOutput.json"), questionList);

    }

    public static ObservableList<Question> readJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File jsonFile = new File("JsonOutput.json");
        List<Question> questionList = objectMapper.readValue(jsonFile, new TypeReference<>() {
        });

        return FXCollections.observableArrayList(questionList);
    }
}

