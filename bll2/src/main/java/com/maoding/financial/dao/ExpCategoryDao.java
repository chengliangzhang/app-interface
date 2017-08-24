package com.maoding.financial.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.financial.entity.ExpCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DataDictionaryDao
 * 类描述：报销类别DAO
 * 作    者：MaoSF
 * 日    期：2016年10月09日-下午2:24:31
 */
public interface ExpCategoryDao extends BaseDao<ExpCategoryEntity>{
	

	/**
	 * 方法描述：根据相关参数查找
	 * 作        者：MaoSF
	 * 日        期：2016年10月09日-下午2:46:28
	 * @param map
	 * @return
	 */
	public List<ExpCategoryEntity> getDataByParemeter(Map<String, Object> map);

	/**
	 * 方法描述：根据父id和那么查询
	 * 作者：MaoSF
	 * 日期：2016/10/13
	 * @param:
	 * @return:
	 */
	public ExpCategoryEntity selectByName(String pid,String name);


	/**
	 * 方法描述：获取最大的seq值
	 * 作者：MaoSF
	 * 日期：2016/10/9
	 * @param:
	 * @return:
	 */
	public int getmaxExpCategorySeq();


	public int deleteByPId(Map<String, Object> map);

    public void initInsertCategory(String companyId);
}
