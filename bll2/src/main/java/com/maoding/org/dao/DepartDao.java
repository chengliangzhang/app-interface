package com.maoding.org.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.org.dto.DepartDTO;
import com.maoding.org.dto.DepartDataDTO;
import com.maoding.org.dto.DepartRoleDTO;
import com.maoding.org.dto.UserDepartDTO;
import com.maoding.org.entity.DepartEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DepartDao
 * 类描述：部门 Dao
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:42:48
 */
public interface DepartDao extends BaseDao<DepartEntity>{
	
	/**
	 * 方法描述：根据companyId查询Departs（部门）
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @param paraMap（companyId（公司ID）,departLevel（部门层级）,pid（部门父ID）,departType（部门type）type:0:部门，1：分公司）
	 * @return
	 */
	List<DepartDataDTO> getDepartByCompanyId(Map<String, Object> paraMap);


	/**
	 * 方法描述：根据companyId和userId查询Departs（部门）包含公司
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @param paraMap（companyId（公司ID）,userId（用户Id））
	 * @return
	 */
	List<DepartEntity> getDepartByUserIdContentCompany(Map<String, Object> paraMap);

	/**
	 * 方法描述：根据companyId和userId查询Departs（部门）包含公司
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @param paraMap（companyId（公司ID）,userId（用户Id））
	 * @return
	 */
	List<DepartEntity> getDepartByUserIdContentCompanyInterface(Map<String, Object> paraMap);
	
	/**
	 * 方法描述：根据部门名称和父id查找部门（用于添加部门，验证是否在同级下面是否已经存在）
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:22:23
	 * @param paraMap(companyId（公司ID）,departDepart（部门名称）,pid（部门父ID）
	 * @return
	 */
	DepartEntity getByDepartNameAndPid(Map<String, Object> paraMap);
	
	/**
	 * 方法描述：根据部门路径（父级部分的id拼接）查找子部门（可包含当前部门）【用于修改部门，修改父部门时使用】
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:48:56
	 * @param departPath
	 * @return
	 */
	List<DepartEntity> getDepartsByDepartPath(String departPath);

	/**
	 * 方法描述：根据部门路径（父级部分的id拼接）查找父部门（可包含当前部门
	 * 作        者：MaoSF
	 * 日        期：2016年7月9日-上午11:48:56
	 * @param departPath
	 * @return
	 */
	List<DepartEntity> getParentDepartsByDepartPath(String departPath);

	/**
	 * 方法描述：查询部门列表
	 * 作        者：MaoSF
	 * 日        期：2016年6月16日-上午11:05:07
	 * @param param{companyIds}
	 * @return
	 */
	List<DepartEntity> selectDepartNodesByCompanyIds(Map<String, Object> param);
	/**
	 * 方法描述：查询部门边列表
	 * 作        者：MaoSF
	 * 日        期：2016年6月16日-上午11:05:43
	 * @param param{companyIds}
	 * @return
	 */
	List<Map<String, Object>>selectDepartEdgesByCompanyIds(Map<String, Object> param);

	/**
	 * 方法描述：根据departPath删除（批量删除，删除自己及所有子部门）逻辑删除
	 * 作        者：MaoSF
	 * 日        期：2016年7月14日-下午8:21:40
	 * @param departPath
	 * @return
	 */
	int deleteByDepartPath(String departPath);


	/**
	 * 方法描述：根据companyId查询Departs（部门）
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @param paraMap（companyId（公司ID）,departLevel（部门层级）,pid（部门父ID）,departType（部门type）type:0:部门，1：分公司）
	 * @return
	 */
	List<DepartEntity> getDepartByCompanyIdWS(Map<String, Object> paraMap);


	/**
	 *selectDepartByParam
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @param paraMap userId
	 * @return
	 */
	List<DepartEntity> selectDepartByParam(Map<String, Object> paraMap);
	/**
	 *selectOneDepartCompanyId
	 * 作        者：TangY
	 * 日        期：2016年7月8日-下午3:32:16
	 * @return
	 */
	List<DepartEntity> selectStairDepartCompanyId(Map<String, Object> params);

	/**
	 * 方法描述：获取我所在一级部门群
	 * 作者：MaoSF
	 * 日期：2017/1/13
	 * @param:
	 * @return:
	 */
	String getOwnDepartGroupId(Map<String, Object> paraMap);

	/**
	 * 方法描述：获取部门全路径
	 * 作者：MaoSF
	 * 日期：2017/2/6
	 * @param:
	 * @return:
	 */
	String getDepartFullName(String id);

}
