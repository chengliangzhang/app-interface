package com.maoding.org.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.Map;

/**
 * 深圳市卯丁技术有限公司
 * 作    者 : 张成亮
 * 日    期 : 2018/4/23 15:59
 * 描    述 : 获取与当前组织相关的组织人员（本公司、分公司、事业合伙人）并按指定的排序字段排序
 */
public class QueryCompanyUserDTO extends BaseDTO{
    /** 要查询的组织id */
    private String companyId;
    /** 要查询的项目id */
    private String projectId;
    /** 查询结果内忽略的用户id (accountId)*/
    private String ignoreUserId;
    /** 查询结果内忽略的用户组织成员id(companyUserId) */
    private String ignoreCompanyUserId;
    /** 查询结果的排序字段和排序方法 */
    private Map<String,Integer> orderCondition;

    /**
     * 部门id or 组织id
     */
    private String orgId;

    private String expMainId;//审批单号

    private String isExceptMe;

    private String companyUserId;

    public String getCompanyId() {
        String s = companyId;
        if (s == null){
            s = this.getAppOrgId();
        }
        return s;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getIgnoreUserId() {
        return ignoreUserId;
    }

    public void setIgnoreUserId(String ignoreUserId) {
        this.ignoreUserId = ignoreUserId;
    }

    public Map<String, Integer> getOrderCondition() {
        return orderCondition;
    }

    public void setOrderCondition(Map<String, Integer> orderCondition) {
        this.orderCondition = orderCondition;
    }

    public String getIgnoreCompanyUserId() {
        return ignoreCompanyUserId;
    }

    public void setIgnoreCompanyUserId(String ignoreCompanyUserId) {
        this.ignoreCompanyUserId = ignoreCompanyUserId;
    }

    public String getIsExceptMe() {
        return isExceptMe;
    }

    public void setIsExceptMe(String isExceptMe) {
        this.isExceptMe = isExceptMe;
    }

    public String getExpMainId() {
        return expMainId;
    }

    public void setExpMainId(String expMainId) {
        this.expMainId = expMainId;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
