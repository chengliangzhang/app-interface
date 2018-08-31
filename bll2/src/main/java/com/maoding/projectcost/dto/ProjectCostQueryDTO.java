package com.maoding.projectcost.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * Created by sandy on 2017/9/5.
 */
public class ProjectCostQueryDTO extends BaseDTO {

    private String projectId;

    /**
     * 组织id（当前组织）
     */
    private String companyId;

    /**
     * 主记录id
     */
    private String costId;

    /**
     * 收款节点的id
     */
    private String pointId;

    /**
     * 发起收款的节点id
     */
    private String pointDetailId;

    /**
     * 收款计划：1，付款计划：2
     */
    private Integer payType;

    /**
     * 从任务方进入，附带了taskId，用于获取数据的companyId
     */
    private String taskId;

    /**
     * 申请单据id（exp_main 中的id）
     */
    private String mainId;

    /**
     * 任务16-17的targetId
     */
    private String paymentDetailId;

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

    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointDetailId() {
        return pointDetailId;
    }

    public void setPointDetailId(String pointDetailId) {
        this.pointDetailId = pointDetailId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPaymentDetailId() {
        return paymentDetailId;
    }

    public void setPaymentDetailId(String paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }
}
