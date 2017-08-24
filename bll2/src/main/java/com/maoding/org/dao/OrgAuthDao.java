package com.maoding.org.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.org.dto.OrgAuthenticationDataDTO;
import com.maoding.org.entity.OrgAuthEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：OrgAuthenticationDao
 * 类描述：组织审核dao
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:42:48
 */
public interface OrgAuthDao extends BaseDao<OrgAuthEntity> {

    OrgAuthenticationDataDTO getOrgAuthenticationInfo(String id);
}
