package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DesignRangeEntity
 * 类描述：权限实体
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午3:46:50
 */
public class ProjectAttachEntity extends BaseEntity {

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 设计依据id,设计范围id(当类型为设计依据的附件时有值)
     */
    private String targetId;

    /**
     * 附件名称
     */
    private String fileName;

    /**
     * 附件类型(1.合同附件),2:设计依据附件，3，设计范围附件,4任务分解附件
     */
    private String fileType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 分组
     */
    private String fileGroup;

    public ProjectAttachEntity() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }
}
