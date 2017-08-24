package com.maoding.org.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.role.dto.RoleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyDTO
 * 类描述：企业信息DTO
 * 作    者：MaoSF
 * 日    期：2016年7月8日-上午11:47:57
 */
public class OrgUserForCustomGroupDTO{

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<CompanyUserAppDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<CompanyUserAppDTO> userList) {
        this.userList = userList;
    }

    /**
	 * 企业名称
	 */
    private String companyName;

    /**
     * 企业id
     */
	private String companyId;

    /**
     * 企业内部人员
     * */
    private List<CompanyUserAppDTO> userList;
}
