package com.maoding.user.dao.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.user.dao.UserDao;
import com.maoding.user.dao.UserWorkPerformanceDao;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.entity.UserWorkPerformanceEntity;


@Service("userWorkPerformanceDao")
public class UserWorkPerformanceDaoImpl extends GenericDao<UserWorkPerformanceEntity> implements UserWorkPerformanceDao{
	@Override
	public List<UserWorkPerformanceEntity> getUserWorkperformanceByUserId(String userId) {
		return this.sqlSession.selectList("UserWorkperformanceEntityMapper.selectByUserId", userId);
	}

	@Override
	public int delUserWorkperformanceByUserId(String userId) {
		return this.sqlSession.delete("UserWorkperformanceEntityMapper.deleteByUserId", userId);
	}
	
}
