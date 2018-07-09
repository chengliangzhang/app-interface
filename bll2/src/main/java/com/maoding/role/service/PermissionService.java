package com.maoding.role.service;


import com.maoding.core.base.service.BaseService;
import com.maoding.role.dto.OperatorDTO;
import com.maoding.role.dto.PermissionDTO;
import com.maoding.role.entity.PermissionEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：PermissionService
 * 类描述：权限表（dao）
 * 作    者：wrb
 * 日    期：2016年11月2日-上午11:38:47
 */
public interface PermissionService extends BaseService<PermissionEntity> {

    /**
     * 方法描述：获取当前角色下下所有的权限Code
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     * @return:
     */
    String getPermissionCodeByUserId(Map<String,Object> map);
    String getPermissionCodeByUserId(String companyId,String userId);

    /**
     * 方法描述：获取当前角色下下所有的权限Code
     * 作者：MaoSF
     * 日期：2016/11/2
     * @param:userId，companyId
     */
    OperatorDTO getPermissionOperator(Map<String,Object> map);

    boolean isOrgManager(String companyId,String userId);

    boolean isFinancial(String companyId,String userId);

    boolean isFinancialReceive(String companyId,String userId);

    /**
     * 财务出账(技术审查费）
     */
    boolean isFinancialManagerPayForTechnical(String companyId,String userId);

    /**
     * 财务出账（合作设计费）
     */
    boolean isFinancialManagerPayForCooperation(String companyId,String userId);

    /**
     * 财务出账（其他费用）
     */
    boolean isFinancialManagerPayForOther(String companyId,String userId);

    /**
     * 财务收款组（合同回款）
     */
    boolean isFinancialManagerReceiveForContract(String companyId,String userId);

    /**
     * 财务收款组（技术审查费）
     */
    boolean isFinancialManagerReceiveForTechnical(String companyId,String userId);

    /**
     * 财务收款组（合作设计费）
     */
    boolean isFinancialManagerReceiveForCooperation(String companyId,String userId);

    /**
     * 财务收款组（其他费用）
     */
    boolean isFinancialManagerReceiveForOther(String companyId,String userId);

    boolean isOperatorManager(String companyId,String userId);

    boolean isDesignManager(String companyId,String userId);

    boolean haveProjectDeletePermission(String companyId,String userId);
    boolean haveProjectEditPermission(String companyId,String userId);
}