package com.maoding.hxIm.dto;


/**
 * Created by Idccapp21 on 2016/8/1.
 */
public class ImGroupCustomerUserDTO {

    /**
     * 组织Id
     */
    private String orgId;

    /**
     * 组织中的成员ID
     */
    private String companyUserId;

    private String accountId;

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public ImGroupCustomerUserDTO() {
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
