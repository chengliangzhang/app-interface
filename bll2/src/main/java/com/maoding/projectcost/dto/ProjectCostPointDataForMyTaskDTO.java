package com.maoding.projectcost.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectCostPointDTO
 * 类描述：费用节点表（记录费用在哪个节点上产生的，费用的描述，金额）
 * 项目费用收款节点表
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午4:11:50
 */
public class ProjectCostPointDataForMyTaskDTO {

    private String id;

    private String pointDetailId;

    /**
     * 任务id（只有从任务自动产生的节点才有taskId）
     */
    private String taskId;

    /**
     * 项目id
     */
    private String projectId;


    private String status;

    /*********************合同回款信息(技术审查，合作设计)***************/
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

    /*******************end************************/

    /**
     * 回款详情总金额
     */
    private BigDecimal backFee;
    /**
     * 未付，或许应收未收
     */
    private BigDecimal unpaid;

    /**
     * 类型1:合同总金额，2：技术审查费,3合作设计费付款,4.其他费用付款，5.其他费用收款
     */
    private String type;


    private BigDecimal paidFee;//累计到账，付款


    /************************发起回款信息部分**********************/
    /**
     * 发起的金额金额（计划收款）
     */
    private BigDecimal pointDetailFee;

    /**
     * 操作人（发起人）
     */
    private String userName;
    /*******************end************************/
    /*************************确认收款金额************************/

    private BigDecimal paymentFee;

    /**
     * 对应该笔款项的收付日期
     */
    private Date paymentDate;

    private String handlerName;//处理人
    /**
     * 经营负责人 确认付款 总金额
     */
    private BigDecimal totalPaymentFee;

    private BigDecimal totalUnPaymentFee;

    /***************************end*******************************/
    /**
     * 付款、到款详情
     */
    private List<PaymentDataDTO> paymentList = new ArrayList();

    /**
     * 公司名
     */
    private String companyName;

    private String projectName;//项目名称


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

    public BigDecimal getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(BigDecimal unpaid) {
        this.unpaid = unpaid;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public BigDecimal getBackFee() {
        return backFee;
    }

    public void setBackFee(BigDecimal backFee) {
        this.backFee = backFee;
    }

    public BigDecimal getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(BigDecimal paidFee) {
        this.paidFee = paidFee;
    }

    public BigDecimal getPointDetailFee() {
        return pointDetailFee;
    }

    public void setPointDetailFee(BigDecimal pointDetailFee) {
        this.pointDetailFee = pointDetailFee;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PaymentDataDTO> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<PaymentDataDTO> paymentList) {
        this.paymentList = paymentList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPointDetailId() {
        return pointDetailId;
    }

    public void setPointDetailId(String pointDetailId) {
        this.pointDetailId = pointDetailId;
    }

    public BigDecimal getPaymentFee() {
        return paymentFee;
    }

    public void setPaymentFee(BigDecimal paymentFee) {
        this.paymentFee = paymentFee;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getTotalPaymentFee() {
        return totalPaymentFee;
    }

    public void setTotalPaymentFee(BigDecimal totalPaymentFee) {
        this.totalPaymentFee = totalPaymentFee;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getTotalUnPaymentFee() {
        if(pointDetailFee!=null){
            if(totalPaymentFee==null){
                totalUnPaymentFee = pointDetailFee;
            }else {
                totalUnPaymentFee = pointDetailFee.subtract(totalPaymentFee);
            }
        }
        return totalUnPaymentFee;
    }

    public void setTotalUnPaymentFee(BigDecimal totalUnPaymentFee) {
        this.totalUnPaymentFee = totalUnPaymentFee;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }
}
