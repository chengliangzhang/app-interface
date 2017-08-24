package com.maoding.user.dao;

import java.util.List;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.user.entity.UserEducationBackGroundEntity;
import com.maoding.user.entity.UserEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：UserDao
 * 类描述：用户信息Dao
 * 作    者：MaoSF
 * 日    期：2015年11月23日-下午2:44:39
 */
public interface UserEducationBackGroundDao extends BaseDao<UserEducationBackGroundEntity>{
	/**
	 * 方法描述：根据参数查询教育背景
	 * 作        者：MaoSF
	 * 日        期：2015年11月23日-下午4:48:56
	 * @param userId（用户id）
	 * @return
	 */
	public List<UserEducationBackGroundEntity> getUserEducationbgByUserId(String userId);
	
	/**
	 * 方法描述：根据参数删除教育背景
	 * 作        者：MaoSF
	 * 日        期：2015年11月23日-下午4:52:17
	 * @param userId（用户id）
	 * @return
	 */
	public int delUserEducationbgByUserId(String userId);
}
