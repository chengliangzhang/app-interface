package com.maoding.org.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maoding.org.dto.CompanyDataDTO;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.org.dto.OrgRelationDataDTO;
import com.maoding.org.service.TeamOperaterService;
import com.maoding.role.dto.OrgRoleTypeDTO;
import com.maoding.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyRelationDao;
import com.maoding.org.dto.CompanyRelationDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyRelationEntity;
import com.maoding.org.service.CompanyRelationService;
/**
 * 深圳市设计同道技术有限公司
 * 类    名：DepartServiceImpl
 * 类描述：组织（公司）ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午4:24:38
 */
@Service("CompanyRelationService")
public class CompanyRelationServiceImpl extends GenericService<CompanyRelationEntity>  implements CompanyRelationService{

	@Autowired
	private CompanyRelationDao companyRelationDao;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private TeamOperaterService teamOperaterService;

	@Override
	public List<CompanyRelationDTO> getCompanyRelationByParam(
			Map<String, Object> parma) {
		return companyRelationDao.getCompanyRelationByParam(parma);
	}

	@Override
	public int getCompanyRelationByParamCount(Map<String, Object> parma) {
		return companyRelationDao.getCompanyRelationByParamCount(parma);
	}

	@Override
	public OrgRelationDataDTO getCooperatorData(Map<String, Object> param) throws Exception {
		String orgId = (String)param.get("orgId");
		OrgRoleTypeDTO roleType = null;
		CompanyRelationDTO relation = companyRelationDao.getCompanyRelationByOrgId(orgId);
		if(relation!=null){
			roleType = roleService.getOrgRoleByType(relation.getTypeId());
		}
		CompanyEntity company = this.companyDao.selectById(orgId);
		CompanyUserAppDTO systemManager = this.teamOperaterService.getSystemManager(orgId);

		OrgRelationDataDTO dataDTO = new OrgRelationDataDTO();
		dataDTO.setCompany(company);
		dataDTO.setRoleType(roleType);
		dataDTO.setSystemManager(systemManager);
		return dataDTO;
	}

}
