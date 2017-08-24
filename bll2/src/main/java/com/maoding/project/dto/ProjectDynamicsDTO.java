package com.maoding.project.dto;

import com.maoding.core.base.entity.BaseEntity;
import com.maoding.project.entity.ProjectDynamicsEntity;

import java.util.ArrayList;
import java.util.List;

public class ProjectDynamicsDTO extends BaseEntity {

    /**
     *日期
     */
    private String dateTime;

    /**
     *动态新
     */
    private List<ProjectDynamicsEntity> projectDynamicsList = new ArrayList<ProjectDynamicsEntity>();

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<ProjectDynamicsEntity> getProjectDynamicsList() {
        return projectDynamicsList;
    }

    public void setProjectDynamicsList(List<ProjectDynamicsEntity> projectDynamicsList) {
        this.projectDynamicsList = projectDynamicsList;
    }
}