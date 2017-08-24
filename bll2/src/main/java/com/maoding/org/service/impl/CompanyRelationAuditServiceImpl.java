package com.maoding.org.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.*;
import com.maoding.org.dto.CompanyRelationAuditDTO;
import com.maoding.org.dto.OrgTreeDTO;
import com.maoding.org.entity.*;
import com.maoding.org.service.CompanyRelationAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DepartServiceImpl
 * 类描述：组织（公司）ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午4:24:38
 */
@Service("companyRelationAuditService")
public class CompanyRelationAuditServiceImpl extends GenericService<CompanyRelationAuditEntity>
		implements CompanyRelationAuditService{

	@Autowired
	private CompanyRelationAuditDao companyRelationAuditDao;

	@Autowired
	private CompanyRelationDao companyRelationDao;

	@Autowired
	private OrgDao orgDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private SubCompanyDao subCompanyDao;

	@Autowired
	private BusinessPartnerDao businessPartnerDao;

	@Override
	public List<CompanyRelationAuditDTO> getCompanyRelationAuditByParam(
			Map<String, Object> parma) {
		return companyRelationAuditDao.getCompanyRelationAuditByParam(parma);
	}

	@Override
	public int getCompanyRelationAuditByParamCount(Map<String, Object> parma) {
		return companyRelationAuditDao.getCompanyRelationAuditByParamCount(parma);
	}

	@Override
	public AjaxMessage applicationOrInvitation(CompanyRelationAuditDTO dto) throws Exception {

		AjaxMessage ajax = this.validateApplicationOrInvitation(dto.getOrgId(),dto.getOrgPid());

		if("1".equals(ajax.getCode())){//如果验证不通过。直接返回
			return ajax;
		}

		CompanyRelationAuditEntity relaEntity=new CompanyRelationAuditEntity();
		BaseDTO.copyFields(dto,relaEntity);
		relaEntity.setId(StringUtil.buildUUID());
		relaEntity.setAuditStatus("2");
		relaEntity.setCreateDate(new Date());
		relaEntity.setCreateBy(dto.getAccountId());
		this.insert(relaEntity);
		return new AjaxMessage().setCode("0").setInfo("处理成功").setData(relaEntity);
	}

	/**
	 * 方法描述：邀请或申请加盟
	 * 作        者：MaoSF
	 * 日        期：2016年7月12日-上午1:12:52
	 *
	 * @param orgId
	 * @param orgPid
	 * @return
	 * @throws Exception
	 */
	@Override
	public AjaxMessage applicationOrInvitation(String orgId, String orgPid,String accountId) throws Exception {
		AjaxMessage ajax = this.validateApplicationOrInvitation(orgId,orgPid);
		if("1".equals(ajax.getCode())){//如果验证不通过。直接返回
			return ajax;
		}
		CompanyRelationAuditEntity relaEntity=new CompanyRelationAuditEntity();
		relaEntity.setId(StringUtil.buildUUID());
        relaEntity.setOrgId(orgId);
        relaEntity.setOrgPid(orgPid);
		relaEntity.setAuditStatus("2");
		relaEntity.setRelationType("0");
		relaEntity.setType("3");
		relaEntity.setCreateDate(new Date());
		relaEntity.setCreateBy(accountId);
		this.insert(relaEntity);
		return ajax.setData(relaEntity.getId());
	}

	/**
	 *
	 * @param orgId
	 * @param orgPid
     * @return
     */
	public AjaxMessage validateApplicationOrInvitation(String orgId,String orgPid){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("cid",orgId);
		List<CompanyRelationAuditDTO> list =companyRelationAuditDao.getCompanyRelationAuditByParam(map);
		if (list!=null && list.size()>0){
			for(CompanyRelationAuditDTO dto :list){
				if((orgId.equals(dto.getOrgId()) && orgPid.equals(dto.getOrgPid())) || (orgPid.equals(dto.getOrgId()) && orgId.equals(dto.getOrgPid()))){
					if("0".equals(dto.getAuditStatus()))
					{
						return new AjaxMessage().setCode("1").setInfo("已经是分公司或合作伙伴");
					}else {
						return new AjaxMessage().setCode("1").setInfo("您已经邀请过该组织"); //20161215组织
					}
				}
			}

		}
		//从上面找父节点,只要有父节点（orgPid）==orgId,则不可加盟

		//理论上，每个orgId只对应一个orgPid，所以list为空，或许只有一条记录
		String currentOrgPid = orgPid;
		boolean isWhile = true;
		try {
			while (isWhile){
				map.clear();
				map.put("orgId", currentOrgPid);
				list = companyRelationAuditDao.getCompanyRelationAuditByParam(map);
				if (list == null || list.size() == 0) {
					return new AjaxMessage().setCode("0");
				}
				CompanyRelationAuditDTO dto = list.get(0);
				if (orgId.equals(dto.getOrgPid())) {
					return new AjaxMessage().setCode("1").setInfo("存在循环加盟，加盟失败");
				}
				currentOrgPid = dto.getOrgPid();

			}
		}catch (Exception e){
			isWhile=false;
		}finally {
			isWhile=false;
		}
		return new AjaxMessage().setCode("1").setInfo("存在循环加盟，加盟失败");
	}

	@Override
	public AjaxMessage processingApplicationOrInvitation(CompanyRelationAuditDTO dto) throws Exception {
		String relationId=dto.getId();
		String orgId = dto.getOrgId();
		String accountId =dto.getAccountId();
		int auditStatus=Integer.parseInt(dto.getAuditStatus());
		if(auditStatus<0){
			companyRelationAuditDao.deleteById(relationId);
			return new AjaxMessage().setCode("0").setInfo("处理成功");
		}else{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("orgId", orgId);
			if(auditStatus==0){
				//查询是否有父公司
				int count=companyRelationDao.getCompanyRelationByParamCount(map);
				if(count==0){
					//1.更新审核表中的数据
					CompanyRelationAuditEntity relationAuditEntity=new CompanyRelationAuditEntity();
					relationAuditEntity.setId(relationId);
					relationAuditEntity.setAuditStatus(String.valueOf(auditStatus));
					relationAuditEntity.setPassDate(DateUtils.date2Str(DateUtils.date_sdf));
					relationAuditEntity.setUpdateBy(accountId);
					companyRelationAuditDao.updateById(relationAuditEntity);
					//2.把审核完的数据插入到已审核的表中
					CompanyRelationEntity relaEntity = new  CompanyRelationEntity();
					relaEntity.setId(relationId);
					relaEntity.setOrgId(dto.getOrgId());
					relaEntity.setOrgPid(dto.getOrgPid());
					relaEntity.setCreateBy(accountId);
					companyRelationDao.insert(relaEntity);

					//3.添加组织基础表中的数据，id=CompanyRelationEntity.id
					OrgEntity orgEntity = new OrgEntity();
					orgEntity.setId(relaEntity.getId());
					orgEntity.setOrgStatus("0");
					orgEntity.setOrgType(dto.getType());
					orgEntity.setCreateBy(accountId);
					orgDao.insert(orgEntity);
					//4.判断是分公司还是合作伙伴。分别插入分公司（合作伙伴数据表中）
					CompanyEntity companyEntity = companyDao.selectById(orgId);
					if("2".equals(dto.getType())){//如果是分公司
						SubCompanyEntity subCompanyEntity = new SubCompanyEntity();
						subCompanyEntity.setId(relaEntity.getId());
						subCompanyEntity.setNickName(companyEntity.getCompanyName());
						subCompanyEntity.setCreateBy(accountId);
						subCompanyDao.insert(subCompanyEntity);
					}
					if("3".equals(dto.getType())){//如果是合作伙伴
						BusinessPartnerEntity businessPartnerEntity = new BusinessPartnerEntity();
						businessPartnerEntity.setId(relaEntity.getId());
						businessPartnerEntity.setNickName(companyEntity.getCompanyName());
						businessPartnerEntity.setCreateBy(accountId);
						businessPartnerDao.insert(businessPartnerEntity);
					}

					OrgTreeDTO tree = new OrgTreeDTO();
					tree.setRealId(orgId);
					tree.setId(orgId);
					tree.setCompanyId(orgId);
					tree.setText(dto.getCompanyName());
					if("2".equals(dto.getType()))//如果是分公司
					{
						tree.setType("subCompany");
					}
					if("3".equals(dto.getType()))//如果是分公司
					{
						tree.setType("partner");
					}
					tree.setTreeEntity(companyDao.selectById(orgId));
					return new AjaxMessage().setCode("0").setInfo("处理成功").setData(tree);
					//
				}else{//如果已经存在挂靠的公司
				/*CompanyRelationAuditEntity relaEntity= new CompanyRelationAuditEntity();
				relaEntity.setId(relationId);*/
					//删除审核表中的数据
					companyRelationAuditDao.deleteById(relationId);
					return new AjaxMessage().setCode("1").setInfo("此公司已有挂靠组织，该数据已被删除");//20161215组织
				}
			}
			else{//拒绝处理
				CompanyRelationAuditEntity relaEntity= new CompanyRelationAuditEntity();
				relaEntity.setId(relationId);
				relaEntity.setAuditStatus(dto.getAuditStatus());
				//删除审核表中的数据
				companyRelationAuditDao.updateById(relaEntity);
				return new AjaxMessage().setCode("0").setInfo("处理成功");
			}
		}
	}
}
