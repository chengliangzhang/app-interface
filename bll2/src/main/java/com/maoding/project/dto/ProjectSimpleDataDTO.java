package com.maoding.project.dto;

public class ProjectSimpleDataDTO {

    private String id;
    private String projectName;
    private String totalLaborHours;
    private String laborHours;
    private String isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTotalLaborHours() {
        return totalLaborHours;
    }

    public void setTotalLaborHours(String totalLaborHours) {
        this.totalLaborHours = totalLaborHours;
    }

    public String getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(String laborHours) {
        this.laborHours = laborHours;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
}
