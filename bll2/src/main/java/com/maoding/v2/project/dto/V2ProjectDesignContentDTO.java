package com.maoding.v2.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.project.dto.ProjectDesignContentDetailDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：V2ProjectDesignContentDTO
 * 作    者：ChenZJ
 * 日    期：2016年12/27
 */
public class V2ProjectDesignContentDTO extends BaseDTO {
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 企业id
     */
    private String companyId;
    /**
     * 设计阶段id(数据字典)
     */
    private String contentId;
    /**
     * 设计阶段名称
     */
    private String contentName;
    /**
     * 设计管理费
     */
    private BigDecimal manageFee;
    /**
     * 到款确认日期
     */
    private String confirmDate;
    /**
     * 到款确认日期
     */
    private String paidConfirm;
    /**
     * 计划进度开始时间
     */
    private String planProgressStarttime;
    /**
     * 计划进度结束时间
     */
    private String planProgressEndtime;

    /**
     * 完成比例
     */
    private String completeRatio;

    /**
     * 完成时间超过计划时间的天数
     */
    private int overDate;
    /**
     * 完成时间
     */
    private String progressDate;
    /**
     * 进度状态(0=未完成,1=完成)
     */
    private String progressStatus;
    /**
     * 是否审查(0.未审查，1.已审查)
     */
    private String isMap;
    /**
     * 审查实际时间
     */
    private String mapDate;
    /**
     * 审查勾选的系统时间
     */
    private String currentMapDate;
    /**
     * 排序字段
     */
    private String designContentSeq;
    /**
     * 备忘录
     */
    private String memo;
    /**
     * 合同进度开始时间
     */
    private String contractProgressStarttime;
    /**
     * 合同进度结束时间
     */
    private String contractProgressEndtime;

    /**
     * 对应服务内容中最小的开始时间
     */
    private String minProgressStarttime;

    /**
     * 对应服务内容中最大的结束时间
     */
    private String maxProgressEndtime;

    /**
     * 1：正常完成，2：超期完成，3：正常进行，4：超期进行，5：无状态
     */
    private int designContentActualStatus;



    /**
     * 变更明细
     */
    private List<ProjectDesignContentDetailDTO> projectDesignContentDetailList = new ArrayList<ProjectDesignContentDetailDTO>();

    private String companyBidFlag;//和projectDTO中的companyBidFlag一样。用于数据传递

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

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public BigDecimal getManageFee() {
        return manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getPaidConfirm() {
        return paidConfirm;
    }

    public void setPaidConfirm(String paidConfirm) {
        this.paidConfirm = paidConfirm;
    }

    public String getPlanProgressStarttime() {
        return planProgressStarttime;
    }

    public void setPlanProgressStarttime(String planProgressStarttime) {
        this.planProgressStarttime = planProgressStarttime;
    }

    public String getPlanProgressEndtime() {
        return planProgressEndtime;
    }

    public void setPlanProgressEndtime(String planProgressEndtime) {
        this.planProgressEndtime = planProgressEndtime;
    }

    public String getCompleteRatio() {
        return completeRatio;
    }

    public void setCompleteRatio(String completeRatio) {
        this.completeRatio = completeRatio;
    }

    public int getOverDate() {
        return overDate;
    }

    public void setOverDate(int overDate) {
        this.overDate = overDate;
    }

    public String getProgressDate() {
        return progressDate;
    }

    public void setProgressDate(String progressDate) {
        this.progressDate = progressDate;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getIsMap() {
        return isMap;
    }

    public void setIsMap(String isMap) {
        this.isMap = isMap;
    }

    public String getMapDate() {
        return mapDate;
    }

    public void setMapDate(String mapDate) {
        this.mapDate = mapDate;
    }

    public String getCurrentMapDate() {
        return currentMapDate;
    }

    public void setCurrentMapDate(String currentMapDate) {
        this.currentMapDate = currentMapDate;
    }

    public String getDesignContentSeq() {
        return designContentSeq;
    }

    public void setDesignContentSeq(String designContentSeq) {
        this.designContentSeq = designContentSeq;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getContractProgressStarttime() {
        return contractProgressStarttime;
    }

    public void setContractProgressStarttime(String contractProgressStarttime) {
        this.contractProgressStarttime = contractProgressStarttime;
    }

    public String getContractProgressEndtime() {
        return contractProgressEndtime;
    }

    public void setContractProgressEndtime(String contractProgressEndtime) {
        this.contractProgressEndtime = contractProgressEndtime;
    }

    public String getMinProgressStarttime() {
        return minProgressStarttime;
    }

    public void setMinProgressStarttime(String minProgressStarttime) {
        this.minProgressStarttime = minProgressStarttime;
    }

    public String getMaxProgressEndtime() {
        return maxProgressEndtime;
    }

    public void setMaxProgressEndtime(String maxProgressEndtime) {
        this.maxProgressEndtime = maxProgressEndtime;
    }

    public int getDesignContentActualStatus() {
        return designContentActualStatus;
    }

    public void setDesignContentActualStatus(int designContentActualStatus) {
        this.designContentActualStatus = designContentActualStatus;
    }

    public List<ProjectDesignContentDetailDTO> getProjectDesignContentDetailList() {
        return projectDesignContentDetailList;
    }

    public void setProjectDesignContentDetailList(List<ProjectDesignContentDetailDTO> projectDesignContentDetailList) {
        this.projectDesignContentDetailList = projectDesignContentDetailList;
    }

    public String getCompanyBidFlag() {
        return companyBidFlag;
    }

    public void setCompanyBidFlag(String companyBidFlag) {
        this.companyBidFlag = companyBidFlag;
    }
}
