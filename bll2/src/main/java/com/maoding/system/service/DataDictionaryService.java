package com.maoding.system.service;

import java.util.List;
import java.util.Map;

import com.maoding.core.base.service.BaseService;
import com.maoding.system.dto.DataDictionaryDTO;
import com.maoding.system.entity.DataDictionaryEntity;

/**深圳市设计同道技术有限公司
 * 类    名：DataDictionaryService
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2015年12月31日-上午11:10:17
 */
public interface DataDictionaryService extends BaseService<DataDictionaryEntity>{
	/**
	 * 方法描述：根据code查询自己及子集
	 * 作        者：MaoSF
	 * 日        期：2016年3月24日-下午3:04:58
	 * @param map
	 * @return
	 */
	public List<DataDictionaryEntity> selectParentAndSubByCode(Map<String,Object> map);

	/**
	 * 方法描述：根据code查出所有子集
	 * 作        者：wangrb
	 * 日        期：2015年11月26日-下午2:44:44
	 * @param code
	 * @return
	 */
	public List<DataDictionaryEntity> getSubDataByCode(String code);
	
	/**
	 * 方法描述：根据code查出所有子集
	 * 作        者：wangrb
	 * 日        期：2015年11月26日-下午2:44:44
	 * @param code
	 * @return
	 */
	public List<DataDictionaryDTO> getSubDataByCodeToDTO(String code) throws Exception;

	/**
	 * 方法描述：根据code查出所有的值
	 * 作        者：wangrb
	 * 日        期：2015年11月26日-下午2:44:44
	 * @param code
	 * @return
	 */
	public List<DataDictionaryEntity> getTypeName(String code) throws Exception;
	
	/**
	 * 方法描述：根据相关参数查找
	 * 作        者：wangrb
	 * 日        期：2015年11月26日-下午2:46:28
	 * @param map
	 * @return
	 */
	public List<DataDictionaryEntity> getDataByParemeter(Map<String,Object> map);

	/**方法描述：
	 * 作        者：Chenxj
	 * 日        期：2016年3月31日-上午11:40:27
	 * @param code
	 * @return
	 */
	public Map<String, String> ddMap(String code);
	
	/**
	 * 方法描述：根据父级code和父级键值查询相关数据字典， 子级的键值跟父级一样情况下 
	 * 作    者：wangrb
	 * 日    期：2016年6月22日-下午1:03:27
	 * @param map{code,vl}
	 * @return
	 */
	public List<DataDictionaryEntity> getByParentCodeAndVl(Map<String,Object> map);

	public List<DataDictionaryEntity> getDataDictionarysubEntityById(String expType);
}
