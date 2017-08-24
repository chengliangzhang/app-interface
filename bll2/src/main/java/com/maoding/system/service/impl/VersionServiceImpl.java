package com.maoding.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.maoding.core.base.service.GenericService;
import com.maoding.system.dao.VersionDao;
import com.maoding.system.entity.VersionEntity;
import com.maoding.system.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



/**深圳市设计同道技术有限公司
 * 类    名：VersionServiceImpl
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2015年12月30日-上午11:32:52
 */
@Service("versionService")
public class VersionServiceImpl extends GenericService<VersionEntity> implements VersionService {
	@Autowired
	private VersionDao versionDao;
	@Value("${fastdfs.url}")
	protected String fastdfsUrl;
	@Override
	public VersionEntity lastVersion(String platform) {
	    Map<String,Object> paraMap=new HashMap<String,Object>();
		paraMap.put("platform", platform);
		paraMap.put("fastdfsUrl",fastdfsUrl);
		return versionDao.selectByPlatform(paraMap);
	}
	
}
