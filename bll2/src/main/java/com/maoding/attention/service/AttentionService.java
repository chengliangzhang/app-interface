package com.maoding.attention.service;


import com.maoding.attention.dto.AttentionDTO;
import com.maoding.attention.entity.AttentionEntity;
import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：AttentionDaoImpl
 * 类描述：关注Dao
 * 作    者：wrb
 * 日    期：2017年01月06日-下午16:38:05
 */
public interface AttentionService extends BaseService<AttentionEntity> {

    /**
     * 添加项目关注记录
     * @param dto
     * @return
     */
    ResponseBean addAttention(AttentionDTO dto) throws Exception;

    /**
     * 取消项目关注记录
     * @param dto)
     * @return
     */
    ResponseBean cancelAttention(AttentionDTO dto) throws Exception;

}
