package com;

import android.content.Context;

import com.example.badmintonmanager.manager.MainManager;
import com.framework.base.BaseAppcation;

import de.greenrobot.daoexample.dao.DaoMaster;
import de.greenrobot.daoexample.dao.DaoMaster.OpenHelper;
import de.greenrobot.daoexample.dao.DaoSession;

public class MyAppcation extends BaseAppcation {

	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		MainManager.getInstance().init();
	}

	/**
	 * 取得DaoMaster
	 *
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster(Context context, String daoName)
	{
		if (daoMaster == null)
		{
			OpenHelper helper = new DaoMaster.DevOpenHelper(context, daoName, null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}
	/**
	 * 取得DaoSession
	 *
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession(Context context, String daoName)
	{
		if (daoSession == null)
		{
			if (daoMaster == null)
			{
				daoMaster = getDaoMaster(context, daoName);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;

	}
}
