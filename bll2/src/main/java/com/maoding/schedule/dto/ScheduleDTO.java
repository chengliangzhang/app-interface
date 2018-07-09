package com.maoding.schedule.dto;

import com.maoding.attach.dto.FileDataDTO;
import com.maoding.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleDTO {

    private String id;

    private String projectId;

    private String projectName;

    private String contentDesc;

    private String content;

    private String remark;

    private String address;

    private Date startTime;

    private Date endTime;

    private Date createDate;

    private String cancelReason;

    private String publishId;

    private String publishUserName;

    private Integer totalMember;

    private Integer totalPartyMember;

    private Integer scheduleType;

    private Integer reminderTime;

    private Integer status; // 0：未处理，1：同意参加，2：不同意参加 会议，3: 已结束（已参加的）

    private List<ScheduleMemberDTO> memberList = new ArrayList<>();
    /**
     * 附件
     */
    private List<FileDataDTO> attachList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }

    public Integer getTotalPartyMember() {
        return totalPartyMember;
    }

    public void setTotalPartyMember(Integer totalPartyMember) {
        this.totalPartyMember = totalPartyMember;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ScheduleMemberDTO> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<ScheduleMemberDTO> memberList) {
        this.memberList = memberList;
    }

    public List<FileDataDTO> getAttachList() {
        return attachList;
    }

    public void setAttachList(List<FileDataDTO> attachList) {
        this.attachList = attachList;
    }
}
