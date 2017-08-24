package com.maoding.task.dto;

import java.util.ArrayList;
import java.util.List;

public class ProjectIssueDataDTO {


    /**
     *公司id
     */
    private String companyId;

    /**
     * 签发的公司
     */
    private String companyName;

    /**
     *负责的任务集合
     */
    List<ProjectTaskListDTO> taskList = new ArrayList<ProjectTaskListDTO>();


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<ProjectTaskListDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ProjectTaskListDTO> taskList) {
        this.taskList = taskList;
    }
}