package com.maoding.project.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.ProjectUserDTO;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.dto.ProjectTableDTO;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.v2.project.dto.V2ProjectTableDTO;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDao
 * 类描述：项目（dao）
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:20:47
 */
public interface ProjectDao extends BaseDao<ProjectEntity> {

    /**
     * 方法描述：经营列表
     * 作者：MaoSF
     * 日期：2016/7/29
     * @param:
     * @return:
     */
    List<ProjectTableDTO> getProjectListByCondition(Map<String, Object> param);


    /**
     * 方法描述：经营列表总数据（用于分页）
     * 作者：MaoSF
     * 日期：2016/7/29
     * @param:
     * @return:
     */
    int getProjectListByConditionCount(Map<String, Object> param);


    /**
     * 方法描述：（经营列表中）--获取设计人
     * 作者：MaoSF
     * 日期：2016/8/2
     * @param:
     * @return:
     */
    List<CompanyDTO> getDesignCompanys(Map<String, Object> param);

    /**
     * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )
     * 作   者：LY
     * 日   期：2016/8/8 14:38
     */
    List<ProjectDTO> getProjectListByCompanyId(Map<String, Object> param);



    /**
     * 方法描述：更改项目状态（删除项目使用）
     * 作者：MaoSF
     * 日期：2016/11/29
     * @param:
     * @return:
     */
    int updatePstatus(ProjectEntity entity);

  //============================================新接口===============================================================================
    /**
     * 方法描述：获取项目列表(数量)
     * 作者：chenzhujie
     * 日期：2016/7/29
     * @param:
     * @return:
     */
    int newGetProjectsCountByParam(Map<String, Object> param) throws Exception;


    /**
     * 方法描述：查询签发组织
     * 作者：Chenzhujie
     * 日期：2016/12/13
     * @param:dto
     * @return:
     */
    List<Map<String,Object>> selectSignTeam(Map<String, Object> param)throws Exception;

    /**
     * 方法描述：任务管理列表
     * 作者：MaoSF
     * 日期：2016/12/5
     * @param:
     * @return:
     */
    List<ProjectTableDTO> getProjectTaskManageByCondition(Map<String, Object> param);

    /**
     * 方法描述：任务管理总条数
     * 作者：MaoSF
     * 日期：2016/12/5
     * @param:
     * @return:
     */
    int getProjectTaskManageCountByCondition(Map<String, Object> param);

    /**
     * 方法描述：v2项目列表
     * 作者：chenzhujie
     * 日期：2016/12/24
     */
    List<V2ProjectTableDTO> getV2ProjectList(Map<String, Object> param);

    /**
     * 方法描述：获取项目的flag值
     * 作者：MaoSF
     * 日期：2017/2/14
     * @param:
     * @return:
     */
    int getProjectFlag(Map<String, Object> param);

    /**
     * 方法描述：获取当前人与该项目的关系 flag：1（参与），0（无关系）用于项目群删除人员做判断
     * 作者：MaoSF
     * 日期：2017/4/12
     * @param:
     * @return:
     */
    int getProjectFlagByUserId(Map<String, Object> param);

    /**
     * 方法描述：获取全部的project
     * 作者：TangY
     * 日期：2016/7/29
     * @param:int
     * @return:
     */
    List<ProjectEntity> selectAll();

    /**
     * 方法描述：项目成员（项目群组展示）
     * 作者：MaoSF
     * 日期：2017/5/23
     * @param:
     * @return:
     */
    List<ProjectUserDTO> getProjectParticipation(String projectId,String fastdfsUrl);
}
