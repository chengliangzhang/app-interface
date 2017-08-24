package com.maoding.org.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.org.dto.*;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyUserService
 * 类描述：组织（公司）用户  Service
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:22:43
 */
public interface CompanyUserService extends BaseService<CompanyUserEntity>{

	/**
	 * 方法描述：根据userId和companyId查找人员（一个人在一个公司只存在一条记录）
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param userId
	 * @param companyId
	 * @return
	 */
    CompanyUserEntity getCompanyUserByUserIdAndCompanyId(String userId, String companyId) throws Exception;

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
    CompanyUserTableDTO getCompanyUserById(String id) throws Exception;

	/**
	 * 方法描述：根据id查询数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param id
	 * @return
	 */
    CompanyUserTableDTO getCompanyUserByIdInterface(String id) throws Exception;
	
	/**
	 * 方法描述：组织人员查询（分页查询）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(orgId,keyword,startPage,endPage)【orgId必传，startPage,endPage分页，不传，则查询所有】
	 * @return
	 * @throws Exception
	 */
    List<CompanyUserTableDTO> getCompanyUserByOrgIdOfAdmin(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：组织人员查询（分页查询）
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
	 * 方法描述：组织人员条数（分页查询）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param (orgId,keyword)【orgId必传】
	 * @return
	 * @throws Exception
	 */
    int getCompanyUserByOrgIdCountOfWork(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：保存组织人员（新增，更改）
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:22:53
	 * @param dto
	 * @return
	 */
    AjaxMessage saveCompanyUser(CompanyUserTableDTO dto) throws Exception;

	/**
	 * 方法描述：添加/移除用户到团队群,
	 * 作者：MaoSF
	 * 日期：2016/11/29
	 * @param:orgId(组织id，公司或许部门），userId，auditStatus：成员的状态（1有效，4：离职）
	 * @return:
	 */
    void sendCompanyUserMessageFun(String orgId, String userId, String auditStatus);

	/**
	 * 方法描述：离职处理
	 * 作        者：MaoSF
	 * 日        期：2016年3月23日-下午6:35:02
	 * @param id
	 * @return
	 */
    AjaxMessage quit(String id) throws Exception;


	/**
	 * 方法描述：查询用户所在组织列表及信息
	 * 作        者：Chenxj
	 * 日        期：2016年6月22日-下午4:14:49
	 * @param map{userId}
	 * @return
	 */
    List<Map<String, Object>> selectPersonalInGroupAndInfo(Map<String, Object> map) throws Exception;

	/**
	 * 方法描述：
	 * 作者：MaoSF
	 * 日期：2016/8/5
	 * @param:
	 * @return:
	 */
    AjaxMessage orderCompanyUser(CompanyUserTableDTO dto1, CompanyUserTableDTO dto2, String orgId)throws Exception;


	/**
	 * 方法描述：根据id集合查询人员名字（任务分解--负责人，设计人）使用
	 * 作        者：MaoSF
	 * 日        期：2016年4月23日-下午6:02:15
	 * @param param
	 * @return
	 */
    List<CompanyUserEntity> getPersonByIds(Map<String, Object> param);



	void addUserToGroup(String userId, String groupId, String departId) throws Exception;

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
	 * 方法描述：查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param companyId
	 * @return
	 */
    List<CompanyUserAppDTO> getCompanyUser(String companyId) throws Exception;

	/**
	 * 方法描述：查找当前公司所有人员排除自己
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param companyId
	 * @return
	 */
    List<CompanyUserAppDTO> getCompanyUserExceptMe(String companyId,String accountId) throws Exception;


	/**
	 * 方法描述：获取当前用户所在的团队及团对中的权限（用于组织切换）
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 * @param:
	 * @return:
	 */
	List<CompanyRoleDataDTO> getCompanyRole(Map<String, Object> param) throws Exception;


	/**
	 * 方法描述：im头像点击进去，查看个人信息
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 */
	ImUserInfoDTO getImUserInfo(ImUserInfoQueryDTO query) throws Exception;
}
