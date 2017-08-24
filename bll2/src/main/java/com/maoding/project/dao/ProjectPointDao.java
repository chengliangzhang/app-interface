package com.maoding.project.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.entity.ProjectPointEntity;

import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectReceivePointDao
 * 类描述：应收节点
 * 作    者：MaoSF
 * 日    期：2015年8月15日-上午10:13:28
 */
public interface ProjectPointDao extends BaseDao<ProjectPointEntity> {

	/**
	 * 方法描述：根据ID删除应收节点
	 * 作        者：wangrb
	 * 日        期：2015年8月14日-上午10:16:29
	 * @param id
	 * @return
	 */
	int deleteByProjectId(String id);
	
	/**
	 * 方法描述：根据projectId获取相关应收节点列表
	 * 作        者：wangrb
	 * 日        期：2015年8月14日-上午10:19:21
	 * @param projectId
	 * @return
	 */
	List<ProjectPointEntity> getProjectPointListByProjectId(String projectId);
	
	/**
	 * 方法描述：根据projectId并过滤一些Ids获取相关应收节点列表
	 * 作        者：wangrb
	 * 日        期：2015年8月24日-下午5:50:10
	 * @param map
	 * @return
	 */
	List<ProjectPointEntity> getRPListByProjectIdFilterIds(Map map);

	/**
	 * 方法描述：获取最大的seq
	 * 作者：MaoSF
	 * 日期：2016/11/24
	 * @param:
	 * @return:
	 */
	int getProjectPointMaxSeq(String projectId);


}
