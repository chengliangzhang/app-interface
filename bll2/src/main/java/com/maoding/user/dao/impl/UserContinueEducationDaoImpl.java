package com.maoding.user.dao.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.user.dao.UserContinueEducationDao;
import com.maoding.user.entity.UserContinueEducationEntity;


@Service("userContinueEducationDao")
public class UserContinueEducationDaoImpl extends GenericDao<UserContinueEducationEntity> implements UserContinueEducationDao{

	@Override
	public List<UserContinueEducationEntity> getUserContinueeducationByUserId(String userId) {
		return this.sqlSession.selectList("UserContinueeducationEntityMapper.selectByUserId", userId);
	}

	@Override
	public int delUserContinueeducationByUserId(String userId) {
		return this.sqlSession.delete("UserContinueeducationEntityMapper.deleteByUserId", userId);
	}
}
