package com.maoding.projectcost.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.projectcost.dao.ProjectCostPaymentDetailDao;
import com.maoding.projectcost.dto.ProjectCostPaymentDetailDTO;
import com.maoding.projectcost.dto.ProjectCostPointReceiveOrPayInfoDTO;
import com.maoding.projectcost.dto.ProjectCostQueryDTO;
import com.maoding.projectcost.entity.ProjectCostPaymentDetailEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectCostDetailDao
 * 类描述：项目费用详情
 * 作    者：MaoSF
 * 日    期：2015年8月10日-下午4:28:32
 */
@Service("projectCostPaymentDetailDao")
public class ProjectCostPaymentDetailDaoImpl extends GenericDao<ProjectCostPaymentDetailEntity> implements ProjectCostPaymentDetailDao {

    /**
     * 方法描述：根据pointDetailId查询收款详情
     * 作者：MaoSF
     * 日期：2017/3/9
     */
    @Override
    public List<ProjectCostPaymentDetailDTO> selectByPointDetailId(Map<String, Object> map) {
        return this.sqlSession.selectList("GetProjectCostPaymentDetailMapper.selectByPointDetailId", map);
    }

    /**
     * 方法描述：根据父节点，查询子节点或许自己节点的明细
     * 作者：MaoSF
     * 日期：2017/3/13
     */
    @Override
    public List<ProjectCostPaymentDetailEntity> getDetailByRootId(String pointDetailId) {
        return this.sqlSession.selectList("GetProjectCostPaymentDetailMapper.getDetailByRootId",pointDetailId);
    }

    @Override
    public double getSumFee(String pointDetailId) {
        return this.sqlSession.selectOne("GetProjectCostPaymentDetailMapper.getSumFee",pointDetailId);
    }

    /**
     * 方法描述：根据发起收款的id逻辑删除收款明细
     * 作者：MaoSF
     * 日期：2017/4/27
     */
    @Override
    public int updateCostPaymentDetailByPointDetailId(ProjectCostPaymentDetailEntity entity) {
        return this.sqlSession.update("ProjectCostPaymentDetailEntityMapper.updateCostPaymentDetailByPointDetailId",entity);
    }

    @Override
    public List<ProjectCostPaymentDetailDTO> listProjectPaymentFeeByPointDetailId(ProjectCostQueryDTO query) {
        return this.sqlSession.selectList("GetProjectCostMapper.listProjectPaymentFeeByPointDetailId",query);
    }

    @Override
    public List<ProjectCostPointReceiveOrPayInfoDTO> listProjectCostPointReceiveOrPayInfo(ProjectCostQueryDTO query) {
        return this.sqlSession.selectList("GetProjectCostMapper.listProjectCostPointReceiveOrPayInfo",query);
    }

    @Override
    public List<ProjectCostPointReceiveOrPayInfoDTO> listProjectCostPointNotReceiveOrPayInfo(ProjectCostQueryDTO query) {
        return this.sqlSession.selectList("GetProjectCostMapper.listProjectCostPointNotReceiveOrPayInfo",query);
    }

}
