package cn.itcast.core.service.impl;

import java.io.Serializable;
import java.util.List;


import org.apache.poi.ss.formula.functions.T;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.service.BaseService;
import cn.itcast.core.util.QueryHelper;

@SuppressWarnings("hiding")
public class BaseServiceImpl<T> implements BaseService<T> {

	
	private BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		baseDao.delete(id);
	}

	@Override
	public List<T> findObects(String hql, List<Object> parameter) {
		// TODO Auto-generated method stub
		return baseDao.findObjects(hql, parameter);
	}

	@Override
	public T findObjectById(Serializable id) {
		// TODO Auto-generated method stub
		return baseDao.findObjectById(id);
	}

	@Override
	public List<T> findObjects() {
		// TODO Auto-generated method stub
		return baseDao.findObjects();
	}

	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		baseDao.save(entity);
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		baseDao.update(entity);
	}

	@Override
	public List<T> findObjects(QueryHelper queryHelper) {
		// TODO Auto-generated method stub
		return baseDao.findObjects(queryHelper);
	}

	@Override
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return baseDao.getPageResult(queryHelper, pageNo, pageSize);
	}

}
