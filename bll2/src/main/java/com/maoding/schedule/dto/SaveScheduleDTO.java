package com.maoding.schedule.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dto.CompanyUserAppDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveScheduleDTO extends BaseDTO {

    private String targetId;
    /**
     * 关联的项目
     */
    private String projectId;

    /**
     * 标题
     */
    private String contentDesc;

    /**
     * 内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;

    /**
     * 日程类型（1=日程，2=会议）
     */
    private Integer scheduleType;

    /**
     * 提前提醒的时间
     */
    private Integer reminderTime;

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 开始时间：前端传递过来的字符串
     */
    private String scheduleStartTime;

    /**
     * 结束时间：前端传递过来的字符串
     */
    private String scheduleEndTime;

    private Date startTime;

    private Date endTime;

    private String address;

    /**
     * 提醒方式（1：卯丁，2：短信）
     */
    private Integer reminderMode;

    private List<CompanyUserAppDTO> memberList = new ArrayList<>();

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(Integer scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Integer getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Integer reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(String scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public String getScheduleEndTime() {
        return scheduleEndTime;
    }

    public void setScheduleEndTime(String scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
    }

    public Date getStartTime() {
        if (!StringUtil.isNullOrEmpty(this.scheduleStartTime)){
            startTime = DateUtils.str2Date(scheduleStartTime,DateUtils.time_sdf_slash);
        }
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        if (!StringUtil.isNullOrEmpty(this.scheduleEndTime)){
            endTime = DateUtils.str2Date(scheduleEndTime,DateUtils.time_sdf_slash);
        }
        return endTime;
    }

    public Integer getReminderMode() {
        return reminderMode;
    }

    public void setReminderMode(Integer reminderMode) {
        this.reminderMode = reminderMode;
    }

    public List<CompanyUserAppDTO> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<CompanyUserAppDTO> memberList) {
        this.memberList = memberList;
    }
//    public List<String> getMemberIds() {
//        return memberIds;
//    }
//
//    public void setMemberIds(List<String> memberIds) {
//        this.memberIds = memberIds;
//    }
//    public List<String> getUserIds() {
//        return userIds;
//    }
//
//    public void setUserIds(List<String> userIds) {
//        this.userIds = userIds;
//    }
}
