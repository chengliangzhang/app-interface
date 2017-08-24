package com.maoding.project.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectProcessNodeDao;
import com.maoding.project.entity.ProjectProcessNodeEntity;
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
@Service("projectProcessNodeDao")
public class ProjectProcessNodeDaoImpl extends GenericDao<ProjectProcessNodeEntity> implements ProjectProcessNodeDao {
    @Override
    public List<ProjectProcessNodeEntity> selectByProcessId(String processId) {
        return this.sqlSession.selectList("ProjectProcessNodeEntityMapper.selectByProcessId", processId);
    }


    public List<ProjectProcessNodeEntity> selectByParam(Map<String,Object> paraMap){
        return this.sqlSession.selectList("ProjectProcessNodeEntityMapper.selectByParam", paraMap);
    }

    /**
     * 方法描述：获取节点（taskId，projectId）
     * 作者：MaoSF
     * 日期：2017/5/20
     */
    @Override
    public List<ProjectProcessNodeEntity> getProcessNodeByParam(Map<String, Object> paraMap) {
        return this.sqlSession.selectList("ProjectProcessNodeEntityMapper.selectNodeByTaskManageId", paraMap);
    }

    public List<ProjectProcessNodeEntity> selectNodeByTaskManageId(String taskManageId){
        Map<String,Object> map = new HashMap<>();
        map.put("taskManageId",taskManageId);
        return this.sqlSession.selectList("ProjectProcessNodeEntityMapper.selectNodeByTaskManageId", map);
    }

    /**
     * 方法描述：根据processId,companyUserId,seq,查询节点（一个节点，一个companyUserId只会产生一条数据）
     * 作者：MaoSF
     * 日期：2017/2/24
     */
    @Override
    public ProjectProcessNodeEntity getNodeByCompanyUserAndSeq(String processId, String companyUserId, int seq) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("processId",processId);
        map.put("companyUserId",companyUserId);
        map.put("seq",seq);
        return this.sqlSession.selectOne("ProjectProcessNodeEntityMapper.getNodeByCompanyUserAndSeq", map);
    }

    /**
     * 方法描述：设置完成时间
     * 作者：MaoSF
     * 日期：2017/3/11
     */
    @Override
    public int updateProcessNodeComplete(String id) {
        return this.sqlSession.update("ProjectProcessNodeEntityMapper.updateProcessNodeComplete",id);
    }

}
