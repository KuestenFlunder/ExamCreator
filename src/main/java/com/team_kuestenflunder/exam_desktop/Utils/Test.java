package com.team_kuestenflunder.exam_desktop.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Test {


    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Beispiel-JsonNode-Objekte
            String jsonString1 = "{\"name\":\"John\", \"age\":30}";
            String jsonString2 = "{\"city\":\"New York\", \"country\":\"USA\"}";

            // JsonNode-Objekte erstellen
            JsonNode jsonNode1 = objectMapper.readTree(jsonString1);
            JsonNode jsonNode2 = objectMapper.readTree(jsonString2);

            // ObjectNode erstellen und JsonNode-Objekte übergeben
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.setAll((ObjectNode) jsonNode1);
            objectNode.setAll((ObjectNode) jsonNode2);

            // Das ObjectNode-Objekt verwenden
            String name = objectNode.get("name").asText();
            int age = objectNode.get("age").asInt();
            String city = objectNode.get("city").asText();
            String country = objectNode.get("country").asText();

            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("City: " + city);
            System.out.println("Country: " + country);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}