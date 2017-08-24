package com.maoding.org.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.org.dto.*;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;

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
	 * @param company
	 * @throws Exception
	 */
	String saveCompany(CompanyEntity company,  String userName,String orgManagerId,String currUserId)throws Exception;

	/**
	 * 方法描述：新增或修改公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-上午9:47:25
	 * @param dto
	 * @return
	 */
	AjaxMessage saveOrUpdateCompany(CompanyDTO dto) throws Exception;

	/**
	 * 方法描述：增加分公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	AjaxMessage createSubCompany(SubCompanyDTO dto)throws Exception;

	/**
	 * 方法描述：修改分公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	AjaxMessage updateSubCompany(SubCompanyDTO dto)throws Exception;

	/**
	 * 方法描述：删除分公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 * @param orgId(分公司id)
	 * @param orgPid(当前公司id)
	 * @return
	 * @throws Exception
	 */
	AjaxMessage deleteSubCompany(String orgPid, String orgId)throws Exception;

	/**
	 * 方法描述：增加合伙人
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	AjaxMessage createBusinessPartner(BusinessPartnerDTO dto)throws Exception;

	/**
	 * 方法描述：删除事业合伙人
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 * @param orgId(分公司id)
	 * @param orgPid(当前公司id)
	 * @return
	 * @throws Exception
	 */
	AjaxMessage deleteBusinessPartner(String orgPid, String orgId)throws Exception;

	/**
	 * 方法描述：修改合伙人
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	AjaxMessage updateBusinessPartner(BusinessPartnerDTO dto)throws Exception;
	
	/**
	 * 方法描述：根据id查询（转化成DTO）
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午3:27:28
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CompanyDTO getCompanyById(String id)throws Exception;

	/**
	 * 方法描述：根据公司名查找公司（转化成DTO）
	 * 作        者：MaoSF
	 * 日        期：2016年7月7日-下午4:16:44
	 * @param companyName
	 * @return
	 */
	CompanyDTO getCompanyDtoByCompanyName(String companyName) throws Exception;
	
	
	/**
	 * 方法描述：获取企业登录的组织(转化成DTO)
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-上午11:00:18
	 * @param userId
	 * @return
	 */
	List<CompanyDTO> getAdminOfCompanyByUserId(String userId) throws Exception;
	
	
	/**
	 * 方法描述：获取所有企业（组织切换列表）return(id,companyName,companyType,companyShortName,status,filePath)
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午2:03:16
	 * @param userId
	 * @return(id,companyName,companyType,companyShortName,status,filePath)
	 */
	List<CompanyDTO> getCompanyByUserId(String userId);


	/**
	 * 方法描述：获取组织架构树信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午6:42:43
	 * @param map
	 * @return
	 */
	OrgTreeDTO getOrgTree(Map<String, Object> map) throws Exception;


	/**
	 * 方法描述：获取组织架构树信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午6:42:43
	 * @return
	 */
	OrgTreeWsDTO getOrgTreeForNotice(Map<String, Object> map) throws Exception;


	/**
	 * 方法描述：获取组织架构树信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午6:42:43
	 * @return
	 */
	OrgTreeWsDTO newV2GetOrgTreeForNotice(Map<String, Object> map) throws Exception;

	/**
	 * 方法描述：获取组织架构树信息（消息通告选择发送组织  使用）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-下午6:42:43
	 *
	 * @param map
	 * @return
	 */
	OrgTreeDTO v2GetOrgTreeForNotice(Map<String, Object> map) throws Exception;
	/**
	 * 方法描述：根据公司id查找最顶级公司的id
	 * 作者：MaoSF
	 * 日期：2016/12/8
	 * @param:
	 * @return:
	 */
	String getRootCompanyId(String id) throws Exception;

	/**
	 * 方法描述：查询线上公司
	 * 作者：MaoSF
	 * 日期：2016/11/11
	 * @param:
	 * @return:
	 */
	CompanyDTO getLineCompanyByCompanyId(String id)throws Exception;

	/***************过滤以挂靠的组织**************/
	/**
	 * 方法描述：查询（未挂靠组织的组织），大B搜索小b，传pid=当前公司，小b搜索大B传companyId=当前公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 * @param map
	 * @return
	 */
	List<CompanyDTO> getCompanyFilterbyParam(Map<String, Object> map) throws Exception;
	
	/**
	 * 方法描述：查询（未挂靠组织的组织）条数，大B搜索小b，传pid=当前公司，小b搜索大B传companyId=当前公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 * @param map
	 * @return
	 */
	int getCompanyFilterbyParamCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 方法描述：查询（未挂靠组织的组织）分页，大B搜索小b，传pid=当前公司，小b搜索大B传companyId=当前公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 * @param map
	 * @return
	 */
	Map getCompanyFilterbyParamPage(Map<String, Object> map) throws Exception;

	/**
	 * 方法描述：查询从未挂靠的组织（包含被邀请没有同意的）
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 * @param map
	 * @return
	 */
	Map<String,Object> getCompanyFilterbyParamForInvit(Map<String, Object> map) throws Exception;
	
	/**
	 * 方法描述：解散组织
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:01:41
	 * @param companyId
	 * @return
	 */
	AjaxMessage disbandCompany(String companyId)throws Exception;


	/**
	 * 方法描述：根据当前id,查找所有的父节点
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:01:41
	 * @param id
	 * @return
	 */
	List<CompanyEntity> getAllParentCompanyList(String id)throws Exception;

	/**
	 * 方法描述：根据当前id,查找所有的子节点
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:01:41
	 * @param id
	 * @return
	 */
	List<CompanyEntity> getAllChilrenCompany(String id) throws Exception;


	/**
	 * 方法描述：根据当前id,查找所有的子节点
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:01:41
	 * @param id
	 * @return
	 */
	List<CompanyEntity> getAllChilrenCompanyWs(String id) throws Exception;

	/*****************项目统计（新增合同，合同回款）统计，项目承接人查询*****************/
	/**
	 * 方法描述：项目统计（新增合同，合同回款）统计，项目承接人查询
	 * 作者：MaoSF
	 * 日期：2016/8/15
	 * @param:
	 * @return:
	 */
	List<CompanyDTO> getCompanyForProjectStatics(String companyId) throws Exception;

	/**
	 * 方法描述： 技术审查费统计(付款方向），技术审查人选项
	 * 作者：MaoSF
	 * 日期：2016/8/15
	 * @param:
	 * @return:
	 */
	List<CompanyDTO> getCompanyForProjectStatics2(String companyId)  throws Exception;

	/**
	 * 方法描述：  合作设计费统计(付款方向）
	 * 作者：MaoSF
	 * 日期：2016/8/15
	 * @param:
	 * @return:
	 */
	List<CompanyDTO> getCompanyForProjectStatics3(String companyId)  throws Exception;

	/**
	 * 方法描述：  合作设计费统计(收款方向）
	 * 作者：MaoSF
	 * 日期：2016/8/15
	 * @param:
	 * @return:
	 */
	List<CompanyDTO> getCompanyForProjectStatics4(String companyId)  throws Exception;


	/**
	 * 方法描述：注册公司群（开发人员手工调用）
	 * 作者：MaoSF
	 * 日期：2016/8/23
	 * @param:
	 * @return:
	 */
	void registerCompanyGroup();

	/**
	 * 方法描述：获取常用的合作伙伴
	 * 作者：MaoSF
	 * 日期：2016/8/26
	 * @param:
	 * @return:
	 */
	List<CompanyDTO> getUsedCooperationPartners(String companyId) throws Exception;


	/**
	 * 方法描述：获取所有的公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 * @return
	 */
	List<CompanyEntity> getAllCompany() throws Exception;

	/**
	 * 方法描述：没有创建成功的生效的公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 * @return
	 */
	List<CompanyEntity> getAllCompanyIm() throws Exception;

	/**
	 * 方法描述：获取所有创建成功公司群的公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 * @return
	 */
	List<CompanyEntity> getImAllCompany() throws Exception;


	/**
	 * 方法描述：添加权限，系统管理员角色（初始话云端数据）
	 * 作者：MaoSF
	 * 日期：2016/11/7
	 * @param:
	 * @return:
	 */
	AjaxMessage initCompanyRole() throws Exception;

	/**
	 * 方法描述：系统管理员权限（初始话云端数据）2016-11-11
	 * 作者：MaoSF
	 * 日期：2016/11/11
	 * @param:
	 * @return:
	 */
	AjaxMessage initRolePermission() throws Exception;

	/**
	 * 获取通知公告的接收人员
	 * @param pubMap
	 * @return
     */
	List<CompanyUserEntity>  selectCompanyUserId(Map<String, Object> pubMap);


	/*********************************v2接口************************************************/
	/**
	 * 方法描述：返回选择团队列表（分公司，合作伙伴，所有公司），任务转发给其他公司，团队选择
	 * 作者：MaoSF
	 * 日期：2017/1/5
	 * @param:
	 * @return:
	 */
	Map<String,Object> getCompanyForSelect(String id,String projectId)throws Exception;



	/**
	 * 方法描述：组织架构数据
	 * 作者：MaoSF
	 * 日期：2017/1/13
	 * @param:
	 * @return:
	 */
	Map<String,Object> getLinkPeopleAndGroup(Map<String, Object> map) throws Exception;

	Map<String,Object> firstLevelDepartId(Map<String, Object> mapass);

	/**
	 * 方法描述：获取挂靠的公司
	 * 作者：MaoSF
	 * 日期：2017/1/16
	 * @param:
	 * @return:
	 */
	CompanyEntity getParentCompanyById(String id) throws Exception;

	/**
	 * 方法描述：获取邀请有关的
	 * 作者：chenzhujie
	 * 日期：2017/1/16
	 * @param:
	 * @return:
	 */
    Map<String,Object> getInviteAbout(Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：分享
	 * 作者：chenzhujie
	 * 日期：2017/1/16
	 * @param:
	 * @return:
	 */
    //int getInviteCode(Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：事业合伙人加入的界面（数据查询，查询我所在的组织）
	 * 作者：chenzhujie
	 * 日期：2017/1/16
	 * @param:map(cellphone)
	 * @return:
	 */
	Map<String,Object> getCompanyPrincipal(Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：发送申请（appOrgId,code）
	 * 作        者：chenzhujie
	 * 日        期：2017/2/27
	 * @return
	 */
	ResponseBean applayBusinessCompany(Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：点击邀请信息请求数据
	 * 作        者：chenzhujie
	 * 日        期：2017/2/27
	 * @return
	 */
	ResponseBean getCompanyByInviteUrl(Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：邀请事业合伙人
	 * 作者：MaoSF
	 * 日期：2017/4/1
	 * @param:
	 * @return:
	 */
	ResponseBean inviteParent(InvitatParentDTO dto) throws Exception;

	/**
	 * 方法描述：邀请事业合伙人身份验证
	 * 作者：MaoSF
	 * 日期：2017/4/1
	 * @param:map(id,cellphone),id为url地址中携带的id
	 * @return:
	 */
	ResponseBean verifyIdentityForParent(Map<String, Object> map)throws Exception;



	/**
	 * 方法描述：设置事业合伙人的别名
	 * 作者：MaoSF
	 * 日期：2017/4/18
	 * @param:
	 * @return:
	 */
	ResponseBean setBusinessPartnerNickName(BusinessPartnerDTO dto) throws Exception;

	/**
	 * 方法描述：获取公司别名
	 * 作者：MaoSF
	 * 日期：2017/4/18
	 * @param:
	 * @return:
	 */
	//String getNickName(String companyId,String currentCompanyId);

	/*********************************v2接口************************************************/
	/**
	 * 方法描述：返回选择团队列表（分公司，合作伙伴，所有公司），任务转发给其他公司，团队选择
	 * 作者：MaoSF
	 * 日期：2017/1/5
	 * @param:
	 * @return:
	 */
	List<OrgUserForCustomGroupDTO> getCompanyForCustomGroupSelect(String id,String accountId) throws Exception;
}
