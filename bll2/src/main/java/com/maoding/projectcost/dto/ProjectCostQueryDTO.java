package com.maoding.projectcost.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by sandy on 2017/9/5.
 */
public class ProjectCostQueryDTO extends BaseDTO {

    private String projectId;

    private String companyId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
