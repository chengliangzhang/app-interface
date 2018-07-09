package com.maoding.role.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.role.dto.RoleUserDTO;
import com.maoding.role.dto.SaveRoleUserDTO;
import com.maoding.role.dto.UserOrgRoleDTO;
import com.maoding.role.entity.RoleUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：RoleService
 * 类描述：用户角色权限Service
 * 作    者：MaoSF
 * 日    期：2016年7月11日-下午3:28:54
 */
public interface RoleUserService  extends BaseService<RoleUserEntity>{


	/**
	 * 方法描述：保存用户权限
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午5:23:51
	 * @param dto
	 * @return
	 */
	public AjaxMessage saveOrUserRole(SaveRoleUserDTO dto)  throws Exception;

	/**
	 * 方法描述：企业负责人，系统管理员 移交调用接口
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	AjaxMessage saveOrgManager(SaveRoleUserDTO dto) throws Exception;

	/**
	 * 方法描述：删除用户角色（根据userId，companyId，roleId）
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 * @param:
	 * @return:
	 */
	public AjaxMessage deleteRoleUser(Map<String,Object> param) throws Exception;

	void sendMessageForRole(List<String> userIdList, String companyId,String accountId) throws Exception;
	void sendMessageForRole(List<String> userIdList, String companyId) throws Exception;
}
