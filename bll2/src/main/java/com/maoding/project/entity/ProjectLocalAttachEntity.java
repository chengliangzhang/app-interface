package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

import java.util.Date;

public class ProjectLocalAttachEntity extends BaseEntity {

    private String companyId;

    private String projectId;

    private String companyUserId;

    private String fileName;

    private String macPath;

    private String filePath;


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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getMacPath() {
        return macPath;
    }

    public void setMacPath(String macPath) {
        this.macPath = macPath == null ? null : macPath.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }
}