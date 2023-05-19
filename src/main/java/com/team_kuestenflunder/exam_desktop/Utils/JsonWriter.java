package com.team_kuestenflunder.exam_desktop.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.team_kuestenflunder.exam_desktop.entity.Answer;
import com.team_kuestenflunder.exam_desktop.entity.Answers;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.entity.Topics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonWriter {

    public static void writeJson(List<Question> questionList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writerWithDefaultPrettyPrinter();

//       //Test Data
//        List<Question> questionList = new ArrayList<>();
//        Answers answers = new Answers();
//        answers.addAnswer(new Answer("2 kleine Schweinchen",true,"Ich kenne die Frage nicht "));
//
//
//       questionList.add(new Question("Frage 1", Topics.No_Topic,"Eine kleine Frage","Ein bisschen Code", answers));
//       questionList.add(new Question("Frage 2", Topics.No_Topic,"Eine kleine Frage2","Ein bisschen Code2", new Answers()));
//       questionList.add( new Question("Frage 3", Topics.No_Topic,"Eine kleine Frage3","Ein bisschen Code3", new Answers()));

       // String jsonData = objectMapper.writeValueAsString(questionList);

        objectMapper.writeValue(new File("JsonOutput.json"),questionList);

    }


}
