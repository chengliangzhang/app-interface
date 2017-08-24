package com.maoding.project.dao.impl;



import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectProcessInstanceDao;
import com.maoding.project.entity.ProjectProcessInstanceEntity;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectReceivePointDao
 * 类描述：服务内容
 * 作    者：wangrb
 * 日    期：2015年8月14日-上午10:13:28
 */
@Service("projectProcessInstanceDao")
public class ProjectProcessInstanceDaoImpl extends GenericDao<ProjectProcessInstanceEntity> implements ProjectProcessInstanceDao {


    /**
     * 方法描述：
     * 作者：MaoSF
     * 日期：2016/10/27
     *
     * @param processId
     * @param:
     * @return:
     */
    @Override
    public ProjectProcessInstanceEntity getProjectProcessInstanceByProcessId(String processId) {
        return this.sqlSession.selectOne("ProjectProcessInstanceEntityMapper.getProjectProcessInstanceByProcessId",processId);
    }

    /**
     * 方法描述：根据任务id查找流程实例
     * 作者：MaoSF
     * 日期：2016/10/27
     *
     * @param taskTaskManageId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectProcessInstanceEntity> getProjectProcessInstanceByTaskManageId(String taskTaskManageId) {
        return this.sqlSession.selectList("GetProjectProcessInstanceByTaskManageIdMapper.getProjectProcessInstanceByTaskManageId",taskTaskManageId);
    }

    /**
     * 方法描述：根据流程id删除流程实例
     * 作者：MaoSF
     * 日期：2016/10/31
     *
     * @param processId
     * @param:
     * @return:
     */
    @Override
    public int deleteByProcessId(String processId) {
        return this.sqlSession.delete("ProjectProcessInstanceEntityMapper.deleteByProcessId",processId);
    }
}
