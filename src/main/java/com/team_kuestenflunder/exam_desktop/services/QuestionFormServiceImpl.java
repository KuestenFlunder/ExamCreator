package com.team_kuestenflunder.exam_desktop.services;

import com.google.inject.Inject;
import com.team_kuestenflunder.exam_desktop.Constants;
import com.team_kuestenflunder.exam_desktop.entity.Question;
import com.team_kuestenflunder.exam_desktop.repository.QuestionRepositoryImpl;

import java.util.List;


public class QuestionFormServiceImpl implements Service {
    private final QuestionRepositoryImpl questionRepository;

    @Inject
    public QuestionFormServiceImpl(QuestionRepositoryImpl questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void addQuestion(Question question) {
        int index = getQuestionIndexByID(question.getId());
        if (index == Constants.NOT_FOUND) {
            questionRepository.addQuestion(question);
        } else {
            questionRepository.updateQuestion(index, question);
        }
    }

    @Override
    public Question getQuestion(String id) {
        int index = getQuestionIndexByID(id);
        return questionRepository.getQuestion(index);
    }

    @Override
    public List<Question> getQuestions() {
        return questionRepository.getQuestions();
    }

    private int getQuestionIndexByID(String id) {
        for (int i = 0; i < questionRepository.getQuestions().size(); i++) {
            if (questionRepository.getQuestions().get(i).getId().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }

    public void initList(){
        questionRepository.initList();
    }
}

