package com.maoding.attach.dao.impl;

import com.maoding.attach.dao.PdfImgAttachDao;
import com.maoding.attach.entity.PdfImgAttachEntity;
import com.maoding.core.base.dao.GenericDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectAttachDaoImpl
 * 类描述：项目附件DaoImpl
 * 作    者：LY
 * 日    期：2016/7/22 16:50
 */
@Service("pdfImgAttachDao")
public class PdfImgAttachDaoImpl extends GenericDao<PdfImgAttachEntity> implements PdfImgAttachDao {


    /**
     * 方法描述：查询附件(根据target_id)
     * 作   者：LY
     * 日   期：2016/8/12 11:18
     */
    @Override
    public List<PdfImgAttachEntity> selectByTargetId(String targetId){
        return this.sqlSession.selectList("PdfImgAttachEntityMapper.selectByTargetId", targetId);
    }
}
