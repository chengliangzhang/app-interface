package com.maoding.org.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.org.dao.CompanyAttachDao;
import com.maoding.org.entity.CompanyAttachEntity;

@Service("companyAttachDao")
public class CompanyAttachDaoImpl extends GenericDao<CompanyAttachEntity> implements CompanyAttachDao{

	@Override
	public List<CompanyAttachEntity> getCompanyAttachByParamer(Map<String,Object> map) {
		return this.sqlSession.selectList("CompanyAttachEntityMapper.selectByParamter", map);
	}

	@Override
	public int delCompanyAttachByParamer(Map<String, Object> map) {
		return this.sqlSession.delete("CompanyAttachEntityMapper.deleteByParameter", map);
	}
	
}
