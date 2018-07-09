package com.maoding.labor.dto;

public class LaborHourDTO {

    private String id;

    private String projectId;

    private String laborHours;

    private String projectName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(String laborHours) {
        this.laborHours = laborHours;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
