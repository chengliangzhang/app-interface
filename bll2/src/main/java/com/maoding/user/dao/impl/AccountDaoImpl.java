package com.maoding.user.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.user.dao.AccountDao;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.dto.AccountDataDTO;
import com.maoding.user.entity.AccountEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：AccountDaoImpl
 * 类描述：用户账号信息Dao
 * 作    者：MaoSF
 * 日    期：2016年7月7日-上午11:48:29
 */
@Service("accountDao")
public class AccountDaoImpl extends GenericDao<AccountEntity> implements AccountDao {

	@Override
	public AccountEntity getAccountByCellphoneOrEmail(String cellphone) {
		return this.sqlSession.selectOne("AccountEntityMapper.getAccountByCellphoneOrEmail",cellphone);
	}

	@Override
	public int updatedeAllfaultCompanyId(String defaultCompanyId) {
		return this.sqlSession.update("AccountEntityMapper.updatedeAllfaultCompanyId",defaultCompanyId);
	}

	/**
	 * 方法描述：获取所有账户信息（注册IM使用）
	 * 作者：MaoSF
	 * 日期：2016/8/8
	 */
	@Override
	public List<AccountEntity> selectAll() {
		return this.sqlSession.selectList("AccountEntityMapper.selectAll");
	}

	@Override
	public List<AccountEntity> listAccountByIds(Map<String, Object> userIds) {
		return this.sqlSession.selectList("AccountEntityMapper.listAccountByIds",userIds);
	}

	@Override
	public List<AccountDataDTO> selectAllPersonByParam(Map<String,Object> map){
		return this.sqlSession.selectList("AccountEntityMapper.selectAllPersonByParam",map);
	}

}
