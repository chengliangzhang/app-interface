package com.maoding.task.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.task.dao.ProjectProcessTimeDao;
import com.maoding.task.entity.ProjectProcessTimeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskRelationDaoImpl
 * 类描述：时间变更
 * 作    者：MaoSF
 * 日    期：2016年12月28日-上午10:13:28
 */
@Service("projectProcessTimeDao")
public  class ProjectProcessTimeDaoImpl extends GenericDao<ProjectProcessTimeEntity> implements ProjectProcessTimeDao {

    @Override
    public List<ProjectProcessTimeEntity> getProjectProcessTime(Map<String,Object> param) {
         return this.sqlSession.selectList("ProjectProcessTimeEntityMapper.selectByParam",param);
    }


    @Override
    public int  deleteByTargetId (String targetId){
        return  this.sqlSession.delete("ProjectProcessTimeEntityMapper.deleteByTargetId",targetId);
    }
}