package com.maoding.org.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.org.dto.*;
import com.maoding.org.entity.CompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyService
 * 类描述：组织（公司）Service
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:22:43
 */
public interface CompanyService extends BaseService<CompanyEntity>{

	/**
	 * 保存公司(创建组织)
	 */
	String saveCompany(CompanyEntity company,  String userName,String orgManagerId,String currUserId)throws Exception;

	/**
	 * 方法描述：新增或修改公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-上午9:47:25
	 */
	AjaxMessage saveOrUpdateCompany(CompanyDTO dto) throws Exception;

	/**
	 * 方法描述：增加分公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 */
	AjaxMessage createSubCompany(SubCompanyDTO dto)throws Exception;

	/**
	 * 方法描述：修改分公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 */
	AjaxMessage updateSubCompany(SubCompanyDTO dto)throws Exception;

	/**
	 * 方法描述：删除分公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 * param orgId(分公司id)
	 * param orgPid(当前公司id)
	 */
	AjaxMessage deleteSubCompany(String orgPid, String orgId)throws Exception;

	/**
	 * 方法描述：增加合伙人
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 */
	ResponseBean createBusinessPartner(BusinessPartnerDTO dto)throws Exception;

	/**
	 * 方法描述：删除事业合伙人
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 * param orgId(分公司id)
	 * param orgPid(当前公司id)
	 */
	AjaxMessage deleteBusinessPartner(String orgPid, String orgId)throws Exception;

	/**
	 * 方法描述：修改合伙人
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 */
	AjaxMessage updateBusinessPartner(BusinessPartnerDTO dto)throws Exception;
	
	/**
	 * 方法描述：根据id查询（转化成DTO）
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午3:27:28
	 */
	CompanyDTO getCompanyById(String id)throws Exception;


	CompanyDTO selectCompanyById(String id)throws Exception;

	/**
	 * 方法描述：获取所有企业（组织切换列表）return(id,companyName,companyType,companyShortName,status,filePath)
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午2:03:16
	 * return(id,companyName,companyType,companyShortName,status,filePath)
	 */
	List<CompanyDTO> getCompanyByUserId(String userId);


	/**
	 * 方法描述：获取组织架构树信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午6:42:43
	 */
	OrgTreeDTO getOrgTree(Map<String, Object> map) throws Exception;

	/**
	 * 方法描述：根据公司id查找最顶级公司的id
	 * 作者：MaoSF
	 * 日期：2016/12/8
	 */
	String getRootCompanyId(String id) ;

	/**
	 * 方法描述：查询线上公司
	 * 作者：MaoSF
	 * 日期：2016/11/11
	 */
	CompanyDTO getLineCompanyByCompanyId(String id)throws Exception;

	/***************过滤以挂靠的组织**************/
	/**
	 * 方法描述：查询（未挂靠组织的组织），大B搜索小b，传pid=当前公司，小b搜索大B传companyId=当前公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 */
	List<CompanyDTO> getCompanyFilterbyParam(Map<String, Object> map) throws Exception;
	
	/**
	 * 方法描述：查询（未挂靠组织的组织）条数，大B搜索小b，传pid=当前公司，小b搜索大B传companyId=当前公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 */
	int getCompanyFilterbyParamCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 方法描述：查询（未挂靠组织的组织）分页，大B搜索小b，传pid=当前公司，小b搜索大B传companyId=当前公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 */
	Map getCompanyFilterbyParamPage(Map<String, Object> map) throws Exception;

	/**
	 * 方法描述：查询从未挂靠的组织（包含被邀请没有同意的）
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 */
	Map<String,Object> getCompanyFilterbyParamForInvit(Map<String, Object> map) throws Exception;
	
	/**
	 * 方法描述：解散组织
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:01:41
	 */
	AjaxMessage disbandCompany(String companyId)throws Exception;


	/**
	 * 方法描述：根据当前id,查找所有的父节点
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:01:41
	 */
	List<CompanyEntity> getAllParentCompanyList(String id)throws Exception;

	/**
	 * 方法描述：根据当前id,查找所有的子节点
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:01:41
	 */
	List<CompanyEntity> getAllChilrenCompany(String id) throws Exception;

	/**
	 * 获取当前组织所在的整个组织架构（所有组织)
	 */
	List<CompanyEntity> getAllOrg(String id) throws Exception;

	/**
	 * 从当前组织为根节点，以及所有的子公司
	 */
	List<CompanyEntity> getOwnAndChildOrg(String id) throws Exception;

	/*********************************v2接口************************************************/
	/**
	 * 方法描述：返回选择团队列表（分公司，合作伙伴，所有公司），任务转发给其他公司，团队选择
	 * 作者：MaoSF
	 * 日期：2017/1/5
	 */
	Map<String,Object> getCompanyForSelect(String id,String projectId)throws Exception;

	/**
	 * 方法描述：组织架构数据
	 * 作者：MaoSF
	 * 日期：2017/1/13
	 */
	Map<String,Object> getLinkPeopleAndGroup(Map<String, Object> map) throws Exception;

	/**
	 * 方法描述：获取邀请有关的
	 * 作者：chenzhujie
	 * 日期：2017/1/16
	 */
    Map<String,Object> getInviteAbout(Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：事业合伙人加入的界面（数据查询，查询我所在的组织）
	 * 作者：chenzhujie
	 * 日期：2017/1/16
	 * param: map(cellphone)
	 */
	Map<String,Object> getCompanyPrincipal(Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：发送申请（appOrgId,code）
	 * 作        者：chenzhujie
	 * 日        期：2017/2/27
	 */
	ResponseBean applayBusinessCompany(Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：邀请事业合伙人
	 * 作者：MaoSF
	 * 日期：2017/4/1
	 */
	ResponseBean inviteParent(InvitatParentDTO dto) throws Exception;

	/**
	 * 方法描述：设置事业合伙人的别名
	 * 作者：MaoSF
	 * 日期：2017/4/18
	 */
	ResponseBean setBusinessPartnerNickName(BusinessPartnerDTO dto) throws Exception;

	/**
	 * 方法描述：项目讨论区的@和特别提醒人员选择
	 * 作者：zcl
	 * 日期：2018/4/23
	 * @return: 查询到的用户列表和公司信息
	 */
	List<CompanyUserGroupDTO> getCompanyForCustomGroupSelect(QueryCompanyUserDTO query) throws Exception;

	/**
	 * 获取整个组织架构的组织及成员
	 */
	List<CompanyUserGroupDTO> getLinkman(QueryCompanyUserDTO query) throws Exception;

	/**
	 * 获取所有组织及项目参与人员（会议选择组织）
	 */
	List<CompanyUserGroupDTO> getOrgForSchedule(QueryCompanyUserDTO query) throws Exception;

	/**
	 * 需求就是选择组织架构中的所有人员，过滤自己以及报销、费用详情审批列表中包含的人员
	 */
	List<CompanyUserGroupDTO> getCostAuditMember(QueryCompanyUserDTO query) throws Exception;

	/**
	 * 抄送人列表（报销 ，费用)
	 */
	List<CompanyUserGroupDTO> getCostCopyMember(QueryCompanyUserDTO query) throws Exception;

	/**
	 * 需求就是选择当前组织的成员，过滤自己以及请假、出差详情审批列表中包含的人员
	 */
	List<CompanyUserAppDTO>  getLeaveAuditMember(QueryCompanyUserDTO query) throws Exception;

	/**
	 * 抄送人列表（请假出差)
	 */
	List<CompanyUserAppDTO>  getLeaveCopyMember(QueryCompanyUserDTO query) throws Exception;

	/**
	 * 方法描述：获取与当前组织相关的组织人员（本公司、分公司、事业合伙人）并按最后操作时间倒序排序
	 * 作者：zcl
	 * 日期：2018/4/23
	 * @param query 查询条件
	 * @return: 查询到的companyUser列表
	 */
	List<CompanyUserAppDTO> listCompanyUserForCustomGroupSelect(QueryCompanyUserDTO query) throws Exception;

	String getOrgTypeId(String companyId);

	/**
	 * 获取财务处理的组织id
	 */
	String getFinancialHandleCompanyId(String companyId);

	List<Map<String,String>> getUsedPartB(String companyId);

	/**
	 * 描述     查询组织信息
	 * 日期     2018/8/8
	 * @author  张成亮
	 * @return  组织信息
	 * @param   query 组织查询条件
	 **/
	List<CompanyDTO> listCompany(CompanyQueryDTO query);
}
