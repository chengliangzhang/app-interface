package com.maoding.user.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.user.dao.UserDao;
import com.maoding.user.dto.UserInfoDTO;
import com.maoding.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("userDao")
public class UserDaoImpl extends GenericDao<UserEntity> implements UserDao{
    /**
     * 方法描述：获取个人信息
     * 作者：MaoSF
     * 日期：2017/5/23
     */
    @Override
    public UserInfoDTO getUserInfo(String id, String fastdfsUrl) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("fastdfsUrl",fastdfsUrl);
        return this.sqlSession.selectOne("UserEntityMapper.getUserInfo",map);
    }

//	@Override
//	public List<UserEntity> findUserByPage(Map<String, Object> map) {
//		return this.sqlSession.selectList("UserEntityMapper.selectByPage", map);
//	}
//
//	@Override
//	public int findUserByPageCount(Map<String, Object> map) {
//		return sqlSession.selectOne("UserEntityMapper.selectByPageCount", map);
//	}
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	public UserEntity getById(Map map) {
//		return sqlSession.selectOne("UserEntityMapper.selectUserByCompanyIdAndUserId", map);
//	}
//
//	@Override
//	public UserEntity getByLoginUserId(String userId) {
//		return this.sqlSession.selectOne("UserEntityMapper.selectByLoginUserId", userId);
//	}
//	
//
//	@Override
//	public List<UserEntity> selectUserByRoleCode(Map<String, Object> paraMap) {
//		return  this.sqlSession.selectList("UserEntityMapper.selectUserByRoleCode", paraMap);
//	}
//
//	@Override
//	public Map<String, Object> getUserInfoByUserId(Map<String, Object> paraMap) {
//		return sqlSession.selectOne("UserEntityMapper.getUserInfoByUserId", paraMap);
//	}
//
//	@Override
//	public List<Map<String, Object>> getVerifyUser(Map<String, Object> paraMap) {
//		return sqlSession.selectList("UserEntityMapper.getVerifyUser", paraMap);
//	}

}
