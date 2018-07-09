package com.maoding.feedback.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by sandy on 2017/9/26.
 */
public class FeedbackDTO extends BaseDTO{

    private String version;

    private String question;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }
}
