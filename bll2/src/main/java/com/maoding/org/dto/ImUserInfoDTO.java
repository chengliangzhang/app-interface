package com.maoding.org.dto;

import com.maoding.attach.dto.FilePathDTO;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.projectmember.dto.UserPositionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ImUserInfoDTO
 * 类描述：im头像携带信息DTO
 * 作    者：MaoSF
 * 日    期：2016年7月6日-下午5:38:15
 */
public class ImUserInfoDTO extends FilePathDTO {

	/**
	 * 公司id
	 */
    private String companyId;
    
    /**
     * 用户id
     */
    private String userId;

    /**
     * user中的姓名
     */
    private String userName;

    /**
     * 所在企业的成员名称
     */
    private String companyUserName;

    /**
     * 账号名字
     */
    private String accountName;
    
    /**
     * 手机号码(注册账号)
     */
    private String cellphone;

    /**
     * 邮箱（所在公司邮箱）
     */
    private String email;

    /**
     * 在项目中的职位（暂无使用）
     */
    List<UserPositionDTO> taskRoleList = new ArrayList<>();

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserPositionDTO> getTaskRoleList() {
        return taskRoleList;
    }

    public void setTaskRoleList(List<UserPositionDTO> taskRoleList) {
        this.taskRoleList = taskRoleList;
    }
}