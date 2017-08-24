package com.maoding.project.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectProcessDao;
import com.maoding.project.dto.ProjectProcessDataDTO;
import com.maoding.project.dto.ProjectProcessUserDTO;
import com.maoding.project.entity.ProjectProcessEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectReceivePointDao
 * 类描述：服务内容
 * 作    者：wangrb
 * 日    期：2015年8月14日-上午10:13:28
 */
@Service("projectProcessDao")
public class ProjectProcessDaoImpl extends GenericDao<ProjectProcessEntity> implements ProjectProcessDao {

    @Value("${fastdfs.url}")
    protected String fastdfsUrl;

    @Override
    public int insert(ProjectProcessEntity entity){
        entity.setSeq(this.getProcessMaxSeq(entity.getTaskManageId()));
        return super.insert(entity);
    }

    @Override
    public List<ProjectProcessDataDTO> selectByTaskManageId(String taskId) {
        return this.selectByTaskManageId(taskId,null);//查询任务中所有的流程
    }

    /**
     * 方法描述：根据流程id查询流程列表
     * 作者：MaoSF
     * 日期：2016/12/5
     */
    @Override
    public List<ProjectProcessDataDTO> selectByTaskManageId(String taskManageId, String processId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("taskId",taskManageId);
        map.put("processId",processId);
        map.put("fastdfsUrl",fastdfsUrl);
        return this.sqlSession.selectList("GetProjectProcessByTaskManageIdMapper.selectByTaskManageId", map);
    }

    /**
     * 方法描述：根据流程节点id查询流程列表
     * 作者：MaoSF
     * 日期：2016/12/5
     */
    @Override
    public ProjectProcessDataDTO selectByNodeId(String nodeId) {
        return this.sqlSession.selectOne("GetProjectProcessByTaskManageIdMapper.selectByNodeId", nodeId);
    }

    @Override
    public String selectDesignNameByTaskManageId(String taskId) {
        return this.sqlSession.selectOne("GetProjectProcessByTaskManageIdMapper.selectDesignNameByTaskManageId", taskId);
    }

    public List<ProjectProcessUserDTO>  selectProessByUserId(Map<String,Object> paraMap){
        return this.sqlSession.selectList("GetProjectProcessByUserIdMapper.selectByUserId", paraMap);
    }

    public int selectProessCountByUserId(Map<String,Object> paraMap){
        return this.sqlSession.selectOne("GetProjectProcessByUserIdMapper.selectCountByUserId", paraMap);
    }

    @Override
    public int updateByTaskManageId(Map<String, Object> paraMap) {
        return this.sqlSession.update("ProjectProcessEntityMapper.updateByTaskManageId",paraMap);
    }

    public List<ProjectProcessUserDTO> selectProessByUserIdGroupByProjectId(Map<String, Object> param){
        return this.sqlSession.selectList("GetProjectProcessByUserIdMapper.selectProessByUserIdGroupByProjectId", param);
    }

    @Override
    public int getProcessMaxSeq(String taskManageId) {
        return this.sqlSession.selectOne("ProjectProcessEntityMapper.getProcessMaxSeq",taskManageId);
    }

}
