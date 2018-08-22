package com.maoding.projectcost.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.projectcost.dto.*;
import com.maoding.projectcost.entity.ProjectCostEntity;

import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectCostDao
 * 类描述：项目费用Dao
 * 作    者：MaoSF
 * 日    期：2015年8月10日-下午4:28:32
 */
public interface ProjectCostDao extends BaseDao<ProjectCostEntity> {

    List<ProjectCostDTO> selectByParam(Map<String, Object> map);

    /**
     * 方法描述：把无费用节点的数据设置为无效
     * 作者：MaoSF
     * 日期：2017/3/7
     * @param:
     * @return:
     */
    int updateHasNoChildPoint();

    /**
     * 项目综合数据 -- 费用部分数据
     */
    ProjectAmountFee getProjectAmountFee(String projectId,String companyId);

    /**
     * 根据costId 查询应收，应付，已收，已付
     */
    ProjectCooperatorCostDTO getProjectAmountFeeByCostId(ProjectCostQueryDTO queryDTO);

    /**
     * 项目费用汇总
     */
    List<ProjectCostSummaryDTO> getProjectCostSummary(ProjectCostQueryDTO query);


    /**
     * 合作设计费查费汇总界面
     */
    List<ProjectCooperatorCostDTO> getProjectCooperatorFee(String projectId, String companyId);

    /**
     * @param pointId (费用节点的id)
     */
    ProjectCostEntity getProjectCostByPointId(String pointId);
}
