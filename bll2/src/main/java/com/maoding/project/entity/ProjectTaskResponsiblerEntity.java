package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskResponsiblerEntity
 * 类描述：项目负责人及参与人
 * 作    者：MaoSF
 * 日    期：2015年8月14日-下午3:50:14
 */
public class ProjectTaskResponsiblerEntity extends BaseEntity {

    /**
     *人员id(company_user_id)
     */
    private String targetId;

    /**
     *人员所在公司id
     */
    private String companyId;

    /**
     *项目id
     */
    private String projectId;

    /**
     *类型（1:负责人,2:专业设计人）
     */
    private String type;

    /**
     *专业名（如果是专业设计人的话），项目负责人该字段为null
     */
    private String majorName;


    /**
     *专业名（如果是专业设计人的话），项目负责人该字段为null
     */
    private String majorId;

    /**
     * 排序
     */
    private int seq;

    /**
     *任务id
     */
    private String taskId;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName == null ? null : majorName.trim();
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}