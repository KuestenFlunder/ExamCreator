package com.team_kuestenflunder.exam_desktop.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonHandler {

    public void writeJson(List<Question> questionList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writeValue(new File("src/main/Output/JsonOutput.json"), questionList);
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


    //TODO Add filechoser in Frontend to select the json files
    public void mergeJsonFiles(List<java.io.File> selectedFiles) {

        List<JsonNode> jsonMergResults = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            for (File file : selectedFiles) {
                String path = file.toString();
                jsonMergResults = ( objectMapper.readValue(new File(path), new TypeReference<>() {
                }));
            }
            objectMapper.writeValue(new File("src/main/Output/JsonMerge.json"), jsonMergResults);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO Add filechoser in Frontend to select the json files
    public void mergeJsonFiles (String... filePaths ) {
        List<JsonNode> jsonMergResults = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            for (String filePath : filePaths) {
                jsonMergResults = objectMapper.readValue(new File(filePath), new TypeReference<>() {});
            }
            objectMapper.writeValue(new File("src/main/Output/mergedJson_1.json"), jsonMergResults);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.mergeJsonFiles("src/main/Output/20_Questions.json","src/main/Output/JsonOutput.json");
    }

}
