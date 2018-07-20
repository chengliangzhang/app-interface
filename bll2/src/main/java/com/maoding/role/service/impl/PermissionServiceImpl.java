package com.maoding.role.service.impl;


import com.beust.jcommander.internal.Lists;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.constant.PermissionConst;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.TeamOperaterService;
import com.maoding.role.dao.PermissionDao;
import com.maoding.role.dto.OperatorDTO;
import com.maoding.role.dto.PermissionDTO;
import com.maoding.role.dto.PermissionEnum;
import com.maoding.role.entity.PermissionEntity;
import com.maoding.role.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：PermissionService
 * 类描述：权限表（dao）
 * 作    者：wrb
 * 日    期：2016年11月2日-上午11:38:47
 */
@Service("permissionService")
public class PermissionServiceImpl  extends GenericService<PermissionEntity> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TeamOperaterService teamOperaterService;

    /**
     * 方法描述：获取当前角色下下所有的权限Code
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     */
    @Override
    public String getPermissionCodeByUserId(Map<String, Object> map) {
        String companyId = (String)map.get("companyId");
        String typeId = companyService.getOrgTypeId(companyId);
        if(!StringUtil.isNullOrEmpty(typeId)){
            map.put("typeId",typeId);
        }else {
            map.remove("typeId");
        }
        return permissionDao.getPermissionCodeByUserId(map);
    }

    @Override
    public String getPermissionCodeByUserId(String companyId, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId",companyId);
        map.put("userId",userId);
        String permissionCode = getPermissionCodeByUserId(map);
        if(!StringUtil.isNullOrEmpty(permissionCode)){
            if(permissionCode.indexOf("project_charge_manage")>-1){
                permissionCode += ","+"1";
            }
            if(permissionCode.indexOf("finance_back_fee")>-1){
                permissionCode += ","+"2";
            }
        }
        return permissionCode;
    }

    @Override
    public OperatorDTO getPermissionOperator(Map<String, Object> map) {
        OperatorDTO operator = new OperatorDTO();
        String roleCodes = null;
        if(map.containsKey("roleCodes")){
            roleCodes = (String) map.get("roleCodes");
        }else {
            roleCodes = this.getPermissionCodeByUserId(map);
        }
        if(!StringUtil.isNullOrEmpty(roleCodes)){
            if (StringUtil.isContain(roleCodes, PermissionEnum.ORG_EDIT.getCode())) {
                operator.setOrgInfoEdit(1);
                operator.setBannerEdit(1);
            }
            String typeId = (String)map.get("typeId");
            if((StringUtil.isNullOrEmpty(typeId))
                    && teamOperaterService.getTeamOperaterByParam((String)map.get("companyId"),(String)map.get("userId"))!=null){
                operator.setOrgDisband(1);
            }
            if (StringUtil.isContain(roleCodes, PermissionEnum.SYS_ROLE_PERMISSION.getCode())) {
               // operator.setRoleManager(1);
            }
            if (StringUtil.isContain(roleCodes,  PermissionEnum.HR_ORG_SET.getCode())) {
                operator.setDepartEdit(1);
                operator.setCompanyUserEdit(1);
            }
            if (StringUtil.isContain(roleCodes, PermissionEnum.ORG_PARTNER.getCode())) {
                operator.setSubOrgCreate(1);
                operator.setSubOrgInvite(1);
                operator.setPartnerInvite(1);
                operator.setPartnerOrgCreate(1);
            }
            if (StringUtil.isContain(roleCodes, PermissionEnum.PROJECT_EDIT.getCode(),PermissionEnum.PROJECT_CHARGE_MANAGER.getCode(),PermissionEnum.PROJECT_CHARGE_PAID.getCode())) {
                operator.setProjectDocView(1);
                operator.setProjectDocModule(1);
                operator.setAllProjectView(1);

            }
        }
        operator.init();
        return operator;
    }

    @Override
    public boolean isOrgManager(String companyId, String userId) {
        return isContentPermission(companyId,userId, SystemParameters.ORG_MANAGER_PERMISSION_ID);
    }

    @Override
    public boolean isFinancial(String companyId, String userId) {
        if(isContentPermission(companyId,userId, SystemParameters.FINANCIAL_PERMISSION_ID)){
            return true;
        }else {
           return isContentPermission(companyId,userId, SystemParameters.FINANCIAL_PERMISSION_ID);
        }
    }

    @Override
    public boolean isFinancialReceive(String companyId, String userId) {
        return isContentPermission(companyId,userId, SystemParameters.FINANCIAL_RECEIVE_PERMISSION_ID);
    }

    @Override
    public boolean isFinancialManagerPayForTechnical(String companyId,String userId) {
        return isFinancial(companyId,userId);
    }

    @Override
    public boolean isFinancialManagerPayForCooperation(String companyId,String userId) {
        return isFinancial(companyId,userId);
    }

    @Override
    public boolean isFinancialManagerPayForOther(String companyId,String userId) {
        return isFinancial(companyId,userId);
    }

    @Override
    public boolean isFinancialManagerReceiveForContract(String companyId,String userId) {
        return isFinancialReceive(companyId,userId);
    }

    @Override
    public boolean isFinancialManagerReceiveForTechnical(String companyId,String userId) {
        return isFinancialReceive(companyId,userId);
    }

    @Override
    public boolean isFinancialManagerReceiveForCooperation(String companyId,String userId) {
        return isFinancialReceive(companyId,userId);
    }

    @Override
    public boolean isFinancialManagerReceiveForOther(String companyId,String userId) {
        return isContentPermission(companyId,userId, PermissionConst.OTHER_RECEIVE);
    }

    @Override
    public boolean haveProjectDeletePermission(String companyId, String userId) {
       // return isContentPermission(companyId,userId, PermissionConst.PROJECT_DELETE);
        return isContentPermission(companyId,userId, "54");
    }

    @Override
    public boolean haveProjectEditPermission(String companyId, String userId) {
       // return isContentPermission(companyId,userId, PermissionConst.PROJECT_EDIT);
        return isContentPermission(companyId,userId, "20");

    }
    @Override
    public boolean isOperatorManager(String companyId, String userId) {
        return isContentPermission(companyId,userId, SystemParameters.OPERATOR_MANAGER_PERMISSION_ID);
    }

    @Override
    public boolean isDesignManager(String companyId, String userId) {
        return isContentPermission(companyId,userId, SystemParameters.DESIGN_MANAGER_PERMISSION_ID);
    }

    private boolean isContentPermission(String companyId, String userId,String permissionId){
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        map.put("userId",userId);
        map.put("permissionIds", Lists.newArrayList(permissionId));
        String typeId = this.companyService.getOrgTypeId(companyId);
        if(!StringUtil.isNullOrEmpty(typeId)){
            map.put("typeId",typeId);
        }
        if(permissionDao.getCompanyUserIsHasPermission(map)>0){
            return true;
        }
        return false;
    }
}