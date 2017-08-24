package com.maoding.hxIm.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.hxIm.dao.ImGroupDao;
import com.maoding.hxIm.dto.ImGroupDataDTO;
import com.maoding.hxIm.dto.ImGroupQuery;
import com.maoding.hxIm.entity.ImGroupEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by sandy on 2017/8/7.
 */
@Service("imGroupDao")
public class ImGroupDaoImpl extends GenericDao<ImGroupEntity> implements ImGroupDao {

    @Override
    public List<ImGroupDataDTO> selectDepartGroupList(ImGroupQuery  query) {
        return sqlSession.selectList("GetImGroupMapper.selectDepartGroupList", query);
    }

    @Override
    public List<ImGroupDataDTO> selectProjectGroupByParameter(ImGroupQuery query) {
        return sqlSession.selectList("GetImGroupMapper.selectProjectGroupByParameter", query);
    }

    @Override
    public List<ImGroupDataDTO> selectCustomGroupByParameter(ImGroupQuery query) {
        return sqlSession.selectList("GetImGroupMapper.selectCustomGroupByParameter", query);
    }

    @Override
    public List<ImGroupDataDTO> selectCompanyGroupByParameter(ImGroupQuery query) {
        return sqlSession.selectList("GetImGroupMapper.selectCompanyGroupByParameter", query);
    }


    @Override
    public List<ImGroupDataDTO> listAllGroupByUserId(ImGroupQuery query) {
        return sqlSession.selectList("GetImGroupMapper.listAllGroupByUserId", query);
    }

    @Override
    public List<ImGroupDataDTO> listAllGroupByUserIdAndCompanyId(ImGroupQuery query) {
        return sqlSession.selectList("GetImGroupMapper.listAllGroupByUserIdAndCompanyId", query);
    }

    @Override
    public List<ImGroupEntity> getImGroupsByParam(Map<String, Object> map) {
        return sqlSession.selectList("ImGroupEntityMapper.selectByParam", map);
    }
}
