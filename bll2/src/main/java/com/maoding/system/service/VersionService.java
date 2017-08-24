package com.maoding.system.service;


import com.maoding.core.base.service.BaseService;
import com.maoding.system.entity.VersionEntity;

/**深圳市设计同道技术有限公司
 * 类    名：VersionService
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2015年12月30日-上午11:32:25
 */
public interface VersionService extends BaseService<VersionEntity> {

	/**方法描述：
	 * 作        者：Chenxj
	 * 日        期：2015年12月30日-上午11:35:09
	 * @param platform
	 * @return
	 */
	VersionEntity lastVersion(String platform);
	
}
