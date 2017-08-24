package com.maoding.org.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.org.entity.TeamOperaterEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：TeamOperaterDao
 * 类描述：组织管理Dao
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:42:48
 */
public interface TeamOperaterDao extends BaseDao<TeamOperaterEntity>{

	/**
	 * 方法描述：根据companyId查找【一个组织一个管理员】
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-上午10:36:11
	 * @param companyId
	 * @return
	 */
	public TeamOperaterEntity getTeamOperaterByCompanyId(String companyId,String userId);


	/**
	 * 方法描述：根据companyId查找【一个组织一个管理员】,理论上有且只有一条数据
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-上午10:36:11
	 * @param companyId
	 * @return
	 */
	public List<TeamOperaterEntity> selectTeamOperaterByCompanyId(String companyId);

	/**
	 * 方法描述：获取整个系统的系统管理员（用户初始化云端的系统管理员角色）（2016-11-07打包，添加系统角色）
	 * 作者：MaoSF
	 * 日期：2016/11/7
	 * @param:
	 * @return:
	 */
	public List<TeamOperaterEntity> getAllTeamOperater();

	/**
	 * 方法描述：
	 * 作者：MaoSF
	 * 日期：2016/11/7
	 * @param:
	 * @return:
	 */
	public List<TeamOperaterEntity> selectByParam(Map<String,Object> param);


}
