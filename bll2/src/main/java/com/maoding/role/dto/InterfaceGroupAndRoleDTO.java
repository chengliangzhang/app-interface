package com.maoding.role.dto;


import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：组强以及角色
 * 类描述：InterfaceGroupAndRoleDTO
 * 作    者：ChenZJ
 * 日    期：2016年9月1日-下午3:22:50
 */
public class InterfaceGroupAndRoleDTO {
    /**公司用户id**/
    private String companyUserId;
    private String companyId;

    private String companyName;

    private String companyShortName;

    private String filePath;

    private String roleCodes;

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }

    List<InterfaceRoleDTO> interfaceRoleDTOList  = new ArrayList<InterfaceRoleDTO>();

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

    public List<InterfaceRoleDTO> getInterfaceRoleDTOList() {
        return interfaceRoleDTOList;
    }

    public void setInterfaceRoleDTOList(List<InterfaceRoleDTO> interfaceRoleDTOList) {
        this.interfaceRoleDTOList = interfaceRoleDTOList;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}