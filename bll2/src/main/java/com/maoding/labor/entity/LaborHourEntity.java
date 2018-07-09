package com.maoding.labor.entity;

import com.maoding.core.base.entity.BaseEntity;

import java.util.Date;

public class LaborHourEntity extends BaseEntity {

    private String companyUserId;

    private String remark;

    private String companyId;

    private String projectId;

    private Date laborDate;

    private String laborHours;

    private Integer deleted;


    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId == null ? null : companyUserId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Date getLaborDate() {
        return laborDate;
    }

    public void setLaborDate(Date laborDate) {
        this.laborDate = laborDate;
    }

    public String getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(String laborHours) {
        this.laborHours = laborHours == null ? null : laborHours.trim();
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

}