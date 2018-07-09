package com.maoding.financial.dto;

import com.maoding.attach.dto.FilePathDTO;

import java.util.Date;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpMainEntity
 * 描    述 : 报销主表DTO
 * 作    者 : LY
 * 日    期 : 2016/7/26 15:15
 */
public class ExpMainDataDTO extends FilePathDTO {

    private String id;

    /**
     *  用户id
     */
    private String companyUserId;

    /**
     * accountId
     */
    private String accountId;

    /**
     * 报销人
     */
    private String userName;

    /**
     * 报销日期
     */
    private Date expDate;

    /**
     * 审批状态(0:待审核，1:同意，2，退回,3:撤回,4:删除,5.审批中）
     */
    private String approveStatus;

    /**
     * 审批状态名称(0:待审核，1:同意，2，退回,3:撤回,4:删除,5.审批中）
     */
    private String approveStatusName;

    /**
     * 审核日期
     */
    private Date approveDate;

    /**
     * 用途说明
     */
    private String expUse;

    /**
     * 备注
     */
    private String remark;

    private String auditMessage;

    /**
     *  是否最新记录
     */
    private String isNew;

    /**
     * 记录 类型
     */
    private Integer type;

    /** 所在组织名称 */
    private String companyName;

    /** 当前处理人 所在组织id */
    private String companyId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveStatusName() {
        return approveStatusName;
    }

    public void setApproveStatusName(String approveStatusName) {
        this.approveStatusName = approveStatusName;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getExpUse() {
        return expUse;
    }

    public void setExpUse(String expUse) {
        this.expUse = expUse;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAuditMessage() {
        return auditMessage;
    }

    public void setAuditMessage(String auditMessage) {
        this.auditMessage = auditMessage;
    }
}