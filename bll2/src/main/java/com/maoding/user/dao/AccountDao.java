package com.maoding.user.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.dto.AccountDataDTO;
import com.maoding.user.entity.AccountEntity;

import java.util.List;
import java.util.Map;


public interface AccountDao extends BaseDao<AccountEntity>{

	/**
	 * 方法描述：根据手机号查找注册信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月7日-上午11:34:57
	 */
	AccountEntity getAccountByCellphoneOrEmail(String cellphone);

	/**
	 * 方法描述： 把默认公司为companyId都设置为null
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:04:11
	 */
	int updatedeAllfaultCompanyId(String defaultCompanyId);


	/**
	 * 方法描述：获取所有账户信息（注册IM使用）
	 * 作者：MaoSF
	 * 日期：2016/8/8
	 */
	List<AccountEntity> selectAll();

	/**
	 * 方法描述：根据ids查找
	 */
	List<AccountEntity> listAccountByIds(Map<String,Object> userIds);

	/**
	 * 方法描述：按公司查询所有人
	 * 作者：Czj
	 * 日期：2016/11/30
	 * @return:List<AccountDataDTO>
	 */
	List<AccountDataDTO> selectAllPersonByParam(Map<String, Object> map);

}
