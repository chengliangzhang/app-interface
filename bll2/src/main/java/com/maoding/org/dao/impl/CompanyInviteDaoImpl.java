package com.maoding.org.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.org.dao.CompanyInviteDao;
import com.maoding.org.entity.CompanyInviteEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyInviteDaoImpl
 * 类描述：邀请公司
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:44:06
 */
@Service("companyInviteDao")
public class CompanyInviteDaoImpl extends GenericDao<CompanyInviteEntity> implements CompanyInviteDao {
	/**
	 * 方法描述：获取某组织中最大值
	 * 作者：MaoSF
	 * 日期：2016/8/5
	 * @param:
	 * @return:
	 */
	@Override
	public int getMaxInviteCode() {
		return this.sqlSession.selectOne("CompanyInviteEntityMapper.getMaxInviteCode");
	}


	public CompanyInviteEntity selectByParam(Map<String, Object> param){
		return this.sqlSession.selectOne("CompanyInviteEntityMapper.selectByParam",param);
	}

}
