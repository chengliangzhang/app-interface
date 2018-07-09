package com.maoding.org.service;

import com.maoding.commonModule.dto.QueryCopyRecordDTO;
import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.org.dto.*;
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
	 */
	CompanyUserDetailDTO getCompanyUserById(String id) throws Exception;

	CompanyUserDetailDTO selectCompanyUserById(String id) throws Exception;

	/**
	 * 方法描述：根据id查询带状态数据
	 */
	CompanyUserDetailDTO getCompanyUserByIdInterface(String id,String needUserStatus) throws Exception;

	/**
	 * 方法描述：组织人员查询
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 * @param param(orgId)【orgId组织Id，companyId 公司Id】
	 */
    List<CompanyUserDataDTO> getUserByOrgId(Map<String, Object> param) throws Exception;

	/**
	 * 方法描述：获取当前组织的人员（如果orgId为null，则查询整个组织的成员，如果orgId不为null，则查询某个部门的成员）
	 * 作   者：LY
	 * 日   期：2016/8/3 17:17
	 * @param  companyId 公司Id  orgId 组织Id
	 *
	 */
	List<CompanyUserDataDTO> getUserList(String companyId, String orgId) throws Exception;

	/**
	 * 获取整个departPath路径下节点下面的所有成员（companyId == orgId，则获取整个组织的成员)
	 */
	List<CompanyUserDataDTO> getDepartAllUser(String companyId, String departPath) throws Exception;

	/**
	 * 方法描述：获取抄送人的信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午2:28:06
	 */
	List<CompanyUserDataDTO> getCopyUser(QueryCopyRecordDTO query);

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


	void addUserToGroup(String userId, String groupId, String departId) throws Exception;

	/**
	 * 方法描述：根据权限id查询公司人员
	 * 作者：MaoSF
	 * 日期：2016/11/3
	 * @param:
	 * @return:
	 */
	List<CompanyUserDataDTO> getCompanyUserByPermissionId(Map<String, Object> param);


	/**
	 * 方法描述：查找当前公司所有人员
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 * @param companyId
	 * @return
	 */
    List<CompanyUserAppDTO> getCompanyUser(String companyId) throws Exception;
	/**
	 * 方法描述：查找当前公司所有人员(带工作状态)
	 * 作        者：ZCL
	 * 日        期：2018年4月17日
	 * @param companyId
	 * @return
	 */
    List<CompanyUserAppDTO> getCompanyUser(String companyId,String needUserStatus) throws Exception;

	/**
	 * 方法描述：查找当前公司所有人员排除自己（也排除指定人员）
	 * 作        者：zcl
	 * 日        期：2018/4/23
	 * @return 查询出的人员列表
	 */
    List<CompanyUserAppDTO> getCompanyUserExceptMe(QueryCompanyUserDTO query) throws Exception;

	/**
	 * 方法描述：查找指定人员
	 * 作        者：zcl
	 * 日        期：2018/4/23
	 * @param query 查询条件
	 * @return 查询出的人员列表
	 */
    List<CompanyUserAppDTO> listCompanyUser(QueryCompanyUserDTO query) throws Exception;


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

	/**
	 * 方法描述：根据companyUserId查询报销审批申请人列表
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	List<CompanyUserExpMemberDTO> getExpTaskMembers(Map<String, Object> paraMap) throws Exception;

	/**
	 * 方法描述：抄送给我的记录的审批人列表
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午4:24:08
	 */
	List<CompanyUserExpMemberDTO> getApplyExpUserForCopy(Map<String, Object> paraMap) throws Exception;


	/**
	 * 组织经营总监
	 */
	List<CompanyUserDataDTO> getOperatorManager(String companyId);

	/**
	 * 组织项目总监
	 */
	List<CompanyUserDataDTO> getDesignManager(String companyId);

	/**
	 * 财务出账(技术审查费）
	 */
	List<CompanyUserDataDTO> getFinancialManagerPayForTechnical(String companyId);

	/**
	 * 财务出账（合作设计费）
	 */
	List<CompanyUserDataDTO> getFinancialManagerPayForCooperation(String companyId);

	/**
	 * 财务出账（其他费用）
	 */
	List<CompanyUserDataDTO> getFinancialManagerPayForOther(String companyId);

	/**
	 * 财务出账（报销拨款）
	 */
	List<CompanyUserDataDTO> getFinancialManagerPayForExp(String companyId);

	/**
	 * 财务出账(费用拨款)
	 */
	List<CompanyUserDataDTO> getFinancialManagerPayForCost(String companyId);


	List<CompanyUserDataDTO> getFinancialManagerForReceive(String companyId);

	List<CompanyUserDataDTO> getFinancialManagerForPay(String companyId);

	/**
	 * 财务收款组（合同回款）
	 */
	List<CompanyUserDataDTO> getFinancialManagerReceiveForContract(String companyId);

	/**
	 * 财务收款组
	 */
	List<CompanyUserDataDTO> getFinancialManagerReceiveForTechnical(String companyId);

	/**
	 * 财务收款组
	 */
	List<CompanyUserDataDTO> getFinancialManagerReceiveForCooperation(String companyId);

	/**
	 * 财务收款组
	 */
	List<CompanyUserDataDTO> getFinancialManagerReceiveForOther(String companyId);

	/**
	 * 企业负责人
	 */
	CompanyUserDataDTO getOrgManager(String companyId);

}
