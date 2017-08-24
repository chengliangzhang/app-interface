package com.maoding.core.base.dao;


import com.maoding.core.base.entity.BaseEntity;

/**深圳市设计同道技术有限公司
 * 类    名：GenericDao
 * 类描述：通用Dao，所有的Dao都应该继承此Dao
 * 作    者：Chenxj
 * 日    期：2015年10月19日-下午5:28:48
 */
public class GenericDao<T extends BaseEntity> extends AbstractDao<T> implements BaseDao<T>{
	
	protected String entityName;
	public GenericDao() {
		super();
		this.entityName=entityClass.getSimpleName();
	}
	
	public int insert(T entity) {
		return sqlSession.insert(entityName+"Mapper.insert", entity);
	}

	public int updateById(T entity) {
		return sqlSession.update(entityName+"Mapper.updateById", entity);
	}

	/**
	 * 方法描述：MyBaties 修改方法
	 * 作        者：MaoSF
	 * 日        期：2015年12月25日-上午11:50:55
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public int update(T entity) {
		return sqlSession.update(entityName+"Mapper.update", entity);
	}

	public int deleteById(Object id) {
		return sqlSession.delete(entityName+"Mapper.deleteById", id);
	}

	public T selectById(Object id) {
		return sqlSession.selectOne(entityName+"Mapper.selectById", id);
	}
	
}
