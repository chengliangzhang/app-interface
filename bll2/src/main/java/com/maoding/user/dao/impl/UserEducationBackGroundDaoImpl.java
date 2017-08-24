package com.maoding.user.dao.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.user.dao.UserDao;
import com.maoding.user.dao.UserEducationBackGroundDao;
import com.maoding.user.entity.UserEducationBackGroundEntity;
import com.maoding.user.entity.UserEntity;


@Service("userEducationBackGroundDao")
public class UserEducationBackGroundDaoImpl extends GenericDao<UserEducationBackGroundEntity> implements UserEducationBackGroundDao{

	@Override
	public List<UserEducationBackGroundEntity> getUserEducationbgByUserId(String userId) {
		return this.sqlSession.selectList("UserEducationbgEntityMapper.selectByUserId", userId);
	}

	@Override
	public int delUserEducationbgByUserId(String userId) {
		return this.sqlSession.delete("UserEducationbgEntityMapper.deleteByUserId", userId);
	}
}
