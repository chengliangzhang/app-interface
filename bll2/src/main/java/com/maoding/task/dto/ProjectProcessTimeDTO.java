package com.maoding.task.dto;

import com.maoding.core.base.dto.BaseDTO;

public class ProjectProcessTimeDTO extends BaseDTO {

    /**
     *  组织ID（当前操作的组织ID）
     */
    private String companyId;

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