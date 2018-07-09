package com.maoding.projectcost.dto;

import java.math.BigDecimal;

/**
 * Created by sandy on 2017/9/6.
 */
public class ProjectCooperatorCostDTO {


    public ProjectCooperatorCostDTO(){
        planFee = new BigDecimal("0");
        notPayFee = new BigDecimal("0");
        notPaidFee = new BigDecimal("0");
        sumPayFee = new BigDecimal("0");
        sumPaidFee = new BigDecimal("0");
        detailFee = new BigDecimal("0");
    }
    /**
     * 对应费用的id
     */
    private String costId;

    /**
     * 设计组织
     */
    private String companyName;

    /**
     * 设计内容
     */
    private String taskName;

    /**
     * 1:收款，2：付款
     */
    private Integer feeType;

    /**
     * 收付详情费用统计
     */
    private BigDecimal detailFee;


    /**
     * 计划付款,未付
     */
    private BigDecimal planFee;

    /**
     * 未收
     */
    private BigDecimal notPaidFee;

    /**
     * 未付
     */
    private BigDecimal notPayFee;

    /**
     * 累计到款金额
     */
    private BigDecimal sumPaidFee;

    /**
     * 累计付款金额
     */
    private BigDecimal sumPayFee;


    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public BigDecimal getNotPayFee() {
        if(detailFee!=null){
            if(sumPayFee==null){
                sumPayFee = new BigDecimal("0");
            }
            notPayFee = detailFee.subtract(sumPayFee);
        }
        return notPayFee;
    }

    public void setNotPayFee(BigDecimal notPayFee) {
        this.notPayFee = notPayFee;
    }

    public BigDecimal getPlanFee() {
        return planFee;
    }

    public void setPlanFee(BigDecimal planFee) {
        this.planFee = planFee;
    }

    public BigDecimal getNotPaidFee() {
        if(detailFee!=null){
            if(sumPaidFee==null){
                sumPaidFee = new BigDecimal("0");
            }
            notPaidFee = detailFee.subtract(sumPaidFee);
        }
        return notPaidFee;
    }

    public void setNotPaidFee(BigDecimal notPaidFee) {
        this.notPaidFee = notPaidFee;
    }

    public BigDecimal getSumPaidFee() {
        return sumPaidFee;
    }

    public void setSumPaidFee(BigDecimal sumPaidFee) {
        this.sumPaidFee = sumPaidFee;
    }

    public BigDecimal getSumPayFee() {
        return sumPayFee;
    }

    public void setSumPayFee(BigDecimal sumPayFee) {
        this.sumPayFee = sumPayFee;
    }

    public BigDecimal getDetailFee() {
        return detailFee;
    }

    public void setDetailFee(BigDecimal detailFee) {
        this.detailFee = detailFee;
    }
}
