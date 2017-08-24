package com.maoding.user.dao.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.user.dao.UserAwardsDao;
import com.maoding.user.entity.UserAwardsEntity;


@Service("userAwardsDao")
public class UserAwardsDaoImpl extends GenericDao<UserAwardsEntity> implements UserAwardsDao{

	@Override
	public List<UserAwardsEntity> getUserAwardshonorsByUserId(String userId) {
		return this.sqlSession.selectList("UserAwardshonorsEntityMapper.selectByUserId", userId);
	}

	@Override
	public int delUserAwardshonorsByUserId(String userId) {
		return this.sqlSession.delete("UserAwardshonorsEntityMapper.deleteByUserId", userId);
	}
	

}
