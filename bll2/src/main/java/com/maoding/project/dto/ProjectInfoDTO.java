package com.maoding.project.dto;

import com.maoding.projectcost.dto.ProjectAmountFee;
import com.maoding.projectmember.dto.ProjectMemberDataDTO;
import com.maoding.task.dto.TaskDataDTO;

/**
 * 项目综合数据DTO
 */
public class ProjectInfoDTO {

    private int feeRole;//1：具有费用板块的权限，其他：不具有
    /**
     * 项目基本信息
     */
    private ProjectDataDTO projectDataDTO = new ProjectDataDTO();

    /**
     * 签发板块的数据
     */
    private TaskDataDTO issueData = new TaskDataDTO();

    /**
     *生产板块的数据
     */
    private TaskDataDTO produceData = new TaskDataDTO();

    /**
     * 费用模块数据
     */
    private ProjectAmountFee feeData = new ProjectAmountFee();

    /**
     * 参与成员数据
     */
    private ProjectMemberDataDTO memberData = new ProjectMemberDataDTO();

    public ProjectDataDTO getProjectDataDTO() {
        return projectDataDTO;
    }

    public void setProjectDataDTO(ProjectDataDTO projectDataDTO) {
        this.projectDataDTO = projectDataDTO;
    }

    public TaskDataDTO getIssueData() {
        return issueData;
    }

    public void setIssueData(TaskDataDTO issueData) {
        this.issueData = issueData;
    }

    public TaskDataDTO getProduceData() {
        return produceData;
    }

    public void setProduceData(TaskDataDTO produceData) {
        this.produceData = produceData;
    }

    public ProjectAmountFee getFeeData() {
        return feeData;
    }

    public void setFeeData(ProjectAmountFee feeData) {
        this.feeData = feeData;
    }

    public ProjectMemberDataDTO getMemberData() {
        return memberData;
    }

    public void setMemberData(ProjectMemberDataDTO memberData) {
        this.memberData = memberData;
    }

    public int getFeeRole() {
        return feeRole;
    }

    public void setFeeRole(int feeRole) {
        this.feeRole = feeRole;
    }
}
