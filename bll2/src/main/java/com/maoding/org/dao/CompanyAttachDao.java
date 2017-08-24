package com.maoding.org.dao;

import java.util.List;
import java.util.Map;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.org.entity.CompanyAttachEntity;

public interface CompanyAttachDao extends BaseDao<CompanyAttachEntity>{
	
	/**
	 * 方法描述：根据参数查询附件
	 * 作        者：wangrb
	 * 日        期：2015年11月19日-下午4:48:56
	 * @param map（companyId：必传，fileType：文件类型）
	 * @return
	 */
	public List<CompanyAttachEntity> getCompanyAttachByParamer(Map<String, Object> map);
	
	/**
	 * 方法描述：根据参数删除附件
	 * 作        者：wangrb
	 * 日        期：2015年11月19日-下午4:52:17
	 * @param map（companyId：必传，fileType：文件类型）
	 * @return
	 */
	public int delCompanyAttachByParamer(Map<String, Object> map);
	

}
