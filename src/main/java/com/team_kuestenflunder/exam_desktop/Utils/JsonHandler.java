package com.team_kuestenflunder.exam_desktop.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class JsonHandler {

    public void writeQuestionJsonToInnerStorage(List<Question> questionList, File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        if (file == null) {
            file = new File("src/main/Data/innerJsonDatastore.json");
        }
        objectMapper.writeValue(new File(file.toURI()), questionList);
        System.out.println("inner Json written successfully");
    }
    public void writeStudentsJsonToInnerStorage(List<Student> questionList, File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        if (file == null) {
            file = new File("src/main/Data/studentInnerJsonDatastore.json");
        }
        objectMapper.writeValue(new File(file.toURI()), questionList);
        System.out.println("inner Json written successfully");
    }

    public static  ObservableList<Question> readJsonFromInnerStorage() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            File jsonFile = new File("src/main/Data/innerJsonDatastore.json");
            List<Question> questionList = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            System.out.println("innerJson created successfully");
            return FXCollections.observableArrayList(questionList);
        } catch (FileNotFoundException e) {
            System.out.println("Json nicht gefunden. Es wird ein entsprechender Pfad beim Beenden der Applikation erstellt");
            return FXCollections.observableArrayList();
        }
    }
    public static ObservableList<Student> readStudentJsonFromInnerStorage() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            File jsonFile = new File("src/main/Data/studentInnerJsonDatastore.json");
            List<Student> studentList = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            System.out.println("innerJson created successfully");
            return FXCollections.observableArrayList(studentList);
        } catch (FileNotFoundException e) {
            System.out.println("Json nicht gefunden. Es wird ein entsprechender Pfad beim Beenden der Applikation erstellt");
            return FXCollections.observableArrayList();
        }
    }
    public ObservableList<Question> readJsonFromFile(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            File jsonFile = new File(file.toURI());
            List<Question> questionList = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            return FXCollections.observableArrayList(questionList);
        } catch (FileNotFoundException e) {
            System.out.println("Json nicht gefunden. Es wird ein entsprechender Pfad beim Beenden der Applikation erstellt");
            return FXCollections.observableArrayList();
        }
    }

    public void mergeJsonFiles(List<java.io.File> selectedFiles, File outputFile) {
        if(selectedFiles == null) return;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ArrayNode mergedJsonNode = objectMapper.createArrayNode();
        try {
            //TODO refactor to Stream
            addAllJsonNodes(selectedFiles, objectMapper, mergedJsonNode);
            objectMapper.writeValue(new File(outputFile.toURI()), omitNodeDuplication(mergedJsonNode));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addAllJsonNodes(List<File> selectedFiles, ObjectMapper objectMapper, ArrayNode mergedJsonNode) throws IOException {
        for (File file : selectedFiles) {
            JsonNode jsonNode = objectMapper.readValue(new File(file.toURI()), JsonNode.class);
            if (jsonNode.isArray()) {
                mergedJsonNode.addAll((ArrayNode) jsonNode);
            } else {
                mergedJsonNode.add(jsonNode);
            }
        }
    }

    private Set<JsonNode> omitNodeDuplication(ArrayNode mergedJsonNode) {
        Set<JsonNode> valuesOfNodesSet = new LinkedHashSet<>();
        for (JsonNode jsonNode : mergedJsonNode) {
            valuesOfNodesSet.add(jsonNode);
            }
        return valuesOfNodesSet;
    }

}
