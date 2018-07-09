package com.maoding.financial.service;


import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.financial.dto.ExpTypeDTO;
import com.maoding.financial.dto.ExpTypeOutDTO;
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

    /**
     * 方法描述：报销类别基础数据
     * 作   者：LY
     * 日   期：2016/7/27 17:59
     * @return
     *
     */
    AjaxMessage getCategoryBaseData(String companyId, String userId) throws Exception ;

    /**
     * 方法描述：查询报销类型
     * 用于我要报销界面，报销类型选择
     * 作        者：MaoSF
     * 日        期：2015年12月7日-上午11:21:49
     * @return
     */
    List<ExpTypeDTO> getExpTypeList(String rootCompanyId,String companyId) throws Exception;

    /**
     * 方法描述：查询报销类型
     * 用于我要报销界面，报销类型选择
     * 作        者：MaoSF
     * 日        期：2015年12月7日-上午11:21:49
     * @return
     */
    List<ExpTypeDTO> getExpCategoryTypeList(String companyId,String userId)  throws Exception;

    /**
     * 方法描述:saveOrUpdateCategoryBaseData
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     * @return
     *
     */
    AjaxMessage saveOrUpdateCategoryBaseData(ExpTypeOutDTO dto, String companyId)throws Exception;
}
