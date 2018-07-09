package com.maoding.projectcost.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by sandy on 2017/8/30.
 * 项目综合数据--费用部分数据
 */
public class ProjectAmountFee {

    public ProjectAmountFee(){
        sumIncome = new BigDecimal("0");
        sumPay = new BigDecimal("0");
        totalIncome = new BigDecimal("0");
        totalPay = new BigDecimal("0");
    }

    /**
     * 总收入(应该收入的)
     */
    private BigDecimal sumIncome;

    /**
     * 总支出(应该支付的)
     */
    private BigDecimal sumPay;

    /**
     * 总收入
     */
    private BigDecimal totalIncome;

    /**
     * 总支出
     */
    private BigDecimal totalPay;


    /**
     * 总应收未收
     */
    private BigDecimal totalNotPaid;

    /**
     * 总应付未付
     */
    private BigDecimal totalNotPay;

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
    }

    public BigDecimal getTotalNotPaid() {
        totalNotPaid = this.sumIncome.subtract(this.getTotalIncome());
        return totalNotPaid;
    }

    public void setTotalNotPaid(BigDecimal totalNotPaid) {
        this.totalNotPaid = totalNotPaid;
    }

    public BigDecimal getTotalNotPay() {
        totalNotPay = this.sumPay.subtract(this.getTotalPay());
        return totalNotPay;
    }

    public void setTotalNotPay(BigDecimal totalNotPay) {
        this.totalNotPay = totalNotPay;
    }
}
