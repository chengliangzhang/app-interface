package com.maoding.attention.dao.impl;

import com.maoding.attention.dao.AttentionDao;
import com.maoding.attention.entity.AttentionEntity;
import com.maoding.core.base.dao.GenericDao;
import org.springframework.stereotype.Service;


@Service("attentionDao")
public class AttentionDaoImpl extends GenericDao<AttentionEntity> implements AttentionDao {


    @Override
    public int cancelAttention(AttentionEntity dto) {
        return this.sqlSession.delete("AttentionEntityMapper.cancelAttention",dto);
    }
}
