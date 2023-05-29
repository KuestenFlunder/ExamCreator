package com.team_kuestenflunder.exam_desktop.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
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


    public void jsonFileMerger_1 (String... filePaths ) {
        String mergedJsonString = "";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            for (String filePath : filePaths) {
                JsonNode jsonNode = objectMapper.readTree(new File(filePath));
                mergedJsonString += jsonNode.toPrettyString();
            }
            //TODO alles wird in eine Zeile verpackt. unschöne Ausgabe
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/Output/mergedJson_1.json"), mergedJsonString);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void jsonFileMerger_2 (String... filePaths ) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonNode> jsonNodes = new ArrayList<>();
        try {
            for (String filePath : filePaths) {
                JsonNode jsonNode = objectMapper.readTree(new File(filePath));
                jsonNodes.add(jsonNode);
            }
            ObjectNode objectNode = objectMapper.createObjectNode();
            for (JsonNode jNode : jsonNodes) {
                if (jNode instanceof ObjectNode) {
                    System.out.println(jNode.toPrettyString());
                    objectNode.setAll((ObjectNode) jNode);       // TODO warum ClassCastException ?
//                    objectMapper.readValue(jNode.toString(), ObjectNode.class);   // der alternativen Code geht auch nicht
                }
            }
//            JsonNode mergedJson = objectNode;
            objectMapper.writeValue(new File("src/main/Output/mergedJson_2.json"), objectNode);
            System.out.println("eine zusammengesetzte JSON-Datei ist erzeugt");
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        JsonHandler handler = new JsonHandler();
        handler.jsonFileMerger_2 ("src/main/Output/JsonOutput.json", "src/main/Output/20_Questions.json" );
    }




}
