package com.maoding.projectmember.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dto.ProjectTaskProcessNodeDTO;
import com.maoding.projectmember.dao.ProjectMemberDao;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.dto.UserPositionDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectCostDao
 * 类描述：项目费用Dao
 * 作    者：MaoSF
 * 日    期：2015年8月10日-下午4:28:32
 */
@Service("projectMemberDao")
public class ProjectMemberDaoImpl extends GenericDao<ProjectMemberEntity> implements ProjectMemberDao {


    /**
     * 方法描述：根据参数查询
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    @Override
    public List<ProjectMemberEntity> listProjectMember(ProjectMemberEntity projectMember) {
        return this.sqlSession.selectList("ProjectMemberEntityMapper.listProjectMember",projectMember);
    }

    @Override
    public List<ProjectMemberDTO> listProjectMemberByParam(ProjectMemberEntity projectMember) {
        return this.sqlSession.selectList("GetProjectMemberMapper.listProjectMemberByParam",projectMember);
    }

    /**
     * 方法描述：项目成员
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    @Override
    public List<ProjectTaskProcessNodeDTO> listProjectMemberGroupByParam(ProjectMemberEntity projectMember) {
        return this.sqlSession.selectList("GetProjectMemberMapper.listProjectMemberGroupByParam",projectMember);
    }

    /**
     * 方法描述：项目成员(设计人员)
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    @Override
    public List<ProjectTaskProcessNodeDTO> listProjectDesignMember(String taskId,String fastdfsUrl) {
        Map<String,Object> map = new HashMap<>();
        map.put("taskId",taskId);
        map.put("fastdfsUrl",fastdfsUrl);
        return this.sqlSession.selectList("GetProjectMemberMapper.listDesignMember",map);
    }

    /**
     * 方法描述：更新状态（status:1，职责所承担的任务已完成，0，未完成）
     * 作者：MaoSF
     * 日期：2017/6/7
     *
     * @param projectMember
     */
    @Override
    public int updateProjectMemberStatus(ProjectMemberEntity projectMember) {
        return this.sqlSession.update("ProjectMemberEntityMapper.updateProjectMemberStatus",projectMember);
    }

    /**
     * 方法描述：逻辑删除
     * 作者：MaoSF
     * 日期：2017/6/7
     */
    @Override
    public int deleteProjectMember(ProjectMemberEntity projectMember) {
        return this.sqlSession.update("ProjectMemberEntityMapper.deleteProjectMember",projectMember);
    }

    /**
     * 方法描述：成员角色
     * 作者：MaoSF
     * 日期：2017/6/13
     */
    @Override
    public List<UserPositionDTO> getUserPositionForProject(String projectId, String companyId, String accountId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        map.put("accountId",accountId);
        return this.sqlSession.selectList("GetProjectMemberMapper.getUserPositionForProject",map);
    }

    /**
     * 方法描述：获取设计人员
     * 作者：MaoSF
     * 日期：2017/6/24
     */
    @Override
    public String getDesignUserByTaskId(String taskId) {
        return this.sqlSession.selectOne("GetProjectMemberMapper.getDesignUser",taskId);
    }

    /**
     * 方法描述：获取任务节点下所有的人员（用于任务copy）
     * 作者：MaoSF
     * 日期：2017/6/21
     */
    @Override
    public List<ProjectMemberEntity> listProjectMemberByTaskId(String taskId) {
        return this.sqlSession.selectList("ProjectMemberEntityMapper.listProjectMemberByTaskId",taskId);
    }
}
