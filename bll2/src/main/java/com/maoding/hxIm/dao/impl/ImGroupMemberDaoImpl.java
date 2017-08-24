package com.maoding.hxIm.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.hxIm.dao.ImGroupDao;
import com.maoding.hxIm.dao.ImGroupMemberDao;
import com.maoding.hxIm.dto.ImGroupDataDTO;
import com.maoding.hxIm.dto.ImGroupMemberDataDTO;
import com.maoding.hxIm.dto.ImGroupMemberQuery;
import com.maoding.hxIm.dto.ImGroupQuery;
import com.maoding.hxIm.entity.ImGroupEntity;
import com.maoding.hxIm.entity.ImGroupMemberEntity;
import com.maoding.org.dto.CompanyUserGroupDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by sandy on 2017/8/7.
 */
@Service("imGroupMemberDao")
public class ImGroupMemberDaoImpl extends GenericDao<ImGroupMemberEntity> implements ImGroupMemberDao {

    @Override
    public List<ImGroupMemberDataDTO> selectNewGroupMembers(ImGroupMemberQuery query) {
        return sqlSession.selectList("GetImGroupMemberMapper.selectNewGroupMembers", query);
    }

    @Override
    public List<ImGroupMemberDataDTO> selectCustomGroupMembers(ImGroupMemberQuery query) {
        return sqlSession.selectList("GetImGroupMemberMapper.selectCustomGroupMembers", query);
    }

    @Override
    public List<ImGroupMemberDataDTO> selectNewDepartGroupMembers(ImGroupMemberQuery query) {
        return sqlSession.selectList("GetImGroupMemberMapper.selectNewDepartGroupMembers", query);
    }

    @Override
    public List<CompanyUserGroupDTO> listCustomerImGroupMember(ImGroupMemberQuery query) {
        return sqlSession.selectList("GetImGroupUserMapper.listCustomerImGroupMember", query);
    }
}
