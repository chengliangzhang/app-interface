package com.maoding.user.dao.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.user.dao.UserDao;
import com.maoding.user.dao.UserQualificationsDao;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.entity.UserQualificationsEntity;


@Service("userQualificationsDao")
public class UserQualificationsDaoImpl extends GenericDao<UserQualificationsEntity> implements UserQualificationsDao{

	@Override
	public List<UserQualificationsEntity> getUserQualificationsByUserId(String userId) {
		return this.sqlSession.selectList("UserQualificationsEntityMapper.selectByUserId", userId);
	}

	@Override
	public int delUserQualificationsByUserId(String userId) {
		return this.sqlSession.delete("UserQualificationsEntityMapper.deleteByUserId", userId);
	}

}
