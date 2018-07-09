package com.maoding.org.dao;

import com.maoding.commonModule.dto.QueryCopyRecordDTO;
import com.maoding.core.base.dao.BaseDao;
import com.maoding.org.dto.*;
import com.maoding.org.entity.CompanyUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyDao
 * 类描述：组织(公司）Dao
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:42:48
 */
public interface CompanyUserDao extends BaseDao<CompanyUserEntity>{

	/**
	 * 方法描述：根据userId和companyId查找人员（一个人在一个公司最多只存在一条记录)【用于添加人员】
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param userId
	 * @param companyId
	 * @return
	 */
    CompanyUserEntity getCompanyUserByUserIdAndCompanyId(String userId, String companyId);

	/**
	 * 方法描述：查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param companyId
	 * @return
	 */
    List<CompanyUserEntity> getCompanyUserByCompanyId(String companyId);
	/**
	 * 方法描述：根据id查询数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param id
	 * @return
	 */
	CompanyUserDetailDTO getCompanyUserById(String id) throws Exception;

	/**
	 * 方法描述：根据id查询数据（带状态）
	 * 作        者：ZCL
	 * 日        期：2018-4-19
	 * @param id companyUserId
	 * @return 带状态companyUser数据
	 */
	CompanyUserDetailDTO getCompanyUserWithStatusById(String id) throws Exception;

	/**
	 * 方法描述：组织人员查询
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(orgId)【orgId组织Id，companyId 公司Id】
	 */
    List<CompanyUserDataDTO> getUserByOrgId(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：组织人员查询
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * param  【departPath，companyId 公司Id】
	 */
	List<CompanyUserDataDTO> getDepartAllUser (Map<String,Object> param) throws Exception;

	/**
	 * 方法描述：获取抄送人的信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 */
	List<CompanyUserDataDTO> getCopyUser(QueryCopyRecordDTO query);

	/**
	 * 方法描述：组织人员部门及权限
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06

	 */
    List<UserDepartDTO> getCompanyUserDepartRole(String userId, String companyId);

	/**
	 * 方法描述：组织人员部门及权限Interface
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 */
    List<UserDepartDTO> getCompanyUserDepartRoleInterface(String userId, String companyId);

	/**
	 * 方法描述：查询用户所在组织列表及信息
	 * 作        者：Chenxj
	 * 日        期：2016年6月22日-下午4:14:49
	 * @param map{userId}
	 */
    List<Map<String, Object>>selectPersonalInGroupAndInfo(Map<String, Object> map)throws Exception;

	/**
	 * 根据userId查询CompanyuserEntity
     */
    List<CompanyUserEntity> getCompanyUserId(String userId);

	/**
	 * 方法描述：根据权限id查询公司人员
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	List<CompanyUserDataDTO> getCompanyUserByPermissionId(Map<String, Object> param);

	/**
	 * 方法描述：根据orgList查询人员的账号id
	 * 作者：MaoSF
	 * 日期：2016/12/8
	 */
	List<String> getUserByOrgIdForNotice(Map<String, Object> param);

	/***********************************v2新接口*********************************/
	/**
	 * 方法描述：查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
    List<CompanyUserAppDTO> getCompanyUser(Map<String, Object> param);
	/***********************************v2新接口*********************************/
	/**
	 * 方法描述：查找当前公司所有人员排除自己
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
    List<CompanyUserAppDTO> getCompanyUserExceptMe(QueryCompanyUserDTO query);

	/***********************************v2新接口*********************************/
	/**
	 * 方法描述：查找人员
	 * 作        者：ZCL
	 * 日        期：2018-4-24
	 */
    List<CompanyUserAppDTO> listCompanyUser(QueryCompanyUserDTO query);


	/**
	 * 方法描述：查询员工简单信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	CompanyUserAppDTO getCompanyUserByUserId(Map<String, Object> param);

	/**
	 * 方法描述：获取当前用户所在的团队及团对中的权限（用于组织切换）
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	List<CompanyRoleDataDTO> getCompanyRole(Map<String, Object> param);


	String getUserNameByCompanyIdAndUserId(String companyId,String userId);

	/**
	 * im用户信息
	 */
	ImUserInfoDTO getImUserInfo(ImUserInfoQueryDTO queryDTO);

	/**
	 * 根据companyUserId查询报销审批申请人列表
	 */
	List<CompanyUserExpMemberDTO> getExpTaskMembers(Map<String, Object> paraMap);

	/**
	 * 抄送给我的记录的审批人列表
	 */
	List<CompanyUserExpMemberDTO> getApplyExpUserForCopy(Map<String, Object> paraMap);

	String getUserName(String companyUserId);

	/**
	 * 方法描述：根据权限code查询人员
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	List<CompanyUserDataDTO> getCompanyUserByPermissionCode(Map<String, Object> param);
}
