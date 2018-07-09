package com.maoding.circle.dto;

import com.maoding.attach.dto.FilePathDTO;

import java.util.Date;

public class MaodingCircleBaseDTO extends FilePathDTO {

    private String id;
    private String content;
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
