package com.maoding.project.dao.impl;

import java.util.List;
import java.util.Map;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectFeeDao;
import com.maoding.project.dto.ProjectAmountFeeDTO;
import com.maoding.project.dto.ProjectFeeDTO;
import com.maoding.project.entity.ProjectFeeEntity;
import org.springframework.stereotype.Service;
/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectInvoiceMoneyDaoImpl
 * 类描述：项目开票DaoImpl
 * 作    者：MaoSF
 * 日    期：2015年8月18日-下午2:22:23
 */
@Service("projectFeeDao")
public class ProjectFeeDaoImpl extends GenericDao<ProjectFeeEntity> implements ProjectFeeDao {

	@Override
	public List<ProjectFeeEntity> getProjectFeeByParameter(Map<String,Object> map){
		return this.sqlSession.selectList("ProjectFeeEntityMapper.getProjectFeeByParameter",map);
	}

	/**
	 * 方法描述：根据节点查询费用
	 * 作        者：MaoSF
	 * 日        期：2015年8月10日-下午4:18:42
	 *
	 * @param map （projectId，type：1:开票，2：收款,3合作设计费付款，4合作设计费到款，5技术审查费付款，6技术审查费到款）
	 * @return
	 */
	@Override
	public List<ProjectFeeDTO> getProjectFeeDetailByParameter(Map<String, Object> map) {
		return this.sqlSession.selectList("GetProjectFeeMapper.getProjectFeeDetailByParameter",map);
	}

	/**
	 * 方法描述：根据服务内容ids查询费用
	 * 作        者：MaoSF
	 * 日        期：2015年8月10日-下午4:18:42
	 *
	 * @param map （projectId，serverContentIds)
	 * @return
	 */
	@Override
	public List<ProjectFeeDTO> getProjectDesignFeeDetailByParameter(Map<String, Object> map) {
		return this.sqlSession.selectList("GetProjectFeeMapper.getProjectDesignFeeDetailByParameter",map);
	}

	@Override
	public int deleteByPointId(String pointId) {
		return this.sqlSession.delete("ProjectFeeEntityMapper.deleteByPointId", pointId);
	}

	@Override
	public int updateStatusByPointId(Map<String, Object> map) {
		return this.sqlSession.update("ProjectFeeEntityMapper.updateStatusByPointId", map);
	}

	/**
	 * 方法描述：根据服务内容id查询到款，付款总额
	 * 作者：MaoSF
	 * 日期：2016/8/8
	 *
	 * @param serverContentId
	 * @param:
	 * @return:
	 */
	@Override
	public ProjectAmountFeeDTO getProjectDesignFeeAmount(String serverContentId) {
		return this.sqlSession.selectOne("GetProjectFeeMapper.getProjectDesignFeeAmount",serverContentId);
	}

}
