package com.example.db;

import java.util.List;

import android.content.Context;

import com.MyAppcation;

import de.greenrobot.dao.Match;
import de.greenrobot.daoexample.dao.DaoSession;
import de.greenrobot.daoexample.dao.MatchDao;
import de.greenrobot.daoexample.dao.MatchDao.Properties;

public class MatchDBHelper
{
	private static Context mContext;
	private static MatchDBHelper instance;


	private BaseDBHelper<Match, Long> dbHelper;
	private MatchDao matchDao;

	private MatchDBHelper()
	{
	}

	public static MatchDBHelper getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new MatchDBHelper();
			
			if (mContext == null)
			{
				mContext = context;
			}
			// 数据库对象
			DaoSession daoSession = MyAppcation.getDaoSession(mContext, DBConstant.MATCH_DB_NAME);
//			dbHelper = daoSession.getMatchDao();

			instance.matchDao = daoSession.getMatchDao();
			instance.dbHelper = new BaseDBHelper(instance.matchDao);
		}
		return instance;
	}

	/** 添加数据 */
	public void addToMatchInfoTable(Match item)
	{
		instance.dbHelper.addItem(item);
	}

	/** 查询 降序列表 */
	public List<Match> getMatchInfoList()
	{
		return instance.dbHelper.getInfoList(Properties.Id);
	}

	/** 查询 */
	public List<Match> getMatchInfo()
	{
		return instance.dbHelper.getInfos();
	}

	/** 查询 */
	public Match getMatchInfoByid(int Id)
	{
		return instance.dbHelper.getInfosById(Properties.Id, Id);
	}

	/** 删除 */
	public void deleteMatchInfoList(int Id)
	{
		instance.dbHelper.deleteInfo(Properties.Id, Id);
	}

	/** 删除 */
	public void clearMatchInfo()
	{
		instance.dbHelper.clearList();
	}

	/** 多重查询 */
//	public List<Match> getIphRegionList(int cityId)
//	{
//		QueryBuilder<Match> qb = matchDao.queryBuilder();
//		qb.where(qb.and(Properties.CityId.eq(cityId), Properties.InfoType.eq(HBContant.MatchInfo_IR)));
//		qb.orderAsc(Properties.Id);// 排序依据
//		return qb.list();
//	}
}