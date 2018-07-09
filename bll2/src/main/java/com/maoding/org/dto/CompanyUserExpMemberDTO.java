package com.maoding.org.dto;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyDTO
 * 类描述：创建合作伙伴DTO
 * 作    者：MaoSF
 * 日    期：2016年7月8日-上午11:47:57
 */
public class CompanyUserExpMemberDTO {


    /**
     * 当前用户在组织中的名称
     */
    private String companyUserName;

    /**
     * 当前用户在组织中的用户ID
     */
    private String companyUserId;

    /**
     * 用户Id
     */
    private String UserId;


    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
