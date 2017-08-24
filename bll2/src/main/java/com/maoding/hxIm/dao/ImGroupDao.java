package com.maoding.hxIm.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.hxIm.dto.ImGroupDataDTO;
import com.maoding.hxIm.dto.ImGroupQuery;
import com.maoding.hxIm.entity.ImGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by sandy on 2017/8/7.
 */
public interface ImGroupDao extends BaseDao<ImGroupEntity> {

    /**方法描述：selectDepartGroupList
     * 作        者：Chenxj
     * 日        期：2016年5月31日-下午2:21:36
     */
    List<ImGroupDataDTO> selectDepartGroupList(ImGroupQuery query);

    /**
     * 方法描述：查询有关的项目群
     * 作        者：Chenxj
     * 日        期：2016年5月31日-下午2:17:17
     */
    List<ImGroupDataDTO> selectProjectGroupByParameter(ImGroupQuery query);
    /**
     * 方法描述：查询有关的项目群
     * 作        者：Chenxj
     * 日        期：2016年5月31日-下午2:17:17
     */
    List<ImGroupDataDTO> selectCustomGroupByParameter(ImGroupQuery query);

    /**
     * 方法描述：查询有关的公司群组
     * 作        者：Chenxj
     * 日        期：2016年5月31日-下午2:17:17
     */
    List<ImGroupDataDTO> selectCompanyGroupByParameter(ImGroupQuery query);

    /**
     * 方法描述：查询userId所在所有群组及群组成员
     * 作        者：MaoSF
     * 日        期：2017年8月8日-下午2:17:17
     */
    List<ImGroupDataDTO> listAllGroupByUserId(ImGroupQuery query);

    /**
     * 方法描述：查询userId所在当前公司下的所有群组
     * 作        者：MaoSF
     * 日        期：2017年8月8日-下午2:17:17
     */
    List<ImGroupDataDTO> listAllGroupByUserIdAndCompanyId(ImGroupQuery query);

    /**
     * 根据参数查找群组（暂时）
     */
    List<ImGroupEntity> getImGroupsByParam(Map<String,Object> map);
}
