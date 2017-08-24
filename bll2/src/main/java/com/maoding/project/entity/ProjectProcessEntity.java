package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

public class ProjectProcessEntity extends BaseEntity {
    private String processName;

    private String companyId;

    private String projectId;

    private String taskManageId;

    private String status;

    private int seq;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName == null ? null : processName.trim();
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

    public String getTaskManageId() {
        return taskManageId;
    }

    public void setTaskManageId(String taskManageId) {
        this.taskManageId = taskManageId == null ? null : taskManageId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}