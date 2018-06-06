package cn.itcast.core.service;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;

public interface BaseService<T> {
	//新增
	public void save(T entity);
	//更新
	public void update(T entity);
	//根据id删除
	public void delete(Serializable id);
	//根据id查找
	public T findObjectById(Serializable id);
	//查找列表
	public List<T> findObjects();
	//条件查询实体列表
	public List<T> findObects(String hql, List<Object> parameter);
	//条件查询实体列表--查询助手queryHelper
	public List<T> findObjects(QueryHelper queryHelper);
	//分页查询
	PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);

}
