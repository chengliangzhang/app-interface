package com.maoding.user.dao;

import java.util.List;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.entity.UserTechnicalEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：UserDao
 * 类描述：用户信息Dao
 * 作    者：MaoSF
 * 日    期：2015年11月23日-下午2:44:39
 */
public interface UserTechnicalDao extends BaseDao<UserTechnicalEntity>{
	/**
	 * 方法描述：根据参数查询技术职称
	 * 作        者：MaoSF
	 * 日        期：2015年11月23日-下午4:48:56
	 * @param userId（用户id）
	 * @return
	 */
	public List<UserTechnicalEntity> getUserTechnicalByUserId(String userId);
	
	/**
	 * 方法描述：根据参数删除技术职称
	 * 作        者：MaoSF
	 * 日        期：2015年11月23日-下午4:52:17
	 * @param userId（用户id）
	 * @return
	 */
	public int delUserTechnicalByUserId(String userId);

}
