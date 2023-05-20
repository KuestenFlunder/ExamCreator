package com.team_kuestenflunder.exam_desktop.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.team_kuestenflunder.exam_desktop.entity.Question;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonWriter {

    public static void writeJson(List<Question> questionList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
//        String jsonData = objectMapper.writeValueAsString(questionList);
//        System.out.println("jsonData = " + jsonData);
        objectMapper.writeValue(new File("JsonOutput.json"), questionList);

    }
}
