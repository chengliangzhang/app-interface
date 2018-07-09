package com.maoding.role.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.role.dto.OrgRoleTypeDTO;
import com.maoding.role.dto.RoleDTO;
import com.maoding.role.dto.RoleDataDTO;
import com.maoding.role.entity.RoleEntity;
import org.springframework.web.bind.annotation.RequestBody;

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
	 */
    AjaxMessage deleteRole(String id, String companyId) throws Exception;

	/**
	 * 方法描述：获取公司的所以角色权限
	 * 作        者：wrb
	 * 日        期：2016年11月6日-下午15:33:42
	 */
    List<RoleDataDTO> getCompanyRoleDTO(String companyId) throws Exception;

	/**
	 * 方法描述：获取当前公司角色-权限-人员总览数据
	 * 作者：MaoSF
	 * 日期：2016/11/15
	 */
    List<RoleDataDTO> getRolePermissionUser(String companyId) throws Exception;
    List<RoleDataDTO> getRolePermissionUserAndSysManager(String companyId) throws Exception;

	/**
	 * 方法描述：
	 * 作者：MaoSF
	 * 日期：2016/11/15
	 */
	List<RoleDataDTO> getRolePermissionByUserId(String companyId, String userId) throws Exception;

	List<OrgRoleTypeDTO> getOrgRoleList();

	OrgRoleTypeDTO getOrgRoleByType(String typeId);

	/**
	 * 方法描述：根据不同的类型，获取权限
	 * 作者：MaoSF
	 * 日期：2016/11/15
	 */
	List<RoleDTO> getRolePermissionByType(String type) throws Exception;

	/**
	 * 获取当前人所在的组织及部门及权限
	 */
	List<Object> getCompanyDepartAndPermission(String userId,String companyId) throws Exception;
}
