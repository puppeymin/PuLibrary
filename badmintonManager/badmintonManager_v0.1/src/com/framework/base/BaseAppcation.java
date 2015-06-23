package com.framework.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;

public class BaseAppcation extends Application{
	private List<String> UIS = new ArrayList<String>(); //闇�瑕侀��鍑烘彁绀虹殑activity闆嗗悎
	private List<AsyncTask<?,?,?>> asyncs = new ArrayList<AsyncTask<?,?,?>>();
	private boolean flag = true; //鏍囪瘑鏈娴嬭繃鐗堟湰         false 鏍囪瘑宸茬粡妫�娴嬭繃鐗堟湰浜�
	private static BaseAppcation instance;
	public static <T extends BaseAppcation> T getInstance(){
		return (T) instance;
	}
	public boolean isFlag() {
		return flag;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		//鍒濆鍖栫櫨搴﹀湴鍥惧紑鍙戠幆澧�
//		SDKInitializer.initialize(getApplicationContext());
		if(instance == null){
			instance = this;
		}
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	private List<Activity> activitys = new ArrayList<Activity>(); //鐣岄潰闆嗗悎
	public void addActivity(Activity activity){
		activitys.remove(activity);
		activitys.add(activity);
	}
	public void exit(){
		for(Activity activity : activitys){
			activity.finish();
		}
	}
	public void delActivity(Activity activity){
		activitys.remove(activity);
		activity.finish();
	}
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		Toast.makeText("褰撳墠搴旂敤宸茬粡鍐呭瓨涓嶈冻锛岃娉ㄦ剰銆傘�傘��", this, Toast.LENGTH_SHORT).show();
	}
	public List<String> getUIS() {
		return UIS;
	}
	public List<AsyncTask<?,?,?>> getAsyncs() {
		return asyncs;
	}
	public void put(AsyncTask<?,?,?> async){
		asyncs.add(async);
	}
    protected void clearAsyncTask() {
        Iterator<AsyncTask<?,?,?>> iterator = asyncs.iterator();
        while (iterator.hasNext()) {
            AsyncTask<?,?,?> asyncTask = iterator.next();
            if (asyncTask != null && !asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
        asyncs.clear();
    }
}
