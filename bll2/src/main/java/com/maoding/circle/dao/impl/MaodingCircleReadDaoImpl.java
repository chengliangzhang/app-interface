package com.maoding.circle.dao.impl;

import com.maoding.circle.dao.MaodingCircleDao;
import com.maoding.circle.dao.MaodingCircleReadDao;
import com.maoding.circle.dto.MaodingCircleDataDTO;
import com.maoding.circle.dto.MaodingCircleReadDTO;
import com.maoding.circle.dto.QueryCircleDTO;
import com.maoding.circle.entity.MaodingCircleEntity;
import com.maoding.circle.entity.MaodingCircleReadEntity;
import com.maoding.core.base.dao.GenericDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
@Service("maodingCircleReadDao")
public class MaodingCircleReadDaoImpl extends GenericDao<MaodingCircleReadEntity> implements MaodingCircleReadDao {

    @Override
    public List<MaodingCircleReadDTO> getNotReadCircle(QueryCircleDTO query) {
        return this.sqlSession.selectList("MaodingCircleReadEntityMapper.getNotReadCircle",query);
    }

    @Override
    public int updateCircleReadStatus(QueryCircleDTO query) {
        return this.sqlSession.update("MaodingCircleReadEntityMapper.updateCircleReadStatus",query);
    }
}
