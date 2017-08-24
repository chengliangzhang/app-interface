package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

public class ProjectMajorEntity extends BaseEntity{

    private String majorName;

    private String companyId;


    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName == null ? null : majorName.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

}