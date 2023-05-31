package com.team_kuestenflunder.exam_desktop.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class JsonHandler {

    //TODO Refactor writeJson methods to one

    public void writeJsonToFile(List<Question> questionList,File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        if (file == null) {
            file = new File("src/main/Output/JsonOutput.json");
        }

        objectMapper.writeValue(new File(file.toURI()), questionList);
    }

    public ObservableList<Question> readJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            File jsonFile = new File("src/main/Output/JsonOutput.json");
            List<Question> questionList = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            System.out.println("Json created successfully");
            return FXCollections.observableArrayList(questionList);
        } catch (FileNotFoundException e) {
            System.out.println("Json nicht gefunden. Es wird ein entsprechender Pfad beim Beenden der Applikation erstellt");
            return FXCollections.observableArrayList();
        }
    }

    public void mergeJsonFiles(List<java.io.File> selectedFiles) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ArrayNode mergedJsonNode = objectMapper.createArrayNode();
        try {
            for (File file : selectedFiles) {
                JsonNode jsonNode = objectMapper.readValue(new File(String.valueOf(file)), JsonNode.class);
                if (jsonNode.isArray()) {
                    mergedJsonNode.addAll((ArrayNode) jsonNode);
                } else {
                    mergedJsonNode.add(jsonNode);
                }
            }
            objectMapper.writeValue(new File("src/main/Output/JsonMerge.json"), mergedJsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
