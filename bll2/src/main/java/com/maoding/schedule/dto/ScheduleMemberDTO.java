package com.maoding.schedule.dto;

import com.maoding.attach.dto.FilePathDTO;

public class ScheduleMemberDTO extends FilePathDTO {

    /**
     * id 为 companyUserId  = memberId，为了前端 数据字典同一
     */
    private String id;

    private String scheduleId;

    private String memberId;

    private String userId;

    private Integer status;

    private String refuseReason;

    private Integer reminderTime;

    private String userName;

    private String companyId; //member 所在组织的id，不一定是会议/日程的companyId

    private String scheduleMemberId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Integer getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Integer reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getScheduleMemberId() {
        return scheduleMemberId;
    }

    public void setScheduleMemberId(String scheduleMemberId) {
        this.scheduleMemberId = scheduleMemberId;
    }
}
