package com.maoding.dynamic.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.dynamic.dto.OrgDynamicDataDTO;
import com.maoding.dynamic.entity.OrgDynamicEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2017/2/23.
 */
public interface OrgDynamicDao extends BaseDao<OrgDynamicEntity> {


    /**
     * 根据参数查询动态
     * @param param
     * @return
     */
    List<OrgDynamicDataDTO> getOrgDynamicByParam(Map<String, Object> param);


    /**
     * 根据参数查询动态
     * @param param
     * @return
     */
    List<OrgDynamicEntity> getLastOrgDynamicByParam(Map<String, Object> param);


    /**
     * 根据参数查询动态数量
     * @param param
     * @return
     */
    int getOrgDynamicCountByParam(Map<String, Object> param);

    /**
     * 方法描述：更改fileld2的值，用于项目删除
     * 作者：MaoSF
     * 日期：2017/3/29
     * @param:
     * @return:
     */
    int updatefield2ByTargetId(String targetId);
}
