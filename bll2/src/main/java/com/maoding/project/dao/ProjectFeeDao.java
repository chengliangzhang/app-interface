package com.maoding.project.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.dto.ProjectAmountFeeDTO;
import com.maoding.project.dto.ProjectFeeDTO;
import com.maoding.project.entity.ProjectFeeEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectInvoiceMoneyAttachDao
 * 类描述：项目附件Dao
 * 作    者：MaoSF
 * 日    期：2015年8月10日-下午4:28:32
 */
public interface ProjectFeeDao extends BaseDao<ProjectFeeEntity> {

	/**
	 * 方法描述：根据节点查询费用
	 * 作        者：MaoSF
	 * 日        期：2015年8月10日-下午4:18:42
	 * @param map（pointId，type：1:开票，2：收款,3合作设计费付款，4合作设计费到款，5技术审查费付款，6技术审查费到款）
	 * @return
	 */
	public List<ProjectFeeEntity> getProjectFeeByParameter(Map<String, Object> map);


	/**
	 * 方法描述：根据节点查询费用
	 * 作        者：MaoSF
	 * 日        期：2015年8月10日-下午4:18:42
	 * @param map（projectId，type：1:开票，2：收款,3合作设计费付款，4合作设计费到款，5技术审查费付款，6技术审查费到款）
	 * @return
	 */
	public List<ProjectFeeDTO> getProjectFeeDetailByParameter(Map<String, Object> map);


	/**
	 * 方法描述：根据服务内容ids查询费用
	 * 作        者：MaoSF
	 * 日        期：2015年8月10日-下午4:18:42
	 * @param map（projectId，serverContentIds)
	 * @return
	 */
	public List<ProjectFeeDTO> getProjectDesignFeeDetailByParameter(Map<String, Object> map);

	
	/**
	 * 方法描述：根据pointId删除
	 * 作        者：MaoSF
	 * 日        期：2016年4月25日-下午7:05:56
	 * @param pointId
	 * @return
	 */
	public int deleteByPointId(String pointId);
	
	/**
	 * 方法描述：根据节点批量修改状态
	 * 作        者：MaoSF
	 * 日        期：2016年6月17日-上午11:58:40
	 * @param map
	 * @return
	 */
	public int updateStatusByPointId(Map<String, Object> map);

	/**
	 * 方法描述：根据服务内容id查询到款，付款总额
	 * 作者：MaoSF
	 * 日期：2016/8/8
	 * @param:
	 * @return:
	 */
	public ProjectAmountFeeDTO getProjectDesignFeeAmount(String serverContentId);
}
