package com.example.db;

import java.util.List;

import android.content.Context;

import com.MyAppcation;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.daoexample.dao.DaoSession;

public class BaseDBHelper<T,K> {
	private AbstractDao<T, K> dao;
	
	public BaseDBHelper(AbstractDao<T, K> dao){
		this.dao = dao;
	}

	public void setDao(AbstractDao<T, K> dao) {
		this.dao = dao;
	}
	
	public AbstractDao<T, K> getDao() {
		return dao;
	}
	
	/** 添加数据 */
	public void addItem(T item)
	{
		getDao().insert(item);
	}
	
	/** 查询 降序列表 */
	public List<T> getInfoList(Property property)
	{
		QueryBuilder<T> qb = getDao().queryBuilder();
		return qb.orderDesc(property).list();
	}
	
	/** 查询 */
	public List<T> getInfos()
	{
		return getDao().loadAll();
	}
	
	/** 通过id查询 */
	public T getInfosById(Property ID, int Id)
	{
		QueryBuilder<T> qb = getDao().queryBuilder();
		return qb.where(ID.eq(Id)).unique();
	}
	
	/** 通过id删除 */
	public void deleteInfo(Property ID, int Id)
	{
		QueryBuilder<T> qb = getDao().queryBuilder();
		DeleteQuery<T> bd = qb.where(ID.eq(Id)).buildDelete();
		bd.executeDeleteWithoutDetachingEntities();
	}
	
	/** 通过某一属性查询条目是否存在*/
	public boolean isExist(WhereCondition whereCondition){
		QueryBuilder<T> qb = getDao().queryBuilder();
		return qb.where(whereCondition).unique() != null;
	}
	
	/** 删除 */
	public void clearList()
	{
		getDao().deleteAll();
	}
}
