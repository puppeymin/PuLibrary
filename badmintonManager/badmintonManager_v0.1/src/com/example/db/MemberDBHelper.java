package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.MyAppcation;

import de.greenrobot.dao.Member;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.daoexample.dao.DaoSession;
import de.greenrobot.daoexample.dao.MemberDao;
import de.greenrobot.daoexample.dao.MemberDao.Properties;

public class MemberDBHelper
{
	private static Context mContext;
	private static MemberDBHelper instance;


	private BaseDBHelper<Member, Long> dbHelper;
	private MemberDao memberDao;

	private MemberDBHelper()
	{
	}

	public static MemberDBHelper getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new MemberDBHelper();
			
			if (mContext == null)
			{
				mContext = context;
			}
			// 数据库对象
			DaoSession daoSession = MyAppcation.getDaoSession(mContext, DBConstant.MEMBER_DB_NAME);
//			dbHelper = daoSession.getMemberDao();

			instance.memberDao = daoSession.getMemberDao();
			instance.dbHelper = new BaseDBHelper(instance.memberDao);
		}
		return instance;
	}

	/** 添加数据 */
	public void addToMemberInfoTable(Member item)
	{
		instance.dbHelper.addItem(item);
	}

	/** 查询 降序列表 */
	public List<Member> getMemberInfoList()
	{
		return instance.dbHelper.getInfoList(Properties.Id);
	}

	/** 查询 */
	public List<Member> getMemberInfo()
	{
		return instance.dbHelper.getInfos();
	}

	/** 通过id查询*/
	public Member getMemberInfoById(int Id)
	{
		return instance.dbHelper.getInfosById(Properties.Id, Id);
	}
	
	/** 通过name查询*/
	public Member getMemberInfoByName(String name)
	{	
		QueryBuilder<Member> qb = instance.memberDao.queryBuilder();
		return qb.where(Properties.Name.eq(name)).unique();
	}
	
	/** 查询 根据names字符串 查出menber数组 字符串用逗号隔开*/
	public List<Member> getMemberInfosByName(String ids)
	{
		List<Member> list = new ArrayList<Member>();
		String[] is = ids.split(",");
		for (int i = 0; i < is.length; i++) {
			list.add(getMemberInfoByName(is[i]));
		}
		return list;
	}

	/** 删除 */
	public void deleteMemberInfoList(int Id)
	{
		instance.dbHelper.deleteInfo(Properties.Id, Id);
	}

	/** 删除 */
	public void clearMemberInfo()
	{
		instance.dbHelper.clearList();
	}

	/** 通过id查询成员是否存在*/
	public boolean isExist(int Id)
	{
		return instance.dbHelper.isExist(Properties.Id.eq(Id));
	}
	
	/** 通过name查询成员是否存在*/
	public boolean isExist(String name)
	{
		return instance.dbHelper.isExist(Properties.Name.eq(name));
	}
}
