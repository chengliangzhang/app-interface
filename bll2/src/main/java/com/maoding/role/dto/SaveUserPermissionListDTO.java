package com.maoding.role.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：SaveUserPermissionListDTO
 * 类描述：公司人员权限DTO（从权限中添加成员）
 * 作    者：MaoSF
 * 日    期：2016年11月16日-下午5:38:15
 */
public class SaveUserPermissionListDTO extends BaseDTO {


    List<SaveUserPermission2DTO> userPermissionList = new ArrayList<SaveUserPermission2DTO>();

    public List<SaveUserPermission2DTO> getUserPermissionList() {
        return userPermissionList;
    }

    public void setUserPermissionList(List<SaveUserPermission2DTO> userPermissionList) {
        this.userPermissionList = userPermissionList;
    }
}