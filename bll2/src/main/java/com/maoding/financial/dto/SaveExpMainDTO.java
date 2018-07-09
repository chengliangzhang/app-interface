package com.maoding.financial.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

public class SaveExpMainDTO extends BaseDTO {

    /**
     * 当前处理人
     */
    private String companyUserId;

    /**
     * 被选择的审核人id
     */
    private String auditPerson;

    /**
     * 版本控制字段
     */
    private Integer versionNum;

    /**
     * 需要抄送人的companyUserId
     */
    private List<String> ccCompanyUserList = new ArrayList<>();

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getAuditPerson() {
        return auditPerson;
    }

    public void setAuditPerson(String auditPerson) {
        this.auditPerson = auditPerson;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public List<String> getCcCompanyUserList() {
        return ccCompanyUserList;
    }

    public void setCcCompanyUserList(List<String> ccCompanyUserList) {
        this.ccCompanyUserList = ccCompanyUserList;
    }
}
