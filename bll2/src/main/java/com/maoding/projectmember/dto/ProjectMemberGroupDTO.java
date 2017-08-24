package com.maoding.projectmember.dto;

import com.maoding.project.dto.ProjectTaskProcessNodeDTO;

import java.util.ArrayList;
import java.util.List;

public class ProjectMemberGroupDTO {


    private String companyId;

    private String companyName;

    private String realName;

    private String projectCreateBy;

    private String projectCompanyId;

    private List<ProjectTaskProcessNodeDTO> nodeList = new ArrayList<>();

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getProjectCreateBy() {
        return projectCreateBy;
    }

    public void setProjectCreateBy(String projectCreateBy) {
        this.projectCreateBy = projectCreateBy;
    }

    public String getProjectCompanyId() {
        return projectCompanyId;
    }

    public void setProjectCompanyId(String projectCompanyId) {
        this.projectCompanyId = projectCompanyId;
    }

    public List<ProjectTaskProcessNodeDTO> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<ProjectTaskProcessNodeDTO> nodeList) {
        this.nodeList = nodeList;
    }
}