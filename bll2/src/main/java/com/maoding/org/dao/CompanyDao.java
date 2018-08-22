package com.maoding.org.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.CompanyDataDTO;
import com.maoding.org.dto.CompanyQueryDTO;
import com.maoding.org.dto.CompanyRelationDTO;
import com.maoding.org.entity.CompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyDao
 * 类描述：组织(公司）Dao
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:42:48
 */
public interface CompanyDao extends BaseDao<CompanyEntity>{

	
	/**
	 * 方法描述：获取所有企业（组织切换列表）
	 * 作        者：MaoSF
	 * 日        期：2016年7月8日-下午2:03:16
	 * @param userId
	 * @return(id,companyName,companyType,companyShortName,status,filePath)
	 */
    List<CompanyDTO> getCompanyByUserId(String userId);
	
	/**
	 * 方法描述：查找组织架构树所有节点边
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午7:20:10
	 * @param map{companyId}
	 */
    List<Map<String, Object>>selectAllCompanyEdges(Map<String, Object> map);
	
	/***************过滤以挂靠的组织**************/
	/**
	 * 方法描述：查询（未挂靠组织的组织），大B搜索小b，传pid=当前公司，小b搜索大B传companyId=当前公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 */
    List<CompanyDTO> getCompanyFilterbyParam(Map<String, Object> map);

	/**
	 * 方法描述：查询从未挂靠的组织（包含被邀请没有同意的）
	 * 作者：MaoSF
	 * 日期：2017/1/17
	 */
    List<CompanyDTO> getCompanyFilterbyParamForInvit(Map<String, Object> map);
	/**
	 * 方法描述：查询从未挂靠的组织（包含被邀请没有同意的）总条数
	 * 作者：MaoSF
	 * 日期：2017/1/17
	 */
    int getCompanyFilterbyParamForInvitCount(Map<String, Object> map);


	
	/**
	 * 方法描述：查询（未挂靠组织的组织）条数，大B搜索小b，传pid=当前公司，小b搜索大B传companyId=当前公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月11日-下午10:49:56
	 */
    int getCompanyFilterbyParamCount(Map<String, Object> map);

	/*******************生成树的数据*******************/
	/**
	 * 方法描述：查询公司的父公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:01:41
	 */
    CompanyEntity getParentCompany(String id);

	/**
	 * 方法描述：查询公司的子公司
	 * 作        者：MaoSF
	 * 日        期：2016年7月15日-下午2:01:41
	 */
    List<CompanyEntity> getChilrenCompany(String id);

    /**
     * 方法描述：查询了公司信息及logo
     * 作者：MaoSF
     * 日期：2017/3/3
     */
    CompanyEntity getCompanyMsgById(String id);


	/**
	 * 方法描述：获取所有公司（全部公司注册环信群，手工操作）
	 * 作者：MaoSF
	 * 日期：2016/8/23
	 */
    List<CompanyEntity> selectAll();

    /**
     * 方法描述：获取具有管理员权限的公司
     * 作者：MaoSF
     * 日期：2017/2/28
     * @param:userId,fastDfs
     */
    List<CompanyDataDTO> getHasSystemManagerCompany(Map<String,Object> map);

	/**
	 * 方法描述：获取我的项目查询条件
	 * 作者：MaoSF
	 * 日期：2017/5/8
	 */
	List<CompanyEntity> getOuterCooperatorCompany(String companyId,String projectId);

	/**
	 * 方法描述：获取项目合作的组织
	 * 作者：MaoSF
	 * 日期：2017/6/8
	 */
	List<CompanyEntity> getCompanyByProjectId(String projectId);

	List<String> listCompanyIdByProjectId(String projectId);

	String getCompanyName(String id);

	/**
	 * 描述     查询相关乙方
	 * 日期     2018/8/8
	 * @author  张成亮
	 * @return  符合条件的公司信息列表
	 * @param   query 查询条件
	 **/
	List<CompanyDTO> listCompanyCooperate(CompanyQueryDTO query);

	CompanyRelationDTO getOrgType(String orgId);
}
