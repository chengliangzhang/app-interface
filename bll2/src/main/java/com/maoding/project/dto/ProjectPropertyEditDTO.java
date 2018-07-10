package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

public class ProjectPropertyEditDTO extends BaseDTO{

    private String projectId;

    private List<CustomProjectPropertyDTO> projectPropertyList = new ArrayList<>();

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<CustomProjectPropertyDTO> getProjectPropertyList() {
        return projectPropertyList;
    }

    public void setProjectPropertyList(List<CustomProjectPropertyDTO> projectPropertyList) {
        this.projectPropertyList = projectPropertyList;
    }
}
