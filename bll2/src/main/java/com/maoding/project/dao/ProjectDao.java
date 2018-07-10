package com.maoding.project.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.org.dto.ProjectUserDTO;
import com.maoding.project.dto.*;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.task.dto.HomeDTO;
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
     * 方法描述：经营列表总数据（用于分页）
     * 作者：MaoSF
     * 日期：2016/7/29
     * @param:
     * @return:
     */
    int getProjectListByConditionCount(Map<String, Object> param);

    /**
     * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )
     * 作   者：LY
     * 日   期：2016/8/8 14:38
     */
    List<ProjectDTO> getProjectListByCompanyId(Map<String, Object> param);

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
     */
    int getProjectFlag(Map<String, Object> param);


    /**
     * 方法描述：获取全部的project
     * 作者：TangY
     * 日期：2016/7/29
     */
    List<ProjectEntity> selectAll();

    /**
     * 方法描述：项目成员（项目群组展示）
     * 作者：MaoSF
     * 日期：2017/5/23
     */
    List<ProjectUserDTO> getProjectParticipation(String projectId,String fastdfsUrl);

    /**
     * 首页项目数据，我参与的与我关注的项目
     * @param param，companyId，companyUserId
     */
    ProjectCountDTO getMyProjectCount(Map<String, Object> param);

    /**
     * 首页项目数据，所有项目的数据（进行中，已完成）统计
     * @param param，companyId
     */
    ProjectCountDTO getAllProjectCount(Map<String, Object> param);


    /**
     * 首页项目数据，我参与的与我关注的项目
     * @param param，companyId，companyUserId，若有分页，加分页参数
     */
    List<ProjectProgressDTO> getMyProjectList(Map<String, Object> param);


    HomeDTO getProjectCountForHomeData(Map<String, Object> param);

    /**
     * 获取甲方名字
     */
    String getEnterpriseName(String enterpriseOrgId);

    /**
     * 获取甲方名字
     */
    String getEnterpriseNameByProjectId(String projectId);

    /**
     * 工时申报项目
     */
    List<ProjectSimpleDataDTO> getProjectListForLaborHour(Map<String, Object> param);

    String getProjectName(String id);

    /**
     * @description 获取默认的功能分类列表
     * @author  张成亮
     * @date    2018/6/25 17:50
     * @param   query 项目查询条件
     * @return  默认的功能分类列表
     **/
    List<ProjectPropertyDTO> listBuiltTypeConst(QueryProjectDTO query);
    List<ProjectPropertyDTO> listBuiltTypeConstEx(QueryProjectDTO query);


    /**
     * @description 获取自定义的功能分类列表
     * @author  张成亮
     * @date    2018/6/25 17:53
     * @param   query 项目查询条件
     * @return  自定义的功能分类列表
     **/
    List<ProjectPropertyDTO> listBuiltTypeCustom(QueryProjectDTO query);
}
