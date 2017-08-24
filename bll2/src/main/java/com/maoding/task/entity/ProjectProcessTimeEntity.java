package com.maoding.task.entity;

import com.maoding.core.base.entity.BaseEntity;

public class ProjectProcessTimeEntity extends BaseEntity {

    /**
     *  开始时间
     */

    private String startTime;

    /**
     * 结束时间
     */

    private String endTime;


    /**
     * 1=约定，2＝计划
     */
    private int type;


    /**
     * 备注
     */
    private String memo;


    /**
     * 任务id（设计阶段的id）
     */
    private String targetId;

    /**
     * 记录公司id
     */
    private String companyId;


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}