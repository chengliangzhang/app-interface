package com.maoding.user.dao.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.user.dao.UserDao;
import com.maoding.user.dao.UserWorkExperienceDao;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.entity.UserWorkExperienceEntity;


@Service("userWorkExperienceDao")
public class UserWorkExperienceDaoImpl extends GenericDao<UserWorkExperienceEntity> implements UserWorkExperienceDao{

	@Override
	public List<UserWorkExperienceEntity> getUserWorkexperienceByUserId(String userId) {
		return this.sqlSession.selectList("UserWorkexperienceEntityMapper.selectByPersonId", userId);
	}

	@Override
	public int delUserWorkexperienceByUserId(String userId) {
		return this.sqlSession.delete("UserWorkexperienceEntityMapper.deleteByUserId", userId);
	}
}
