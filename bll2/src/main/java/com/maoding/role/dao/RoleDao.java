package com.maoding.role.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.role.dto.RoleDataDTO;
import com.maoding.role.entity.RoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：RoleUserDao
 * 类描述：权限（dao）
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午5:20:47
 */
public interface RoleDao extends BaseDao<RoleEntity>{

	/**
	 * 方法描述：获取公司的所以角色权限
	 * 作        者：MaoSF
	 * 日        期：2016年7月12日-上午3:21:42
	 * @param companyId
	 * @return
	 */
    List<RoleEntity> getCompanyRole(String companyId);

	/**
	 * 方法描述：获取公司的所以角色权限
	 * 作        者：wrb
	 * 日        期：2016年11月6日-下午15:33:42
	 * @param companyId
	 * @return
	 */
    List<RoleDataDTO> getCompanyRoleDTO(String companyId);
	
	/**
	 * 方法描述：获取用户所在组织的权限
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午4:29:29
	 * @param userId
	 * @param orgId
	 * @return
	 */
    List<RoleEntity> getUserRolesByOrgId(String userId, String orgId);

	/**
	 * 方法描述：获取用户在某组织的权限code值，用于项目服务内容金额权限控制
	 * 作者：MaoSF
	 * 日期：2016/8/19
	 * @param:
	 * @return:
	 */
    String getUserRoleCodes(String userId, String orgId);
	/**
	 * 方法描述：根据角色名和公司Id获取人员Id
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午5:53:54
	 * @param param(userId,companyId,orgId)
	 * @return
	 */
    List<String> getUserIdByRoleName(Map<String, Object> param);

	/**
	 * 方法描述：获取最大index
	 * 作者：MaoSF
	 * 日期：2016/11/2
	 * @param:
	 * @return:
	 */
    int getMaxRoleIndex();
}
