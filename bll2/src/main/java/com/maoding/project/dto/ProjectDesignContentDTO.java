package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.StringUtil;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDcontent
 * 类描述：设计阶段DTO
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午4:48:50
 */
public class ProjectDesignContentDTO extends BaseDTO {
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
     * 排序字段
     */
    private int seq;

    /**
     *开始时间
     */
    private String startTime;

    /**
     *结束时间
     */
    private String endTime;

    /**
     * 变更原因
     */
    private String memo;

    private int changeFlag;//后台给予标识，判断前端是否出现 变更 字眼

    private int flag;//是否变更>1有变更，否则没有 ，前端给以后台，用于判断是否有变更

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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getChangeFlag() {
        if(!StringUtil.isNullOrEmpty(this.startTime) || !StringUtil.isNullOrEmpty(this.endTime)){
            changeFlag = 1;
        }
        return changeFlag;
    }

    public void setChangeFlag(int changeFlag) {
        this.changeFlag = changeFlag;
    }
}
