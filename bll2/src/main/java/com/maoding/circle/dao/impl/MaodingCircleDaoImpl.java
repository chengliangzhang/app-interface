package com.maoding.circle.dao.impl;

import com.maoding.circle.dao.MaodingCircleDao;
import com.maoding.circle.dto.MaodingCircleDataDTO;
import com.maoding.circle.dto.QueryCircleDTO;
import com.maoding.circle.entity.MaodingCircleEntity;
import com.maoding.core.base.dao.GenericDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
@Service("maodingCircleDao")
public class MaodingCircleDaoImpl extends GenericDao<MaodingCircleEntity> implements MaodingCircleDao {

    @Override
    public List<MaodingCircleDataDTO> listMaodingCircle(QueryCircleDTO query) {
        return this.sqlSession.selectList("GetMaodingCircleMapper.listMaodingCircle",query);
    }

    @Override
    public MaodingCircleDataDTO getMaodingCircleById(QueryCircleDTO query) {
        return this.sqlSession.selectOne("GetMaodingCircleMapper.getMaodingCircleById",query);
    }

    @Override
    public String getPartInCompany(QueryCircleDTO query) {
        return this.sqlSession.selectOne("GetMaodingCircleMapper.getPartInCompany",query);
    }
}
