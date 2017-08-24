package com.maoding.attention.dao;


import com.maoding.attention.entity.AttentionEntity;
import com.maoding.core.base.dao.BaseDao;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：AttentionDaoImpl
 * 类描述：关注Dao
 * 作    者：MaoSF
 * 日    期：2017年01月06日-下午16:38:05
 */
public interface AttentionDao extends BaseDao<AttentionEntity> {


    int cancelAttention(AttentionEntity dto);
}
