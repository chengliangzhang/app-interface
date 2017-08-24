package com.maoding.financial.service.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.financial.dao.ExpCategoryDao;
import com.maoding.financial.entity.ExpCategoryEntity;
import com.maoding.financial.service.ExpCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpCategoryServiceImpl
 * 描    述 : 报销类别ServiceImpl
 * 作    者 : MaoSF
 * 日    期 : 2016/10/09-15:52
 */

@Service("expCategoryService")
public class ExpCategoryServiceImpl extends GenericDao<ExpCategoryEntity> implements ExpCategoryService {

    @Autowired
    private ExpCategoryDao expCategoryDao;

    /**
     * 方法描述：根据相关参数查找
     * 作        者：MaoSF
     * 日        期：2016年10月09日-下午2:46:28
     *
     * @param map
     * @return
     */
    @Override
    public List<ExpCategoryEntity> getDataByParemeter(Map<String, Object> map) {
        return expCategoryDao.getDataByParemeter(map);
    }

    @Override
    public List<ExpCategoryEntity> getParentExpCategory(String companyId) {
        Map param=new HashMap();
        param.put("pid", "");
        param.put("companyId", companyId);
        List<ExpCategoryEntity> list=getDataByParemeter(param);
        if(list==null || list.size()==0){
            param.clear();
            param.put("pid", "");
            param.put("companyId", "");
            list=getDataByParemeter(param);
        }
        return list;
    }
}
