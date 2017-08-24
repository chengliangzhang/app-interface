package com.maoding.projectcost.dto;

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
public class ProjectCostPointDataDTO {

    private String id;

    /**
     * 任务id（只有从任务自动产生的节点才有taskId）
     */
    private String taskId;

    /**
     * 项目id
     */
    private String projectId;


    private String status;

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
     * 回款详情总金额
     */
    private BigDecimal backFee;
    /**
     * 未付
     */
    private BigDecimal unpaid;

    /**
     * 类型1:合同总金额，2：技术审查费,3合作设计费付款,4.其他费用
     */
    private String type;

    /**
     * 预留字段1
     */
    private int seq;

    /**
     * 父节点id
     */
    private String pid;


    /**
     * 备注（界面展示：共多少个收款节点，已发起收款有多少个）
     */
    private String memo;

    /**
     * 上一级的总金额
     */
    private BigDecimal totalFee;


    /**
     * 是否可以删除（0：可删除，1：不可删除）
     */
    private int deleteFlag;

    private BigDecimal paidFee;//用于计算未付


    /**
     * 子节点
     */
    private List<ProjectCostPointDetailDataDTO> pointDetailList = new ArrayList<ProjectCostPointDetailDataDTO>();


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

    public BigDecimal getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(BigDecimal unpaid) {
        this.unpaid = unpaid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<ProjectCostPointDetailDataDTO> getPointDetailList() {
        return pointDetailList;
    }

    public void setPointDetailList(List<ProjectCostPointDetailDataDTO> pointDetailList) {
        this.pointDetailList = pointDetailList;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getBackFee() {
        return backFee;
    }

    public void setBackFee(BigDecimal backFee) {
        this.backFee = backFee;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public BigDecimal getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(BigDecimal paidFee) {
        this.paidFee = paidFee;
    }
}
