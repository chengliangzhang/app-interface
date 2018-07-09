package com.maoding.projectcost.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.projectcost.dao.ProjectCostDao;
import com.maoding.projectcost.dto.*;
import com.maoding.projectcost.entity.ProjectCostEntity;
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
@Service("projectCostDao")
public class ProjectCostDaoImpl extends GenericDao<ProjectCostEntity> implements ProjectCostDao {

    @Override
    public List<ProjectCostDTO> selectByParam(Map<String, Object> map) {
        return this.sqlSession.selectList("GetProjectCostMapper.selectByParam", map);
    }

    /**
     * 方法描述：把无费用节点的数据设置为无效
     * 作者：MaoSF
     * 日期：2017/3/7
     *
     * @param:
     * @return:
     */
    @Override
    public int updateHasNoChildPoint() {
        return this.sqlSession.update("ProjectCostEntityMapper.updateHasNoChildPoint");
    }

    @Override
    public ProjectAmountFee getProjectAmountFee(String projectId, String companyId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectOne("GetProjectCostMapper.getProjectAmountFee", map);
    }

    @Override
    public List<ProjectCostSummaryDTO> getProjectCostSummary(ProjectCostQueryDTO query) {
        return this.sqlSession.selectList("GetProjectCostSummaryMapper.getProjectCostSummary", query);
    }

    @Override
    public List<ProjectCooperatorCostDTO> getProjectCooperatorFee(String projectId, String companyId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectList("GetProjectCostMapper.getProjectTechAmountFee", map);
    }
}
