package com.maoding.org.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.org.dto.*;
import com.maoding.org.entity.DepartEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：departService
 * 类描述：部门           Service
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:22:43
 */
public interface DepartService extends BaseService<DepartEntity>{

	/**
	 * 方法描述：根据companyId查询Departs（部门）
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @param paraMap（companyId（公司ID）,departLevel（部门层级）,pid（部门父ID）,departType（部门type）,type:0:部门，1：分公司）
	 */
    List<DepartDataDTO> getDepartByCompanyId(Map<String, Object> paraMap)throws Exception;

	/**
	 * 方法描述：使用递归查询公司部门
	 * 作者：MaoSF
	 * 日期：2016/9/18
	 */
    List<DepartDataDTO> getDepartByCompanyId(Map<String, Object> param, List<DepartDataDTO> departDTOList) throws Exception;


	/**
	 * 方法描述：根据companyId和userId查询Departs（部门）包含公司
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @param paraMap（companyId（公司ID）,userId（用户Id））
	 */
    List<DepartDataDTO> getDepartByUserIdContentCompany(Map<String, Object> paraMap) throws Exception;

	/**
	 * 方法描述：根据companyId和userId查询Departs（部门）包含公司getDepartByUserIdContentCompanyInterface
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @param paraMap（companyId（公司ID）,userId（用户Id））
	 */
    List<DepartDataDTO> getDepartByUserIdContentCompanyInterface(Map<String, Object> paraMap) throws Exception;
	
	/**
	 * 方法描述：增加修改部门
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 */
    ResponseBean saveOrUpdateDepart(DepartDTO dto)throws Exception;

	/**
	 * 方法描述：删除部门（递归删除）【删除部门及所有的子部门和人员】
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:15:52
	 */
    AjaxMessage deleteDepartById(String id, String accountId)throws Exception;

	/**
	 * 方法描述：根据companyId查询Departs（部门）
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @param paraMap（companyId（公司ID）,departLevel（部门层级）,pid（部门父ID）,departType（部门type）,type:0:部门，1：分公司）
	 */
    List<DepartDataDTO> getDepartByCompanyIdWS(Map<String, Object> paraMap)throws Exception;
	List<DepartDataDTO> getDepartByCompanyId(String companyId,String pid)throws Exception;
	/**
	 * 方法描述：查询部门下所有的子部门及人员
	 * 作者：MaoSF
	 * 日期：2017/2/6
	 */
	ResponseBean getDepartAndGroup(Map<String, Object> map) throws Exception;

	/**
	 * 方法描述：查询部门下所有的子部门及人员
	 * 作者：MaoSF
	 * 日期：2017/2/6
	 */
	OrgDataDTO getOrgData(QueryCompanyUserDTO dto,boolean isSelectDepartAllUser) throws Exception;
}
