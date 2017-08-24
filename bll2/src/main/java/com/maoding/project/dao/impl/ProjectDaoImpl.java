package com.maoding.project.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.ProjectUserDTO;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.dto.ProjectTableDTO;
import com.maoding.project.entity.ProjectEntity;
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
     * 方法描述：经营列表
     * 作者：MaoSF
     * 日期：2016/7/29
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTableDTO> getProjectListByCondition(Map<String, Object> param) {
        return this.sqlSession.selectList("GetProjectEntityPageMapper.getProjectListByCondition",param);
    }


    /**
     * 方法描述：经营列表总数据（用于分页）
     * 作者：MaoSF
     * 日期：2016/7/29
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public int getProjectListByConditionCount(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectEntityPageMapper.getProjectListByConditionCount", param);
    }

    /**
     * 方法描述：（经营列表中）--获取设计人
     * 作者：MaoSF
     * 日期：2016/8/2
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public List<CompanyDTO> getDesignCompanys(Map<String, Object> param) {
        return this.sqlSession.selectList("GetProjectEntityPageMapper.getDesignCompanys",param);
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
     * 方法描述：更改项目状态（删除项目使用）
     * 作者：MaoSF
     * 日期：2016/11/29
     *
     * @param entity
     * @param:
     * @return:
     */
    @Override
    public int updatePstatus(ProjectEntity entity) {
        return this.sqlSession.update("ProjectEntityMapper.updatePstatus",entity);
    }


/*******************************新的接口**********************************************/


    /**
     * 方法描述：获取项目列表(数量)
     * 作者：chenzhujie
     * 日期：2016/7/29
     * @param:
     * @return:
     */
    public int newGetProjectsCountByParam(Map<String, Object> param) throws Exception{
        return this.sqlSession.selectOne("ProjectEntityMapper.newGetProjectsCountByParam",param);
    }

    /**
     * 方法描述：查询签发组织
     * 作者：Chenzhujie
     * 日期：2016/12/13
     * @param:dto
     * @return:
     */
    public List<Map<String,Object>> selectSignTeam(Map<String, Object> param)throws Exception{
        return this.sqlSession.selectList("ProjectEntityMapper.selectSignTeam",param);
    }

    /**
     * 方法描述：任务管理列表
     * 作者：MaoSF
     * 日期：2016/12/5
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTableDTO> getProjectTaskManageByCondition(Map<String, Object> param) {
        return this.sqlSession.selectList("GetProjectEntityPageMapper.getProjectTaskManageByCondition", param);
    }

    /**
     * 方法描述：任务管理总条数
     * 作者：MaoSF
     * 日期：2016/12/5
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public int getProjectTaskManageCountByCondition(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectEntityPageMapper.getProjectTaskManageCountByCondition", param);
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
     *
     * @param param（id，companyUserId）
     * @param:
     * @return:
     */
    @Override
    public int getProjectFlag(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectEntityPageMapper.getProjectFlag",param);
    }

    /**
     * 方法描述：获取当前人与该项目的关系 flag：1（参与），0（无关系）用于项目群删除人员做判断
     * 作者：MaoSF
     * 日期：2017/2/14
     *
     * @param param（id，userId）
     * @param:
     * @return:
     */
    @Override
    public int getProjectFlagByUserId(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectEntityPageMapper.getProjectFlagByUserId",param);
    }

    /**
     * 方法描述：获取全部的project
     * 作者：TangY
     * 日期：2016/7/29
     *
     * @param:int
     * @return:
     */
    @Override
    public List<ProjectEntity> selectAll() {
        return this.sqlSession.selectList("ProjectEntityMapper.selectAll");
    }

    /**
     * 方法描述：项目成员（项目群组展示）
     * 作者：MaoSF
     * 日期：2017/5/23
     *
     * @param projectId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectUserDTO> getProjectParticipation(String projectId,String fastdfsUrl) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("fastdfsUrl",fastdfsUrl);
        return this.sqlSession.selectList("GetProjectParticipationMapper.getProjectParticipation",map);
    }
}


