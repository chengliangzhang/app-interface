package com.maoding.financial.entity;

import com.maoding.core.base.entity.BaseEntity;

import java.util.Date;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpMainEntity
 * 描    述 : 报销主表实体
 * 作    者 : LY
 * 日    期 : 2016/7/26 15:15
 */
public class ExpMainEntity extends BaseEntity {

    /**
     *  用户id
     */
    private String userId;


    /**
     * 报销日期
     */
    private Date expDate;

    /**
     * 审批状态(0:待审核，1:同意，2，退回,3:撤回,4:删除）
     */
    private String approveStatus;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 部门id
     */
    private String departId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 版本控制字段
     */
    private Integer versionNum;

    /**
     * 报销单号
     */
    private String expNo;

    /**
     * 0:没有任何操作，1:退回记录重新提交,2:新生成记录
     */
    private Integer expFlag;

    public Integer getExpFlag() {
        return expFlag;
    }

    public void setExpFlag(Integer expFlag) {
        this.expFlag = expFlag;
    }

    public String getExpNo() {
        return expNo;
    }

    public void setExpNo(String expNo) {
        this.expNo = expNo;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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
        this.approveStatus = approveStatus == null ? null : approveStatus.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId == null ? null : departId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

}