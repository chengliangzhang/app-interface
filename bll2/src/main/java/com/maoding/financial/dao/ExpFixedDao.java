package com.maoding.financial.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.financial.entity.ExpFixedEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpFixedDao
 * 描    述 : 固定支出Dao
 * 作    者 : LY
 * 日    期 : 2016/8/4 11:01
 */
public interface ExpFixedDao extends BaseDao<ExpFixedEntity>{

    /**
     * 方法描述：查询选择月的固定支出
     * 作   者：LY
     * 日   期：2016/8/4 15:25
     * @param  expDate 月份
     */
    public List<ExpFixedEntity> getExpFixedByExpDate(String companyId, String expDate);

    /**
     * 方法描述：月账列表数量
     * 作   者：LY
     * 日   期：2016/8/2 17:42
     */
    public int getExpDateCount(Map<String, Object> paraMapk);
}