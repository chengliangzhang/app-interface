package com.maoding.task.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.task.dao.ProjectManagerDao;
import com.maoding.task.dto.ProjectManagerDTO;
import com.maoding.task.entity.ProjectManagerEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/10/18.
 */
@Service("projectManagerDao")
public class ProjectManagerDaoImpl extends GenericDao<ProjectManagerEntity> implements ProjectManagerDao {

    /**
     * 方法描述：根据projectId,companyId查询经营负责人和项目负责人
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectManagerDTO> getProjectManager(String projectId, String companyId,String fastdfsUrl){
        Map<String,String> map = new HashMap<String,String>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        map.put("fastdfsUrl",fastdfsUrl);
        return this.sqlSession.selectList("ProjectManagerEntityMapper.getProjectManager",map);
    }

    @Override
    public List<ProjectManagerEntity> getProjectManagerByParam(Map<String, Object> param) throws Exception {
        return this.sqlSession.selectList("ProjectManagerEntityMapper.selectByParam",param);
    }

    /**
     * 方法描述：根据projectId,companyId查询经营负责人和项目负责人
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public ProjectManagerEntity getProjectOperaterManager(String projectId, String companyId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectOne("ProjectManagerEntityMapper.getProjectOperaterManager",map);
    }

    @Override
    public List<ProjectManagerEntity> selectByParam(Map<String, Object> map) {
        return this.sqlSession.selectList("ProjectManagerEntityMapper.selectByParam", map);
    }

    /**
     * 方法描述：根据projectId,companyId查询经营负责人和项目负责人
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public ProjectManagerEntity getProjectDesignManager(String projectId, String companyId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectOne("ProjectManagerEntityMapper.getProjectDesignManager",map);
    }

    /**
     * 方法描述：删除公司在该项目的经营负责人及企业负责人（用于删除任务，更换任务组织）使用
     * 作者：MaoSF
     * 日期：2017/3/14
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public int deleteProjectManage(String projectId, String companyId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.delete("ProjectManagerEntityMapper.deleteProjectManage",map);
    }
}
