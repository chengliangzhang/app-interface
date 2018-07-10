package com.maoding.project.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.org.dto.ProjectUserDTO;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dto.*;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.task.dto.HomeDTO;
import com.maoding.v2.project.dto.V2ProjectTableDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDaoImpl
 * 类描述：projectDao
 * 作    者：ChenZJ
 * 日    期：2016年7月7日-上午5:21:29
 */
@Service("projectDao")
public class ProjectDaoImpl extends GenericDao<ProjectEntity> implements ProjectDao {

    /**
     * 方法描述：经营列表总数据（用于分页）
     * 作者：MaoSF
     * 日期：2016/7/29
     */
    @Override
    public int getProjectListByConditionCount(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectEntityPageMapper.getProjectListByConditionCount", param);
    }

    /**
     * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )
     * 作   者：LY
     * 日   期：2016/8/8 14:38
     */
    @Override
    public List<ProjectDTO> getProjectListByCompanyId(Map<String, Object> param){
        return this.sqlSession.selectList("GetProjectEntityPageMapper.getProjectListByCompanyId",param);
    }


    /**
     * 方法描述：v2项目列表
     * 作者：chenzhujie
     * 日期：2016/12/24
     */
    @Override
    public List<V2ProjectTableDTO> getV2ProjectList(Map<String, Object> param) {
        return this.sqlSession.selectList("GetProjectEntityPageMapper.getV2ProjectList",param);
    }

    /**
     * 方法描述：获取项目的flag值
     * 作者：MaoSF
     * 日期：2017/2/14
     * @param param（id，companyUserId）
     */
    @Override
    public int getProjectFlag(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectEntityPageMapper.getProjectFlag",param);
    }

    /**
     * 方法描述：获取全部的project
     * 作者：TangY
     * 日期：2016/7/29
     */
    @Override
    public List<ProjectEntity> selectAll() {
        return this.sqlSession.selectList("ProjectEntityMapper.selectAll");
    }

    /**
     * 方法描述：项目成员（项目群组展示）
     * 作者：MaoSF
     * 日期：2017/5/23
     */
    @Override
    public List<ProjectUserDTO> getProjectParticipation(String projectId,String fastdfsUrl) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("fastdfsUrl",fastdfsUrl);
        return this.sqlSession.selectList("GetProjectParticipationMapper.getProjectParticipation",map);
    }

    @Override
    public ProjectCountDTO getMyProjectCount(Map<String, Object> param){
        return this.sqlSession.selectOne("GetProjectEntityPageMapper.getMyProjectCount",param);
    }

    @Override
    public ProjectCountDTO getAllProjectCount(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectEntityPageMapper.getAllProjectCount",param);
    }

    @Override
    public List<ProjectProgressDTO> getMyProjectList(Map<String, Object> param) {
        return this.sqlSession.selectList("GetProjectEntityPageMapper.getMyProjectList",param);
    }

    @Override
    public HomeDTO getProjectCountForHomeData(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectEntityPageMapper.getProjectCountForHomeData",param);
    }

    @Override
    public String getEnterpriseName(String enterpriseOrgId) {
        return sqlSession.selectOne("EnterpriseMapper.getEnterpriseName",enterpriseOrgId);
    }

    @Override
    public String getEnterpriseNameByProjectId(String projectId) {
        return sqlSession.selectOne("EnterpriseMapper.getEnterpriseNameByProjectId", projectId);
    }

    @Override
    public List<ProjectSimpleDataDTO> getProjectListForLaborHour(Map<String, Object> param) {
        return this.sqlSession.selectList("GetProjectEntityPageMapper.getProjectListForLaborHour",param);
    }

    @Override
    public String getProjectName(String id) {
        ProjectEntity p = selectById(id);
        return (p != null) ? p.getProjectName() : null;
    }

    /**
     * @param query 项目查询条件
     * @return 默认的功能分类列表
     * @description 获取默认的功能分类列表
     * @author 张成亮
     * @date 2018/6/25 17:50
     **/
    @Override
    public List<ProjectPropertyDTO> listBuiltTypeConst(QueryProjectDTO query) {
        return this.sqlSession.selectList("ProjectMapper.listBuiltTypeConst", query);
    }
    @Override
    public List<ProjectPropertyDTO> listBuiltTypeConstEx(QueryProjectDTO query) {
        return this.sqlSession.selectList("ProjectMapper.listBuiltTypeConstEx", query);
    }

    /**
     * @param query 项目查询条件
     * @return 自定义的功能分类列表
     * @description 获取自定义的功能分类列表
     * @author 张成亮
     * @date 2018/6/25 17:53
     **/
    @Override
    public List<ProjectPropertyDTO> listBuiltTypeCustom(QueryProjectDTO query) {
        return this.sqlSession.selectList("ProjectMapper.listBuiltTypeCustom", query);
    }
}


