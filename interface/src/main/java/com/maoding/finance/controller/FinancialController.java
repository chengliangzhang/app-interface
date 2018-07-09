package com.maoding.finance.controller;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.StringUtil;
import com.maoding.financial.dto.*;
import com.maoding.financial.service.ExpCategoryService;
import com.maoding.financial.service.ExpDetailService;
import com.maoding.financial.service.ExpMainService;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.DepartDTO;
import com.maoding.org.dto.DepartDataDTO;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.CompanyUserService;
import com.maoding.org.service.DepartService;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：FinancialController
 * 类描述：财务报销
 * 作    者：MaoSF
 * 日    期：2016年7月8日-下午3:12:45
 */
@Controller
@RequestMapping("/finance")
public class FinancialController extends BaseWSController {

	@Autowired
	private ExpMainService expMainService;

	@Autowired
	private ExpCategoryService expCategoryService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyUserService companyUserService;
	@Autowired
	private DepartService departService;

	@Autowired
	private ExpDetailService expDetailService;







	/**
	 * 方法描述：自定义报销类别查询
	 * 作   者：CZJ
	 * 日   期：2016/10/17 15:08
	 *
	 * @return
	 */
	@RequestMapping("/getExpCategory")
	@ResponseBody
	public ResponseBean getExpCategory(@RequestBody Map<String, String> paraMap) throws Exception {
		try {
			String companyId = paraMap.get("appOrgId");
			String userId = paraMap.get("accountId");
			AjaxMessage ajax = expCategoryService.getCategoryBaseData(companyId, userId);
			Map<String,Object> map = (Map<String,Object>)ajax.getData();
			return responseSuccess().addData("expTypeList",map.get("expTypeList"));
		}
		catch (Exception e){
			return ResponseBean.responseError("查询报销类别失败！");
		}
	}
	/**
	 * 方法描述：自定义报销类别增加或者修改
	 * 作   者：CZJ
	 * 日   期：2016/10/17 15:08
	 *
	 * @param dto
	 */
	@RequestMapping("/saveOrUpdateExpCategory")
	@ResponseBody
	public ResponseBean saveOrUpdateExpCategory(@RequestBody ExpTypeOutDTO dto) {
		try {
			String companyId = dto.getAppOrgId();
			AjaxMessage ajax = expCategoryService.saveOrUpdateCategoryBaseData(dto,companyId);
			if("0".equals(ajax.getCode())){
				return ResponseBean.responseSuccess("保存成功");
			}
			return  ResponseBean.responseError("保存失败！");
		} catch (Exception e) {
			log.error("操作失败！", e);
			return  ResponseBean.responseError("操作失败！");
		}
	}


	/**
	 * 方法描述：获取报销类型
	 * 作   者：LY
	 * 日   期：2016/7/27 17:59
	 *
	 * @return
	 */
	@RequestMapping("/get_exptype")
	@ResponseBody
	public ResponseBean getExpTypeLis(@RequestBody Map<String, String> paraMap) throws Exception {
		try {
			String companyId = paraMap.get("appOrgId");
			List<ExpTypeDTO> expType=expCategoryService.getExpTypeList(null,companyId);
			return ResponseBean.responseSuccess().addData("expTypeList",expType);
		}
		catch (Exception e){
			return  ResponseBean.responseError("查询报销类型失败！");
		}
	}

	/**
	 * 方法描述：获取相关项目列表
	 * 作   者：LY
	 * 日   期：2016/7/27 17:59
	 *
	 * @return
	 */
	@RequestMapping("/get_projects")
	@ResponseBody
	public ResponseBean getProjectList(@RequestBody Map<String, String> paraMap){
		try {
			String companyId = paraMap.get("appOrgId");
			String searchVal=paraMap.get("searchVal");
			Map<String, Object> map = new HashMap<String, Object>();
			ProjectDTO projectDTO=new ProjectDTO();
			projectDTO.setCompanyId(companyId);
			//projectDTO.setSearchVal(searchVal);
			//map.put("projectList",expMainService.getProjectListWS(projectDTO));
			return ResponseBean.responseSuccess().addData(map);
		}
		catch (Exception e){
			return ResponseBean.responseError("查询项目列表失败");
		}

	}
	/**
	 * 方法描述：获取和自己有关的公司和部门列表
	 * 作   者：LY
	 * 日   期：2016/7/27 17:59
	 *
	 * @return
	 */
	@RequestMapping("/get_companywithdepartlist")
	@ResponseBody
	public ResponseBean getCompanyWithDepartList(@RequestBody Map<String, String> paraMap) throws Exception {
		try {
			String companyId = paraMap.get("appOrgId");
			String userId = paraMap.get("accountId");
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mapParams = new HashMap<String, Object>();
			mapParams.put("companyId", companyId);
			mapParams.put("userId", userId);
			map.put("departList", departService.getDepartByUserIdContentCompanyInterface(mapParams));
			return ResponseBean.responseSuccess().addData(map);
		}
		catch (Exception e){
			return ResponseBean.responseError("查询公司和部门列表失败");
		}
	}

	/**
	 * 方法描述：获取部门列表
	 * 作   者：LY
	 * 日   期：2016/7/27 17:59
	 *
	 * @return
	 */
	@RequestMapping("/get_departlist")
	@ResponseBody
	public ResponseBean getDepartList(@RequestBody Map<String, String> paraMap) throws Exception {
		try {
			String companyId = paraMap.get("appOrgId");
			String userId = paraMap.get("accountId");
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> mapParams = new HashMap<String, Object>();
			mapParams.put("companyId", companyId);
			mapParams.put("userId", userId);
			CompanyDTO company = companyService.getCompanyById(companyId);
			List<DepartDataDTO> list = departService.getDepartByCompanyId(mapParams);
			DepartDataDTO dto = new DepartDataDTO();
			dto.setId(companyId);
			dto.setDepartName(company.getCompanyShortName());
			list.add(0, dto);
			map.put("departListByCompanyId", list);
			List<DepartDTO> newList = new ArrayList<DepartDTO>();
			newList = BaseDTO.copyFields(list,DepartDTO.class);
			for(DepartDTO departDTO : newList){
				String strDepartName = departDTO.getDepartName();
				if(strDepartName.indexOf("/")!=-1){
					String[] arrStr = strDepartName.split("/");
					strDepartName = arrStr[arrStr.length-1];
					departDTO.setDepartName(strDepartName);
				}
			}
			map.put("departListByCompanyIdWithOutParentDepart",newList);
			return ResponseBean.responseSuccess().addData(map);
		}
		catch(Exception e){
			return  ResponseBean.responseError("查询部门列表失败");
		}
	}

	/**
	 * 方法描述：获取报销人列表
	 * 作   者：LY
	 * 日   期：2016/7/27 17:59
	 *
	 * @return
	 */
	@RequestMapping("/get_expuserlist")
	@ResponseBody
	public ResponseBean getExpUserList(@RequestBody Map<String, String> paraMap) throws Exception {
		try {
			String companyId = paraMap.get("appOrgId");
			String userId = paraMap.get("accountId");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyUserList", companyUserService.getCompanyUserByCompanyId(companyId));
			return ResponseBean.responseSuccess().addData(map);
		}
		catch(Exception e){
			return  ResponseBean.responseError("查询部门列表");
		}
	}


	/**
	 * 方法描述：查询当前组织所有人员
	 * 作   者：LY
	 * 日   期：2016/8/3 17:17
	 *
	 * @param
	 */
	@RequestMapping("/get_userlist")
	@ResponseBody
	public ResponseBean getUserList(@RequestBody Map<String, String> paraMap) throws Exception {
		String companyId = paraMap.get("appOrgId");
		String orgId = paraMap.get("orgId");
		return ResponseBean.responseSuccess().addData("userList", companyUserService.getUserList(companyId, orgId));
	}

	/**
	 * 方法描述：我的报销列表
	 * 作   者：LY
	 * 日   期：2016/7/29 10:53
	 *
	 * @param
	 */
	@RequestMapping("/get_expmainpage")
	@ResponseBody
	public ResponseBean getExpMainPage(@RequestBody Map<String, Object> paraMap) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("approveStatus", paraMap.get("approveStatus"));
		param.put("departId", paraMap.get("departId"));
		param.put("startDate", paraMap.get("startDate"));
		param.put("endDate", paraMap.get("endDate"));
		param.put("pageSize", paraMap.get("pageSize"));
		param.put("pageNumber", paraMap.get("pageNumber"));
		param.put("companyId", paraMap.get("appOrgId"));
		param.put("userId", paraMap.get("accountId"));
		List<ExpMainDTO> data = expMainService.getExpMainPage(param);
		for(ExpMainDTO expMainDTO : data){
			List<ExpDetailDTO> list = expDetailService.getExpDetailDTO(expMainDTO.getId());
			String expPName = "";
			for(int i = 0 ; i < list.size();i++){
				String str = list.get(i).getExpAllName();
				String[] strAttr = str.split("-");
				if(i==0){
					expPName = strAttr[0];
				}else{
					if(expPName.indexOf(strAttr[0]) == -1){
						expPName = expPName +","+ strAttr[0];
					}
				}
			}
			expMainDTO.setExpTypeParentName(expPName);
		}

		/*for(ExpMainDTO expMainDTO : data){
			List<DataDictionaryEntity> dataDictionaryEntityList = dataDictionaryService.getDataDictionarysubEntityById(expMainDTO.getExpType());
			if(dataDictionaryEntityList.size()>0){
				expMainDTO.setExpTypeParentName(dataDictionaryEntityList.get(0).getName());
			}
		}*/
		return ResponseBean.responseSuccess().addData("expMainList", data);
	}

	/**
	 * 方法描述：待我审核列表
	 * 作   者：LY
	 * 日   期：2016/7/29 10:53
	 * @param
	 *
	 */
	@RequestMapping("/get_expmainpageforaudit")
	@ResponseBody
	public ResponseBean getExpMainPageForAudit(@RequestBody Map<String,Object>paraMap) throws Exception{


		Map<String,Object> param = new HashMap<String, Object>();
		param.put("departId",paraMap.get("departId"));
		param.put("startDate",paraMap.get("startDate"));
		param.put("endDate",paraMap.get("endDate"));
		param.put("pageSize",paraMap.get("pageSize"));
		param.put("pageNumber",paraMap.get("pageNumber"));
		param.put("companyId", paraMap.get("appOrgId"));
		if(StringUtil.isNullOrEmpty(paraMap.get("approveStatus"))){
			param.put("defaultApproveStatus","('0','1','2','5')");
		}
		if("0".equals(paraMap.get("approveStatus"))){
			param.put("defaultApproveStatus","('0','5')");
			param.put("waitCheck","1");
			param.put("isHave","have");
		}else if("1".equals(paraMap.get("approveStatus"))){
			param.put("defaultApproveStatus","('1','5')");
			param.put("waitCheck","1");
		}else{
			param.put("approveStatus", paraMap.get("approveStatus"));
		}

		param.put("auditPerson",paraMap.get("accountId"));
//		param.put("companyId", this.getFromSession("companyId", String.class));
//		param.put("userId", this.getFromSession("userId", String.class));
		List<ExpMainDTO> data = expMainService.getExpMainPageInterface(param);
		int totalNumber = expMainService.getExpMainPageCount(param);
		for(ExpMainDTO expMainDTO : data){
			List<ExpDetailDTO> list = expDetailService.getExpDetailDTO(expMainDTO.getId());
			String expPName = "";
			for(int i = 0 ; i < list.size();i++){
				String str = list.get(i).getExpAllName();
				String[] strAttr = str.split("-");
				if(i==0){
					expPName = strAttr[0];
				}else{
					if(expPName.indexOf(strAttr[0]) == -1){
						expPName = expPName +","+ strAttr[0];
					}
				}
			}
			expMainDTO.setExpTypeParentName(expPName);
		}

		/*for(ExpMainDTO expMainDTO : data){
			List<DataDictionaryEntity> dataDictionaryEntityList = dataDictionaryService.getDataDictionarysubEntityById(expMainDTO.getExpType());
			if(dataDictionaryEntityList.size()>0){
				expMainDTO.setExpTypeParentName(dataDictionaryEntityList.get(0).getName());
			}
		}*/
		return ResponseBean.responseSuccess().addData("expMainList",data).addData("total",totalNumber);
	}

	/**
	 * 方法描述：撤回报销
	 * 作   者：LY
	 * 日   期：2016/7/29 11:01
	 * @param  --报销单id  type--状态(3撤回)
	 * @return
	 *
	 */
	@RequestMapping("/recall_expmain")
	@ResponseBody
	public ResponseBean recallExpMain(@RequestBody ExpAuditDTO dto) throws Exception{

		int i=expMainService.recallExpMain(dto);
		if(i>0){
			return ResponseBean.responseSuccess("报销退回成功!");
		}
		else{
			return ResponseBean.responseError("报销退回失败！");
		}
	}

	/**
	 * 方法描述：报销详情
	 * 作   者：LY
	 * 日   期：2016/7/29 10:53
	 * @param --报销单id
	 *
	 */
	@RequestMapping("/get_expmainpagedetail")
	@ResponseBody
	public ResponseBean getExpMainPageDetail(@RequestBody ExpMainDTO expMainDTO) throws Exception{
		ExpMainDTO dto = expMainService.selectExpMainDetail(expMainDTO.getId());
		return responseSuccess().addData("expMainInfo",dto);
	}

	/**
	 * 方法描述：删除报销
	 * 作   者：LY
	 * 日   期：2016/7/29 10:53
	 * @param --报销单id
	 */
	@RequestMapping("/delete_expmain")
	@ResponseBody
	public ResponseBean deleteExpMain(@RequestBody ExpMainDTO expMainDTO) throws Exception{
		int i= expMainService.deleteExpMain(expMainDTO.getId(),expMainDTO.getVersionNum()+"");
		if(i>0){
			return  ResponseBean.responseSuccess("删除成功!");
		}
		else{
			return  ResponseBean.responseError("删除失败！");
		}
	}

	/**
	 * 方法描述：同意报销
	 * 作   者：LY
	 * 日   期：2016/8/1 15:08
	 * @param  --报销单id
	 */
	@RequestMapping("/agree_expmain")
	@ResponseBody
	public ResponseBean agreeExpMain(@RequestBody SaveExpMainDTO expMainDTO) throws Exception{
		int i=expMainService.agreeExpMain(expMainDTO);
		if(i>0){
			return ResponseBean.responseSuccess("操作成功!");
		}
		else{
			return  ResponseBean.responseError("操作失败！");
		}
	}

	/**
	 * 方法描述：同意报销并转移审批人
	 * 作   者：LY
	 * 日   期：2016/8/1 15:08
	 * @param  --报销单id auditPerson--新审批人
	 */
	@RequestMapping("/agreeandtrans_auditperexpmain")
	@ResponseBody
	public ResponseBean agreeAndTransAuditPerExpMain(@RequestBody SaveExpMainDTO expMainDTO) throws Exception{
		int i=expMainService.agreeAndTransAuditPerExpMain(expMainDTO);
		if(i>0) {
			return ResponseBean.responseSuccess("转发成功!");
		}
		else{
			return  ResponseBean.responseError("报销转发失败！");
		}
	}


	/**
	 * 方法描述：报销汇总
	 * 作   者：LY
	 * 日   期：2016/8/2 10:53
	 * @param
	 */
	@RequestMapping("/get_expmainpageforsummary")
	@ResponseBody
	public ResponseBean getExpMainPageForSummary(@RequestBody Map<String,Object>paraMap ) throws Exception{
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("departId", paraMap.get("departId"));//部门
		param.put("userId", paraMap.get("userId"));//报销人
		param.put("startDate", paraMap.get("startDate"));
		param.put("endDate", paraMap.get("endDate"));
		//param.put("expType",dto.getExpType());//报销类别
		//param.put("expParentType",dto.getExpParentType());//报销父类别
		param.put("expName",paraMap.get("expName"));//子
		param.put("expPName",paraMap.get("expPName"));//父
		if(null != paraMap.get("pageIndex")){
			param.put("pageNumber",paraMap.get("pageIndex"));
		}else{
			param.put("pageNumber",paraMap.get("pageNumber"));
		}
		param.put("pageSize", paraMap.get("pageSize"));

		param.put("companyId", paraMap.get("appOrgId"));
		//param.put("userId", this.getFromSession("userId", String.class));
		List<ExpMainDTO> data = expMainService.getExpMainPageForSummary(param);
		/*for(ExpMainDTO expMainDTO : data){
			List<DataDictionaryEntity> dataDictionaryEntityList = dataDictionaryService.getDataDictionarysubEntityById(expMainDTO.getExpType());
			if(dataDictionaryEntityList.size()>0){
				expMainDTO.setExpTypeParentName(dataDictionaryEntityList.get(0).getName());
			}
		}*/

		for(ExpMainDTO expMainDTO : data){
			expMainDTO.setExpTypeParentName(expMainDTO.getExpPName());
		}
		int totalNumber = expMainService.getExpMainPageForSummaryCount(param);
		param.clear();
		param.put("expMainList",data);
		param.put("total", totalNumber);
		BigDecimal expSumAmount = new BigDecimal("0.00");
		for (ExpMainDTO expMainDTO : data) {
			expSumAmount = expSumAmount.add(expMainDTO.getExpSumAmount());
		}
		param.put("expSumAmount", expSumAmount);
		return responseSuccess().addData(param);
	}



}
