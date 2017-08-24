package com.maoding.project.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectProcessNodeHistoryDao;
import com.maoding.project.entity.ProjectProcessNodeHistoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectReceivePointDao
 * 类描述：服务内容
 * 作    者：wangrb
 * 日    期：2015年8月14日-上午10:13:28
 */
@Service("projectProcessNodeHistoryDao")
public class ProjectProcessNodeHistoryDaoImpl extends GenericDao<ProjectProcessNodeHistoryEntity> implements ProjectProcessNodeHistoryDao {

    @Override
    public int insert(ProjectProcessNodeHistoryEntity entity){
        int seq = getMaxSeqByProcessInstanceId(entity.getProcessInstanceId());
        entity.setSeq(seq);
        return  super.insert(entity);
    }
    /**
     * 方法描述：根据nodeId获取最后一次更新节点的数据
     * 作者：MaoSF
     * 日期：2016/10/28
     *
     * @param processNodeId
     * @param:
     * @return:
     */
    @Override
    public ProjectProcessNodeHistoryEntity getLastUpdateProcessNode(String processNodeId) {
        return this.sqlSession.selectOne("ProjectProcessNodeHistoryEntityMapper.getLastUpdateProcessNode",processNodeId);
    }

    /**
     * 方法描述：根据processId获取最后一次更新节点的数据
     * 作者：MaoSF
     * 日期：2016/10/28
     *
     * @param processId
     * @param:
     * @return:
     */
    @Override
    public ProjectProcessNodeHistoryEntity getLastProjectProcessNodeHistory(String processId) {
        return this.sqlSession.selectOne("ProjectProcessNodeHistoryEntityMapper.getLastProjectProcessNodeHistory",processId);
    }

    /**
     * 方法描述：根据nodeId获取最后一次更新节点的数据
     * 作者：MaoSF
     * 日期：2016/10/28
     *
     * @param processInstanceId
     * @param:
     * @return:
     */
    @Override
    public ProjectProcessNodeHistoryEntity getLastUpdateProcessInstanceId(String processInstanceId) {
        return this.sqlSession.selectOne("ProjectProcessNodeHistoryEntityMapper.getLastUpdateProcessInstanceId",processInstanceId);
    }

    @Override
    public int deleteByProcessInstanceId(String processInstanceId) {
        return this.sqlSession.delete("ProjectProcessNodeHistoryEntityMapper.deleteByProcessInstanceId",processInstanceId);
    }

    /**
     * 方法描述：根据流程实例获取流程历史记录
     * 作者：MaoSF
     * 日期：2016/11/1
     *
     * @param processInstanceId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectProcessNodeHistoryEntity> getByProcessInstanceId(String processInstanceId) {
        return this.sqlSession.selectList("ProjectProcessNodeHistoryEntityMapper.getByProcessInstanceId",processInstanceId);
    }


    /**
     * 方法描述：根据nodeId获取最后一次更新节点的数据
     * 作者：MaoSF
     * 日期：2016/10/28
     *
     * @param processInstanceId
     * @param:
     * @return:
     */
    @Override
    public  int  getMaxSeqByProcessInstanceId(String processInstanceId){
        return this.sqlSession.selectOne("ProjectProcessNodeHistoryEntityMapper.selectByInstanceId",processInstanceId);
    }
}
