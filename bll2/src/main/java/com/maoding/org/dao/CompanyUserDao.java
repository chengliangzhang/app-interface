package com.maoding.org.dao;

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
	 * 方法描述：查询不在群组当中的公司人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param companyId
	 * @return
	 */
    List<CompanyUserEntity> getnotInGroupCompanyUserByCompanyId(String companyId);


	/**
	 * 方法描述： 根据参数查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param param
	 * @return
	 */
    List<CompanyUserEntity> getCompanyUserByParam(Map<String, Object> param);
	/**
	 * 方法描述：根据id查询数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param id
	 * @return
	 */
    CompanyUserTableDTO getCompanyUserById(String id) throws Exception;
	
	/**
	 * 方法描述：组织人员查询（分页查询）（以前的，攒无使用，递归查询子节点中人）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(orgId,keyword,startPage,endPage)【orgId必传，startPage,endPage分页，不传，则查询所有】
	 * @return
	 * @throws Exception
	 */
    List<CompanyUserTableDTO> getCompanyUserByOrgId(Map<String, Object> param) throws Exception;


	/**
	 * 方法描述：组织人员条数（分页查询）（以前的，攒无使用，递归查询子节点中人的数）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param (orgId,keyword)【orgId必传】
	 * @return
	 * @throws Exception
	 */
    int getCompanyUserByOrgIdCount(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：组织人员查询（分页查询）(admin项目）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(orgId,keyword,startPage,endPage)【orgId必传，startPage,endPage分页，不传，则查询所有】
	 * @return
	 * @throws Exception
	 */
    List<CompanyUserTableDTO> getCompanyUserByOrgIdOfAdmin(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：组织人员查询（分页查询）（work项目）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(orgId,keyword,startPage,endPage)【orgId必传，startPage,endPage分页，不传，则查询所有】
	 * @return
	 * @throws Exception
	 */
    List<CompanyUserTableDTO> getCompanyUserByOrgIdOfWork(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：组织人员查询
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(orgId)【orgId组织Id，companyId 公司Id】
	 */
    List<CompanyUserDataDTO> getUserByOrgId(Map<String, Object> param) throws Exception;
	

	/**
	 * 方法描述：组织人员条数（分页查询）（work项目）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param (orgId,keyword)【orgId必传】
	 * @return
	 * @throws Exception
	 */
    int getCompanyUserByOrgIdCountOfWork(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：组织人员条数（分页查询）（admin项目）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param (orgId,keyword)【orgId必传】
	 * @return
	 * @throws Exception
	 */
    int getCompanyUserByOrgIdCountOfAdmin(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：组织人员部门及权限
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param userId
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
    List<UserDepartDTO> getCompanyUserDepartRole(String userId, String companyId);

	/**
	 * 方法描述：组织人员部门及权限Interface
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param userId
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
    List<UserDepartDTO> getCompanyUserDepartRoleInterface(String userId, String companyId);
	
	/**
	 * 方法描述：企业未激活人员查询（分页查询）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(companyId,startPage,endPage)【companyId必传，startPage,endPage分页，不传，则查询所有】
	 * @return
	 * @throws Exception
	 */
    List<CompanyUserTableDTO> getCompanyUserOfNotActive(Map<String, Object> param) throws Exception;
	
	/**
	 * 方法描述：：企业未激活人员条数（分页查询）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param (companyId)【companyId必传】
	 * @return
	 * @throws Exception
	 */
    int getCompanyUserOfNotActiveCount(Map<String, Object> param) throws Exception;


	/**
	 * 方法描述：查询用户所在组织列表及信息
	 * 作        者：Chenxj
	 * 日        期：2016年6月22日-下午4:14:49
	 * @param map{userId}
	 * @return
	 */
    List<Map<String, Object>>selectPersonalInGroupAndInfo(Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：根据id集合查询人员名字（任务分解--负责人，设计人）使用
	 * 作        者：MaoSF
	 * 日        期：2016年4月23日-下午6:02:15
	 * @param param
	 * @return
	 */
    List<CompanyUserEntity> getPersonByIds(Map<String, Object> param);
	/**
	 * 方法描述：查询部门用户
	 * 作        者：MaoSF
	 * 日        期：2016年4月23日-下午6:02:15
	 * @param param
	 * @return
	 */
    List<CompanyUserEntity> getUserByDepartId(Map<String, Object> param);

	/**
	 * 方法描述：查询不在群组当中的部门用户
	 * 作        者：MaoSF
	 * 日        期：2016年4月23日-下午6:02:15
	 * @param param
	 * @return
	 */
    List<CompanyUserEntity> getNotInGroupUserByDepartId(Map<String, Object> param);

	/**
	 * 根据userId查询CompanyuserEntity
	 * @param userId
	 * @return
     */
    List<CompanyUserEntity> getCompanyUserId(String userId);



	/**
	 * 方法描述：根据角色id查询公司人员
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 * @param:
	 * @return:
	 */
	List<CompanyUserTableDTO> getCompanyUserByRoleId(Map<String, Object> param);

	/**
	 * 方法描述：根据权限id查询公司人员
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 * @param:
	 * @return:
	 */
	List<CompanyUserTableDTO> getCompanyUserByPermissionId(Map<String, Object> param);

	/**
	 * 方法描述：根据orgList查询人员的账号id
	 * 作者：MaoSF
	 * 日期：2016/12/8
	 * @param:
	 * @return:
	 */
	List<String> getUserByOrgIdForNotice(Map<String, Object> param);

	/***********************************v2新接口*********************************/
	/**
	 * 方法描述：查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param param
	 * @return
	 */
    List<CompanyUserAppDTO> getCompanyUser(Map<String, Object> param);
	/***********************************v2新接口*********************************/
	/**
	 * 方法描述：查找当前公司所有人员排除自己
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param param
	 * @return
	 */
    List<CompanyUserAppDTO> getCompanyUserExceptMe(Map<String, Object> param);


	/**
	 * 方法描述：查询员工简单信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param param
	 * @return
	 */
	CompanyUserAppDTO getCompanyUserByUserId(Map<String, Object> param);



	/**
	 * 人员根据公司用户id来查询app用户信息
	 * @param companyUserId
	 * @return
     */
    List<Map<String, Object>> getAppCompanyUser(String companyUserId);


	/**
	 * 方法描述：获取当前用户所在的团队及团对中的权限（用于组织切换）
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 * @param:
	 * @return:
	 */
	List<CompanyRoleDataDTO> getCompanyRole(Map<String, Object> param);


	/**
	 * 方法描述：根据companyList查询人员的账号id
	 * 作者：MaoSF
	 * 日期：2016/12/8
	 * @param:
	 * @return:
	 */
	List<String> getUserByCompanyForSendMsg(Map<String, Object> param);


	String getUserNameByCompanyIdAndUserId(String companyId,String userId);

	/**
	 * im用户信息
	 * @param queryDTO
	 */
	ImUserInfoDTO getImUserInfo(ImUserInfoQueryDTO queryDTO);
}
