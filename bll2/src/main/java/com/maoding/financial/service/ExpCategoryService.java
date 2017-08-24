package com.maoding.financial.service;


import com.maoding.core.base.service.BaseService;
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
public interface ExpCategoryService extends BaseService<ExpCategoryEntity> {
    /**
     * 方法描述：根据相关参数查找
     * 作        者：MaoSF
     * 日        期：2016年10月09日-下午2:46:28
     * @param map
     * @return
     */
    public List<ExpCategoryEntity> getDataByParemeter(Map<String, Object> map);

    public List<ExpCategoryEntity> getParentExpCategory(String companyId);
}
