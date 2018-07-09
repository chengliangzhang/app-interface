package com.maoding.projectcost.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectCostPointDTO
 * 类描述：费用节点表（记录费用在哪个节点上产生的，费用的描述，金额）
 * 项目费用收款节点表
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午4:11:50
 */
public class ProjectCostPointDTO extends BaseDTO {

    /**
     * 项目id
     */
    private String projectId;


    private String status;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 总金额的id
     */
    private String costId;
    /**
     * 回款节点描述
     */
    private String feeDescription;

    /**
     * 回款比例
     */
    private String feeProportion;
    /**
     * 回款金额
     */
    private BigDecimal fee;


    /**
     * 类型1:合同总金额，2：技术审查费,3合作设计费付款,4.其他费用
     */
    private String type;

    /**
     * 标示：1：正式协议，2：补充协议
     */
    private String flag;

    /**
     * 预留字段1
     */
    private int seq;

    /**
     * 父节点id
     */
    private String pid;

    /**
     * 已收
     */
    private BigDecimal totalPaidFee;

    /**
     * 总金额
     */
    private BigDecimal totalFee;

    /**
     * 应收未收
     */
    private BigDecimal notReciveFee;


    /**
     * 节点明细
     */
    private List<ProjectCostPointDetailDTO> projectCostDetailList= new ArrayList<>();



    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeeDescription() {
        return feeDescription;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription;
    }

    public String getFeeProportion() {
        return feeProportion;
    }

    public void setFeeProportion(String feeProportion) {
        this.feeProportion = feeProportion;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public BigDecimal getTotalPaidFee() {
        return totalPaidFee;
    }

    public void setTotalPaidFee(BigDecimal totalPaidFee) {
        this.totalPaidFee = totalPaidFee;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getNotReciveFee() {
        return notReciveFee;
    }

    public void setNotReciveFee(BigDecimal notReciveFee) {
        this.notReciveFee = notReciveFee;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<ProjectCostPointDetailDTO> getProjectCostDetailList() {
        return projectCostDetailList;
    }

    public void setProjectCostDetailList(List<ProjectCostPointDetailDTO> projectCostDetailList) {
        this.projectCostDetailList = projectCostDetailList;
    }
}
