package com.maoding.notice.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.notice.dto.NoticeDTO;
import com.maoding.notice.dto.NoticeDataDTO;
import com.maoding.notice.entity.NoticeEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/10/18.
 */
public interface NoticeDao extends BaseDao<NoticeEntity> {

     /**
      * 方法描述：根据参数查询公告（companyId,userId) 可以分页查询
      * 作者：MaoSF
      * 日期：2016/12/1
      * @param:
      * @return:
      */
     List<NoticeDTO> getNoticeByParam(Map<String, Object> param);


     /**
      * 方法描述：根据参数查询公告条目数（companyId,userId) 分页查询使用
      * 作者：MaoSF
      * 日期：2016/12/1
      * @param:
      * @return:
      */
     int getNoticeCountByParam(Map<String, Object> param);

     /**
      * 方法描述：根据companyId获取公告
      * 作者：MaoSF
      * 日期：2016/12/1
      * @param:
      * @return:
      */
     List<NoticeDTO> getNoticeByCompanyId(Map<String, Object> param) throws Exception;

     /**
      * 方法描述：根据参数查询公告条目数（companyId,userId) 分页查询使用
      * 作者：MaoSF
      * 日期：2016/12/1
      * @param:
      * @return:
      */
     int getNoticeCountByCompanyId(Map<String, Object> param);

     /**
      * 方法描述：获取公告，并且按照时间分组
      * 作者：MaoSF
      * 日期：2017/6/8
      */
     List<NoticeDataDTO> getNoticeGroupTime(Map<String, Object> param) throws Exception;
}
