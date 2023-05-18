package com.team_kuestenflunder.exam_desktop.repository;

import com.team_kuestenflunder.exam_desktop.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class DataService {
    private static DataService instance;
    public final List<Question> questions = new ArrayList<>();

    private DataService() {
    }

    public static DataService getInstance() {
        if (instance == null) {
            synchronized (DataService.class) {
                if (instance == null) {
                    instance = new DataService();
                }
            }
        }
        return instance;
    }
}