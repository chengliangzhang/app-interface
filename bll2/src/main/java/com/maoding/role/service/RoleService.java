package com.maoding.role.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.role.dto.RoleDTO;
import com.maoding.role.dto.RoleDataDTO;
import com.maoding.role.entity.RoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：RoleService
 * 类描述：角色权限Service
 * 作    者：MaoSF
 * 日    期：2016年7月11日-下午3:28:54
 */
public interface RoleService  extends BaseService<RoleEntity>{

	/**
	 * 方法描述：保存自定义角色
	 * 作者：MaoSF
	 * 日期：2016/11/2
	 * @param:
	 * @return:
	 */
    AjaxMessage saveRole(RoleDTO dto) throws Exception;

	/**
	 * 方法描述：删除权限
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 * @param:
	 * @return:
	 */
    AjaxMessage deleteRole(String id, String companyId) throws Exception;
	/**
	 * 方法描述：获取公司的所以角色权限
	 * 作        者：MaoSF
	 * 日        期：2016年7月12日-上午3:21:42
	 * @param companyId
	 * @return
	 */
    List<RoleEntity> getCompanyRole(String companyId) throws Exception;

	/**
	 * 方法描述：获取公司的所以角色权限
	 * 作        者：wrb
	 * 日        期：2016年11月6日-下午15:33:42
	 * @param companyId
	 * @return
	 */
    List<RoleDataDTO> getCompanyRoleDTO(String companyId) throws Exception;

	/**
	 * 方法描述：根据组织获取用户的角色
	 * 作者：MaoSF
	 * 日期：2016/8/16
	 * @param:
	 * @return:
	 */
    List<RoleEntity> getRoleByUser(String userId, String orgId) throws Exception;

	/**
	 * 方法描述：根据组织获取用户的角色
	 * 作者：MaoSF
	 * 日期：2016/8/16
	 * @param:
	 * @return:
	 */
    List<RoleDTO> getRoleByUserToDTO(String userId, String orgId) throws Exception;


	/**
	 * 方法描述：根据角色名和组织Id查询人员Id
	 * 作者：MaoSF
	 * 日期：2016/8/16
	 * @param:
	 * @return:
	 */
    List<String> getUserIdByRoleName(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：获取当前公司角色-权限-人员总览数据
	 * 作者：MaoSF
	 * 日期：2016/11/15
	 * @param:
	 * @return:
	 */
    List<RoleDataDTO> getRolePermissionUser(String companyId) throws Exception;



	/**
	 * 方法描述：
	 * 作者：MaoSF
	 * 日期：2016/11/15
	 * @param:
	 * @return:
	 */
	List<RoleDataDTO> getRolePermissionByUserId(String companyId, String userId) throws Exception;
}
