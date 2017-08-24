package com.maoding.system.dao.impl;

import java.util.Map;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.system.dao.VersionDao;
import com.maoding.system.entity.VersionEntity;
import org.springframework.stereotype.Service;



/**深圳市设计同道技术有限公司
 * 类    名：VersionDaoImpl
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2015年12月30日-上午11:28:41
 */
@Service("versionDao")
public class VersionDaoImpl extends GenericDao<VersionEntity> implements VersionDao {

	public VersionEntity selectByPlatform(Map <String,Object> paraMap){
		return this.sqlSession.selectOne("VersionEntityMapper.selectByPlatform", paraMap);
	}
}
