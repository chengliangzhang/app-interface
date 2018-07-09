package com.maoding.org.dto;

import com.maoding.core.util.StringUtil;

/**
 * Created by Idccapp22 on 2017/1/5.
 */
public class CompanyDataDTO {

    /**
     *组织id
     */
    private String id;
    /**
     *组织名称
     */
    private String companyName;

    /**
     *组织名称
     */
    private String realName;

    /**
     *组织简称
     */
    private String companyShortName;
    /**
     *组织类型
     */
    private String companyType;

    /**
     *公司
     */
    private String filePath;

    /**
     * 企业管理员名
     */
    private String systemManager;

    private int flag;//是否已经存在事业合伙人或许分公司（0：无，1：是）

    private String memo;

    /**
     * 是否是外部合作组织（1：是）
     */
    private int isOuterCooperator;

    /**
     * 1:存在，0：不存在
     */
    private int isOwnCompany;//当前人是否存在该公司

    /**
     * 别名（此字段不是对应数据表的字段）
     */
    private String aliasName;

    private String typeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        if(!StringUtil.isNullOrEmpty(this.aliasName)){
            companyName = aliasName;
        }
        this.companyName = companyName;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSystemManager() {
        return systemManager;
    }

    public void setSystemManager(String systemManager) {
        this.systemManager = systemManager;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getIsOuterCooperator() {
        return isOuterCooperator;
    }

    public void setIsOuterCooperator(int isOuterCooperator) {
        this.isOuterCooperator = isOuterCooperator;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public int getIsOwnCompany() {
        return isOwnCompany;
    }

    public void setIsOwnCompany(int isOwnCompany) {
        this.isOwnCompany = isOwnCompany;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
