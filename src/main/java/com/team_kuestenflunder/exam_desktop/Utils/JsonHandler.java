package com.team_kuestenflunder.exam_desktop.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.team_kuestenflunder.exam_desktop.entity.Question;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonHandler {

    public static void writeJson(List<Question> questionList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
//        String jsonData = objectMapper.writeValueAsString(questionList);
//        System.out.println("jsonData = " + jsonData);
        objectMapper.writeValue(new File("JsonOutput.json"), questionList);

    }

    public static List<Question> readJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File jsonFile = new File("JsonOutput.json");
        List<Question> questionList = objectMapper.readValue(jsonFile, new TypeReference<List<Question>>() {
        });
        return questionList;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("JsonHandler.readJson() = " + JsonHandler.readJson());
    }
}
