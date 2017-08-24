package com.maoding.org.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.List;

/**
 * Created by idccapp21 on 2016/8/5.
 */
public class BatchCompanyUserDTO  extends BaseDTO {
    //导入的组织
    private String orgId;

    private List<CompanyUserTableDTO>compayUserList;

    public List<CompanyUserTableDTO> getCompayUserList() {
        return compayUserList;
    }

    public void setCompayUserList(List<CompanyUserTableDTO> compayUserList) {
        this.compayUserList = compayUserList;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
