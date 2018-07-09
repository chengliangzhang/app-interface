package com.maoding.task.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandy on 2017/9/2.
 */
public class IssueTaskDTO {

    private String projectName;

    List<ProjectIssueTaskDTO> taskList = new ArrayList<>();

    List<ProjectIssueTaskDTO> treeList = new ArrayList<>();

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectIssueTaskDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ProjectIssueTaskDTO> taskList) {
        this.taskList = taskList;
    }

    public List<ProjectIssueTaskDTO> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<ProjectIssueTaskDTO> treeList) {
        this.treeList = treeList;
    }
}
