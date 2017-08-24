package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDcontentEntity
 * 类描述：设计阶段
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午4:11:50
 */
public class ProjectDesignContentEntity extends BaseEntity {
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 企业id
     */
    private String companyId;
    /**
     * 设计阶段id(数据字典)
     */
    private String contentId;
    /**
     * 设计阶段名称
     */
    private String contentName;

    /**
     * 备忘录
     */
    private String memo;


    /**
     * 排序字段
     */
    private int seq;


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

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
