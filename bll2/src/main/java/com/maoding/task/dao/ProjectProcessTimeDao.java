package com.maoding.task.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.task.entity.ProjectProcessTimeEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDao
 * 类描述：项目任务
 * 作    者：MaoSF
 * 日    期：2016年12月28日-上午10:13:28
 */
public interface ProjectProcessTimeDao extends BaseDao<ProjectProcessTimeEntity> {


    public List<ProjectProcessTimeEntity> getProjectProcessTime(Map<String, Object> param) ;


    public int  deleteByTargetId(String targetId);
}