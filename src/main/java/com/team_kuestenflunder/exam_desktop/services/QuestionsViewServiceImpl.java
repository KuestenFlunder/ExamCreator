package com.team_kuestenflunder.exam_desktop.services;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.Constants;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;

import java.util.*;

public class QuestionsViewServiceImpl implements Service{
    QuestionRepositoryImpl questionRepository;

    @Inject
    public QuestionsViewServiceImpl(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void addQuestion(Question question) {
    }

    @Override
    public Question getQuestion(String id) {
        return null;
    }

    @Override
    public List<Question> getQuestions() {
        return questionRepository.getQuestions();
    }


    public int getQuestionIndexByID(String id) {
        for (int i = 0; i < questionRepository.getQuestions().size(); i++) {
            if (questionRepository.getQuestions().get(i).getId().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }

    public void deleteQuestion(String id) {}


    public void deleteQuestion(Question question) {
        int index = getQuestionIndexByID(question.getId());
        questionRepository.deleteQuestion(index);
    }

    public Set<Question> getRandomExamQuestions(int requestedNumberOfQuestions) {

//requested number must be lower than getQuestions.size
        Set<Question> examQuestions = new HashSet<Question>();
        //? improve the random algorithm with probability percent of pick by topic
        Random random = new Random();
        for (int i = 0;  examQuestions.size()<requestedNumberOfQuestions; i++) {
            int index = random.nextInt(questionRepository.getQuestions().size());
            examQuestions.add(questionRepository.getQuestions().get(index));
        }
        return examQuestions;
    }
}
