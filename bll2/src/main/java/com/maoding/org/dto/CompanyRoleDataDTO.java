package com.maoding.org.dto;

import com.maoding.role.dto.OperatorDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyRoleDataDTO
 * 类描述：团队切换列表
 * 作    者：MaoSF
 * 日    期：2016年7月6日-下午5:38:15
 */
public class CompanyRoleDataDTO {

    /**公司用户id**/
    private String companyUserId;

    private String companyUserName;

	/**
	 * 公司id
	 */
    private String companyId;
    

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司简称
     */
    private String companyShortName;

    /**
     * 公司别名
     */
    private String companyNikeName;


    /**权限*/
    private String roleCodes;

    /**
     * logo
     */
    private String filePath;

    private String typeId;

    /**
     * 组织下操作权限标识
     */
    private OperatorDTO operatorRole;

    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
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

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getCompanyNikeName() {
        return companyNikeName;
    }

    public void setCompanyNikeName(String companyNikeName) {
        this.companyNikeName = companyNikeName;
    }

    public OperatorDTO getOperatorRole() {
        return operatorRole;
    }

    public void setOperatorRole(OperatorDTO operatorRole) {
        this.operatorRole = operatorRole;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}