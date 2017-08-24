package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;

import java.util.Date;

public class ProjectTaskManagerDetailDTO extends BaseDTO{

    private String taskManageId;

    private String progressStartTime;

    private String progressEndTime;

    private String memo;

    /**
     * 关联任务的状态
     */
    private int taskStatus;

    private String projectId;

    private String designContentId;

    /**
     * 一共多少天
     */
    private int allDay;

    public String getTaskManageId() {
        return taskManageId;
    }

    public void setTaskManageId(String taskManageId) {
        this.taskManageId = taskManageId == null ? null : taskManageId.trim();
    }

    public String getProgressStartTime() {
        return progressStartTime;
    }

    public void setProgressStartTime(String progressStartTime) {
        this.progressStartTime = progressStartTime;
    }

    public String getProgressEndTime() {
        return progressEndTime;
    }

    public void setProgressEndTime(String progressEndTime) {
        this.progressEndTime = progressEndTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDesignContentId() {
        return designContentId;
    }

    public void setDesignContentId(String designContentId) {
        this.designContentId = designContentId;
    }

    public int getAllDay() {
        String startTime = this.progressStartTime;
        String endTime = this.progressEndTime;

        if(StringUtil.isNullOrEmpty(startTime) || StringUtil.isNullOrEmpty(endTime)){
            allDay=0;
        }else {
            Date startDate = DateUtils.str2Date(startTime,DateUtils.date_sdf);
            Date endDate = DateUtils.str2Date(endTime,DateUtils.date_sdf);
            allDay=DateUtils.daysOfTwo(endDate,startDate);
        }
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }
}