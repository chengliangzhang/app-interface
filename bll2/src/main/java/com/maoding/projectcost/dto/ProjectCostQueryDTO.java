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
}
