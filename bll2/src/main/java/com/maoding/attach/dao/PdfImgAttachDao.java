package com.maoding.attach.dao;


import com.maoding.attach.entity.PdfImgAttachEntity;
import com.maoding.core.base.dao.BaseDao;

import java.util.List;


/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ProjectAttachDao
 * 描    述 : 项目附件（dao）
 * 作    者 : LY
 * 日    期 : 2016/7/22 16:50
 */
public interface PdfImgAttachDao extends BaseDao<PdfImgAttachEntity> {

    /**
     * 方法描述：查询附件(根据target_id)
     * 作   者：LY
     * 日   期：2016/8/12 11:18
    */
    List<PdfImgAttachEntity> selectByTargetId(String targetId);
}
