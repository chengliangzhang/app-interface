package com.maoding.deliver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maoding.core.base.dto.BaseShowDTO;
import com.maoding.v2.project.dto.ProjectSkyDriveListDTO;

import java.util.Date;
import java.util.List;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * @date : 2018/7/18
 * @description : 查询交付列表时的返回信息
 */
public class DeliverDTO extends BaseShowDTO {
    /** 交付说明 */
    private String remark;
    /** 截止时间 */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;
    /** 发起人 */
    private String createBy;
    /** 发起人名称 */
    private String createByName;
    /** 发起时间 */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;
    /** 交付是否已经结束 */
    private String isFinished;
    /** 交付目录编号 */
    private String targetId;
    /** 签发任务编号 */
    private String issueId;
    /** 签发任务路径 */
    private String issuePath;

    /** 负责人列表 */
    private List<ResponseDTO> responseList;
    /** 交付目录下的文件 */
    private List<ProjectSkyDriveListDTO> uploadedList;

    public List<ProjectSkyDriveListDTO> getUploadedList() {
        return uploadedList;
    }

    public String getIssuePath() {
        return issuePath;
    }

    public void setIssuePath(String issuePath) {
        this.issuePath = issuePath;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public void setUploadedList(List<ProjectSkyDriveListDTO> uploadedList) {
        this.uploadedList = uploadedList;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    public List<ResponseDTO> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<ResponseDTO> responseList) {
        this.responseList = responseList;
    }
}
