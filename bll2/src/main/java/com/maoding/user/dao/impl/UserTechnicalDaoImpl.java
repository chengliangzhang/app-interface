package com.maoding.user.dao.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.user.dao.UserDao;
import com.maoding.user.dao.UserTechnicalDao;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.entity.UserTechnicalEntity;


@Service("userTechnicalDao")
public class UserTechnicalDaoImpl extends GenericDao<UserTechnicalEntity> implements UserTechnicalDao{
	@Override
	public List<UserTechnicalEntity> getUserTechnicalByUserId(String userId) {
		return this.sqlSession.selectList("UserTechnicalEntityMapper.selectByUserId", userId);
	}

	@Override
	public int delUserTechnicalByUserId(String userId) {
		return this.sqlSession.delete("UserTechnicalEntityMapper.deleteByUserId", userId);
	}
	
}
