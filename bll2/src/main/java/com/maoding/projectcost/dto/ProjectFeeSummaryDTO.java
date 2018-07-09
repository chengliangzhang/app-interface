package com.maoding.projectcost.dto;

import com.maoding.role.dto.ProjectOperatorDTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandy on 2017/9/5.
 */
public class ProjectFeeSummaryDTO {

    public ProjectFeeSummaryDTO() {
        this.contractSumFee = new BigDecimal("0");
        this.contractPaidFee = new BigDecimal("0");
        this.contractNotPaidFee = new BigDecimal("0");
        this.techIncomeFee = new BigDecimal("0");
        this.techPaidFee = new BigDecimal("0");
        this.techNotPaidFee = new BigDecimal("0");
        this.techOutcomeFee = new BigDecimal("0");
        this.techPayFee = new BigDecimal("0");
        this.techNotPayFee = new BigDecimal("0");
        this.cooperateIncomeFee = new BigDecimal("0");
        this.cooperatePaidFee = new BigDecimal("0");
        this.cooperateNotPaidFee = new BigDecimal("0");
        this.cooperateOutcomeFee = new BigDecimal("0");
        this.cooperatePayFee = new BigDecimal("0");
        this.cooperateNotPayFee = new BigDecimal("0");
        this.otherIncomeFee = new BigDecimal("0");
        this.otherPaidFee = new BigDecimal("0");
        this.otherNotPaidFee = new BigDecimal("0");
        this.otherOutcomeFee = new BigDecimal("0");
        this.otherPayFee = new BigDecimal("0");
        this.otherNotPayFee = new BigDecimal("0");
    }


    /**********合同回款数据***********/
    //总费用
    private BigDecimal contractSumFee;

    //已收
    private BigDecimal contractPaidFee;

    //应收未收
    private BigDecimal contractNotPaidFee;


    /**********技术审查费数据***********/
    //技术收款
    //总收款
    private BigDecimal techIncomeFee;

    //已收
    private BigDecimal techPaidFee;

    //应收未收
    private BigDecimal techNotPaidFee;

    /**付款**/
    //总付款
    private BigDecimal techOutcomeFee;
    //已付
    private BigDecimal techPayFee;
    //应付未付
    private BigDecimal techNotPayFee;

    /**********技术审查费数据***********/
    //收款
    // 总金额
    private BigDecimal cooperateIncomeFee;

    //总已收
    private BigDecimal cooperatePaidFee;

    //总应收未收
    private BigDecimal cooperateNotPaidFee;

    //付款
    //总金额
    private BigDecimal cooperateOutcomeFee;

    //已付款
    private BigDecimal cooperatePayFee;

    //应付未付
    private BigDecimal cooperateNotPayFee;

    /**********其他费用审查费数据***********/
    //收款
    //总金额
    private BigDecimal otherIncomeFee;
    //总已收
    private BigDecimal otherPaidFee;

    //未收
    private BigDecimal otherNotPaidFee;

    //付款
    //总金额
    private BigDecimal otherOutcomeFee;
    //已付款
    private BigDecimal otherPayFee;

    //未付
    private BigDecimal otherNotPayFee;

    private ProjectOperatorDTO roleMap = new ProjectOperatorDTO();

    public BigDecimal getContractSumFee() {
        return contractSumFee;
    }

    public void setContractSumFee(BigDecimal contractSumFee) {
        this.contractSumFee = contractSumFee;
    }

    public BigDecimal getContractPaidFee() {
        return contractPaidFee;
    }

    public void setContractPaidFee(BigDecimal contractPaidFee) {
        this.contractPaidFee = contractPaidFee;
    }

    public BigDecimal getContractNotPaidFee() {
        return contractNotPaidFee;
    }

    public void setContractNotPaidFee(BigDecimal contractNotPaidFee) {
        this.contractNotPaidFee = contractNotPaidFee;
    }

    public BigDecimal getTechIncomeFee() {
        return techIncomeFee;
    }

    public void setTechIncomeFee(BigDecimal techIncomeFee) {
        this.techIncomeFee = techIncomeFee;
    }

    public BigDecimal getTechPaidFee() {
        return techPaidFee;
    }

    public void setTechPaidFee(BigDecimal techPaidFee) {
        this.techPaidFee = techPaidFee;
    }

    public BigDecimal getTechNotPaidFee() {
        return techNotPaidFee;
    }

    public void setTechNotPaidFee(BigDecimal techNotPaidFee) {
        this.techNotPaidFee = techNotPaidFee;
    }

    public BigDecimal getTechOutcomeFee() {
        return techOutcomeFee;
    }

    public void setTechOutcomeFee(BigDecimal techOutcomeFee) {
        this.techOutcomeFee = techOutcomeFee;
    }

    public BigDecimal getTechPayFee() {
        return techPayFee;
    }

    public void setTechPayFee(BigDecimal techPayFee) {
        this.techPayFee = techPayFee;
    }

    public BigDecimal getTechNotPayFee() {
        return techNotPayFee;
    }

    public void setTechNotPayFee(BigDecimal techNotPayFee) {
        this.techNotPayFee = techNotPayFee;
    }

    public BigDecimal getCooperateIncomeFee() {
        return cooperateIncomeFee;
    }

    public void setCooperateIncomeFee(BigDecimal cooperateIncomeFee) {
        this.cooperateIncomeFee = cooperateIncomeFee;
    }

    public BigDecimal getCooperatePaidFee() {
        return cooperatePaidFee;
    }

    public void setCooperatePaidFee(BigDecimal cooperatePaidFee) {
        this.cooperatePaidFee = cooperatePaidFee;
    }

    public BigDecimal getCooperateNotPaidFee() {
        return cooperateNotPaidFee;
    }

    public void setCooperateNotPaidFee(BigDecimal cooperateNotPaidFee) {
        this.cooperateNotPaidFee = cooperateNotPaidFee;
    }

    public BigDecimal getCooperateOutcomeFee() {
        return cooperateOutcomeFee;
    }

    public void setCooperateOutcomeFee(BigDecimal cooperateOutcomeFee) {
        this.cooperateOutcomeFee = cooperateOutcomeFee;
    }

    public BigDecimal getCooperatePayFee() {
        return cooperatePayFee;
    }

    public void setCooperatePayFee(BigDecimal cooperatePayFee) {
        this.cooperatePayFee = cooperatePayFee;
    }

    public BigDecimal getCooperateNotPayFee() {
        return cooperateNotPayFee;
    }

    public void setCooperateNotPayFee(BigDecimal cooperateNotPayFee) {
        this.cooperateNotPayFee = cooperateNotPayFee;
    }

    public BigDecimal getOtherIncomeFee() {
        return otherIncomeFee;
    }

    public void setOtherIncomeFee(BigDecimal otherIncomeFee) {
        this.otherIncomeFee = otherIncomeFee;
    }

    public BigDecimal getOtherPaidFee() {
        return otherPaidFee;
    }

    public void setOtherPaidFee(BigDecimal otherPaidFee) {
        this.otherPaidFee = otherPaidFee;
    }

    public BigDecimal getOtherNotPaidFee() {
        return otherNotPaidFee;
    }

    public void setOtherNotPaidFee(BigDecimal otherNotPaidFee) {
        this.otherNotPaidFee = otherNotPaidFee;
    }

    public BigDecimal getOtherOutcomeFee() {
        return otherOutcomeFee;
    }

    public void setOtherOutcomeFee(BigDecimal otherOutcomeFee) {
        this.otherOutcomeFee = otherOutcomeFee;
    }

    public BigDecimal getOtherPayFee() {
        return otherPayFee;
    }

    public void setOtherPayFee(BigDecimal otherPayFee) {
        this.otherPayFee = otherPayFee;
    }

    public BigDecimal getOtherNotPayFee() {
        return otherNotPayFee;
    }

    public void setOtherNotPayFee(BigDecimal otherNotPayFee) {
        this.otherNotPayFee = otherNotPayFee;
    }

    public ProjectOperatorDTO getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(ProjectOperatorDTO roleMap) {
        this.roleMap = roleMap;
    }
}
