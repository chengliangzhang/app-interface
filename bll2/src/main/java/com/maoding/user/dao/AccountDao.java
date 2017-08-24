package com.maoding.user.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.entity.AccountEntity;

import java.util.List;
import java.util.Map;


public interface AccountDao extends BaseDao<AccountEntity>{

	/**
	 * 方法描述：根据手机号查找注册信息
	 * 作        者：MaoSF
	 * 日        期：2016年7月7日-上午11:34:57
	 * @param cellphone
	 * @return
	 */
	AccountEntity getAccountByCellphoneOrEmail(String cellphone);

	/**
	 * 方法描述： 把默认公司为companyId都设置为null
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:04:11
	 * @param defaultCompanyId
	 */
	int updatedeAllfaultCompanyId(String defaultCompanyId);


	/**
	 * 方法描述：获取所有账户信息（注册IM使用）
	 * 作者：MaoSF
	 * 日期：2016/8/8
	 * @param:
	 * @return:
	 */
	List<AccountEntity> selectAll();

	/**
	 * 方法描述：获取所有没有创建的im用户
	 * 作者：Czj
	 * 日期：2016/11/30
	 * @param:map
	 * @return:List<AccountEntity>
	 */
	List<AccountEntity> selectNotCreateImUsers(Map<String, Object> map);

	/**
	 * 方法描述：按公司查询所有人
	 * 作者：Czj
	 * 日期：2016/11/30
	 * @param:map
	 * @return:List<AccountDTO>
	 */
	List<AccountDTO> selectAllPersonByParam(Map<String, Object> map);

	/**
	 * 方法描述：查询负责人,根据type,1:项目负责人,2:专业负责人
	 * 作者：Czj
	 * 日期：2016/11/30
	 * @param:map
	 * @return:List<AccountEntity>
	 */
	List<AccountEntity> selectProjectPrincipal(Map<String, Object> projectPrincipalMap);

	/**
	 * 方法描述：参与人员
	 * 作者：Czj
	 * 日期：2016/11/30
	 * @param:map
	 * @return:List<AccountEntity>
	 */
	List<AccountEntity> selectOtherParticipationPeople(Map<String, Object> projectPrincipalMap);

	/**
	 * 方法描述：任务签发人
	 * 作者：Czj
	 * 日期：2016/11/30
	 * @param:map
	 * @return:List<AccountEntity>
	 */
	List<AccountEntity> selectSignPeople(Map<String, Object> projectPrincipalMap);



	/**
	 * v2
	 * 方法描述：按公司查询所有人
	 * 作者：Czj
	 * 日期：2016/12/26
	 * @param:map
	 * @return:List<AccountEntity>
	 */
	List<AccountEntity> selectV2AllPersonByParam(Map<String, Object> map);




}
