package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectSaveDTO
 * 类描述：经营列表DTO
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午4:35:50
 */
public class ProjectTableDTO extends BaseDTO {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 乙方
     */
    private String companyBid;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 合同签订日期
     */
    private String signDate;

    /**
     * 合同总金额
     */
    private BigDecimal totalContractAmount;

    /**
     * 组织Id
     */
    private String companyId;

    /**
     * 项目承接人
     */
    private String companyName;

    /**
     * 设计人（接包人）
     */
    private String designCompanyName;

    /**
     * 发包人
     */
    private String fromCompanyName;

    /**
     * 乙方
     */
    private String partyB;

    /**
     * 甲方（建设单位）
     */
    private String partyA;

    private String parentProjectid;

    private String status;

    private Date createDate;

    private String createBy;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyBid() {
        return companyBid;
    }

    public void setCompanyBid(String companyBid) {
        this.companyBid = companyBid;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public BigDecimal getTotalContractAmount() {
        return totalContractAmount;
    }

    public void setTotalContractAmount(BigDecimal totalContractAmount) {
        this.totalContractAmount = totalContractAmount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParentProjectid() {
        return parentProjectid;
    }

    public void setParentProjectid(String parentProjectid) {
        this.parentProjectid = parentProjectid;
    }

    public String getDesignCompanyName() {
        return designCompanyName;
    }

    public void setDesignCompanyName(String designCompanyName) {
        this.designCompanyName = designCompanyName;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public String getFromCompanyName() {
        return fromCompanyName;
    }

    public void setFromCompanyName(String fromCompanyName) {
        this.fromCompanyName = fromCompanyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
