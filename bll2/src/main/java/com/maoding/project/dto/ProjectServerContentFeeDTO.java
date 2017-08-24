package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.math.BigDecimal;
/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectServerContentFeeDTO
 * 类描述：服务内容费用DTO（技术审查费，合作设计费）
 * 作    者：MaoSF
 * 日    期：2016年9月6日-下午5:49:50
 */
public class ProjectServerContentFeeDTO extends BaseDTO {

    /**
     * 服务内容id
     */
    private String serverContentId;

    /**
     * 金额
     */
    private BigDecimal fee;

    /**
     * 备注
     */
    private String memo;

    /**
     * 费用类型（1：合作设计费，2，技术审查费）
     */
    private String type;

    /**
     * 排序
     */
    private int seq;

    /**
     * 审核日期
     */
    private String auditDate;

    /**
     * 审核状态
     */
    private String auditStatus;

    /**
     * 付款日期
     */
    private String payDate;

    /**
     * 到款日期
     */
    private String paidDate;

    /**
     * 付款备注
     */
    private String payRemark;

    /**
     * 到款备注
     */
    private String paidRemark;

    /**
     * 服务内容名称
     */
    private String serverName;

    /**
     * 审批类型
     */
    private int  auditType;//审批类型：审核：1,付款：2，到款：3

    /**
     * 审核人
     */
    private String auditUserName;

    /**
     * 付款人
     */
    private String payUserName;

    /**
     * 收款人
     */
    private String paidUserName;



    public String getServerContentId() {
        return serverContentId;
    }

    public void setServerContentId(String serverContentId) {
        this.serverContentId = serverContentId == null ? null : serverContentId.trim();
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }

    public String getPaidRemark() {
        return paidRemark;
    }

    public void setPaidRemark(String paidRemark) {
        this.paidRemark = paidRemark;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getAuditType() {
        return auditType;
    }

    public void setAuditType(int auditType) {
        this.auditType = auditType;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public String getPayUserName() {
        return payUserName;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }

    public String getPaidUserName() {
        return paidUserName;
    }

    public void setPaidUserName(String paidUserName) {
        this.paidUserName = paidUserName;
    }
}