package com.maoding.financial.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.financial.dao.ExpAttachDao;
import com.maoding.financial.entity.ExpAttachEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpAttachDao
 * 描    述 : 报销单据附件
 * 作    者 : MaoSF
 * 日    期 : 2016/12/27 15:11
 */
@Service("expAttachDao")
public class ExpAttachDaoImpl extends GenericDao<ExpAttachEntity> implements ExpAttachDao {

    /**
     * 方法描述：根据报销主表id查询报销单据附件
     * 作   者：MaoSF
     * 日   期：2016/8/2 15:35
     *
     * @param mainId 报销主表id
     */
    @Override
    public List<ExpAttachEntity> getExpAttachByMainId(String mainId) {
        return this.sqlSession.selectList("ExpAttachEntityMapper.getExpAttachByMainId", mainId);
    }

    /**
     * 方法描述：删除报销附件
     * 作   者：chenzhujie
     * 日   期：2016/12/27
     */
    @Override
    public int deleteByParam(Map<String, Object> map) {
        return this.sqlSession.delete("ExpAttachEntityMapper.deleteByParam", map);
    }
}
