package com.maoding.v2.financial.dto;

import java.math.BigDecimal;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : V2ExpDetailDTO
 * 描    述 : 报销明细DTO
 * 作    者 : LY
 * 日    期 : 2016/7/26 15:14
 */
public class V2ExpDetailDTO {
    /**
     * 支出类别父名称
     */
    private String expPName;

    /**
     * 支出类别子名称
     */
    private String expName;

    /**
     * 支出类别父子名称
     */
    private String expAllName;

    /**
     * 总金额
     */
    private BigDecimal totalExpAmount;

    /**
     * 报销主单id
     */
    private String mainId;

    /**
     * 款项用途
     */
    private String expUse;

    /**
     * 款项金额
     */
    private BigDecimal expAmount;


    /**
     * 款项金额
     */
    private String expAmountStr;

    /**
     * 关联项目id
     */
    private String projectId;

    /**
     * 关联项目name
     */
    private String projectName;

    public String getExpPName() {
        return expPName;
    }

    public void setExpPName(String expPName) {
        this.expPName = expPName;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpAllName() {
        return expAllName;
    }

    public void setExpAllName(String expAllName) {
        this.expAllName = expAllName;
    }

    public BigDecimal getTotalExpAmount() {
        return totalExpAmount;
    }

    public void setTotalExpAmount(BigDecimal totalExpAmount) {
        this.totalExpAmount = totalExpAmount;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getExpUse() {
        return expUse;
    }

    public void setExpUse(String expUse) {
        this.expUse = expUse;
    }

    public BigDecimal getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(BigDecimal expAmount) {
        this.expAmount = expAmount;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getExpAmountStr() {
        return expAmountStr;
    }

    public void setExpAmountStr(String expAmountStr) {
        this.expAmountStr = expAmountStr;
    }
}