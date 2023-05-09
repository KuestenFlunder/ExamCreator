package com.team_kuestenflunder.exam_desktop.question;

import java.time.LocalDate;
import java.util.UUID;

public class QuestionEntity {
    private final String id;
    private final LocalDate creationDate;
    private AnswerList answerList;
    private ChangeLog changeLog;
    private String Code;
    private String questionText;
    private String questionTitle; //? Might be useful if we find meaningful titles
    private Topics topic;
    //! Maybe use Markdown for textfileds

    public QuestionEntity() {
        this.creationDate = LocalDate.now();
        this.id = UUID.randomUUID().toString();
    }
}
