package com.maoding.system.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.system.entity.VersionEntity;

import java.util.Map;



/**深圳市设计同道技术有限公司
 * 类    名：VersionDao
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2015年12月30日-上午11:28:15
 */
public interface VersionDao extends BaseDao<VersionEntity> {
	/**
	 * 方法描述：系统更新 
	 * 作        者：TangY
	 * 日        期：2016年3月25日-上午11:29:05
	 * @param paraMap
	 * @return
	 */
	public VersionEntity selectByPlatform(Map<String, Object> paraMap);
}
