package com.maoding.feedback.entity;

import com.maoding.core.base.entity.BaseEntity;

public class FeedbackEntity extends BaseEntity{

    private String version;

    private Boolean isHandle;

    private String question;

    /**
     * 移动平台（android ,ios,web）
     */
    private String platform;

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

    public Boolean getHandle() {
        return isHandle;
    }

    public void setHandle(Boolean handle) {
        isHandle = handle;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}