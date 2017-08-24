package com.maoding.projectcost.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 项目费用详情
 * 类    名：ProjectCostDetailDTO
 * 类描述：费用收付款明显详情记录（支付方金额，收款方金额，发票信息等）
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午4:11:50
 */
public class ProjectCooperationCostTotalDTO {




    private BigDecimal unpaid;
    /**
     * 金额
     */
    private BigDecimal fee;

    /**
     * 回款金额
     */
    private BigDecimal backMoney;


    /**
     * 已收
     */
    private BigDecimal toTheMoney;

    /**
     * 总应收未收
     */
    private BigDecimal receivedUncollected;

    /**
     * 回款比例
     */
    private double feeProportion;

    private int totalFlag;

    private BigDecimal totalCost;

    private String costId;

    /**
     * 合作方组织
     */
    private String companyName ;

    /**
     * 付款方
     */
    private String fromCompanyId;
    /**
     * 收款方
     */
    private String toCompanyId;


    /**
     * 付款方经营负责人
     */
    private String isManager;

    /**
     * 收款方经营负责人
     */
    private String isManager2;

    private List<ProjectCostPointDataDTO> pointList = new ArrayList<ProjectCostPointDataDTO>();

    public ProjectCooperationCostTotalDTO(){
        this.fee=new BigDecimal(0);
        this.unpaid = new BigDecimal(0);
        this.backMoney=new BigDecimal(0);
        this.toTheMoney=new BigDecimal(0);
        this.receivedUncollected=new BigDecimal(0);
        this.feeProportion=0;

    }


    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    public BigDecimal getToTheMoney() {
        return toTheMoney;
    }

    public void setToTheMoney(BigDecimal toTheMoney) {
        this.toTheMoney = toTheMoney;
    }

    public BigDecimal getReceivedUncollected() {
        return receivedUncollected;
    }

    public void setReceivedUncollected(BigDecimal receivedUncollected) {
        this.receivedUncollected = receivedUncollected;
    }

    public double getFeeProportion() {
        return feeProportion;
    }

    public void setFeeProportion(double feeProportion) {
        this.feeProportion = feeProportion;
    }

    public BigDecimal getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(BigDecimal unpaid) {
        this.unpaid = unpaid;
    }

    public List<ProjectCostPointDataDTO> getPointList() {
        return pointList;
    }

    public void setPointList(List<ProjectCostPointDataDTO> pointList) {
        this.pointList = pointList;
    }

    public int getTotalFlag() {
        return totalFlag;
    }

    public void setTotalFlag(int totalFlag) {
        this.totalFlag = totalFlag;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

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

    public String getFromCompanyId() {
        return fromCompanyId;
    }

    public void setFromCompanyId(String fromCompanyId) {
        this.fromCompanyId = fromCompanyId;
    }

    public String getToCompanyId() {
        return toCompanyId;
    }

    public void setToCompanyId(String toCompanyId) {
        this.toCompanyId = toCompanyId;
    }

    public String getIsManager() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }

    public String getIsManager2() {
        return isManager2;
    }

    public void setIsManager2(String isManager2) {
        this.isManager2 = isManager2;
    }
}
