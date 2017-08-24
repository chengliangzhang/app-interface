package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by Idccapp21 on 2016/12/19.
 */
public class TaskDTO extends BaseDTO {

     private String id;

     private String taskContent;

     private String projectId;

     // 1,立项=立即签发2，负责人＝分解任务，3，专业＝设置参与人员，4，任务＝查看任务 提交设计，5，财务＝立即审批
     private String type;

     private String createDate;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
