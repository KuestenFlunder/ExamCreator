package com.team_kuestenflunder.exam_desktop.Utils;

public class InputValidation {

    private static void StringValidator(String toTest, String massage) {
        if (toTest == null || toTest.isEmpty())
            throw new IllegalArgumentException(massage + " cannot be null or empty!");
    }
}
