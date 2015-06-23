package com.framework.base;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.json.JSONObject;

import com.example.badmintonmanager.R;
import com.framework.net.AsyncTaskUtil;
import com.framework.net.FileDownLoad;
import com.framework.net.NetWorkUtils;
import com.framework.net.OnResultListener;
import com.framework.util.AppUtils;
import com.framework.util.DialogTools;
import com.framework.util.MyLogger;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 璇ョ被鍔犲叆浠ヤ笅鍔熻兘锛�<br />
 * 1."鍐嶆寜涓�娆￠��鍑虹▼搴�"鎻愮ず(鍙渶瑕佽皟鐢╝ddFirstToast()鏂规硶鎶奱ctivity娣诲姞鍒伴��鍑洪�氱煡鎻愮ず鐨勯泦鍚堝嵆鍙�) <br />
 * 2.鑷姩妫�娴嬬増鏈彁绀烘洿鏂�<br />
 * 鏈嶅姟鍣ㄨ繑鍥炴暟鎹鏄庯細渚� {"flag":1,"appversion":鏈�鏂扮増鏈彿,"content":"鏇存柊鍐呭","url":"鏈�鏂扮増鏈殑涓嬭浇鍦板潃"}
 * flag: "1"鏁版嵁鑾峰彇鎴愬姛
 * 寮�鍚嚜鍔ㄦ洿鏂扮増鏈殑鍔熻兘锛歴p.putBoolean(SPContants.AUTO_UPLOAD,true).commit();
 * 3.鏃犻渶涓簐iew璁剧疆鐩戝惉鎿嶄綔锛屽彧闇�瑕佸０鏄庝竴涓猧nt[]绫诲瀷鐨刬ds鍙橀噺锛屾妸鎵�鏈夎鐩戝惉鐨勬帶浠秈d鏀惧埌璇ユ暟缁勪腑鍗冲彲锛屽悓鏃堕渶瑕佺户鎵胯绫�
 * 4.瀵笰syncTask鍋氫簡鍐呭瓨浼樺寲銆�
 * @author lee
 * 		   gwm 鍦ˋndroid framework涓嬶紝
 *         寤鸿浣跨敤浼樺寲杩囩殑鏁版嵁瀹瑰櫒姣斿锛歋parseArray,SparseBooleanArray,LongSparseArray銆�
 *         閫氱敤鐨凥ashMap瀹炵幇鐨勫唴瀛樹娇鐢ㄧ巼闈炲父鐨勪綆锛屽洜涓轰粬闇�瑕佷负姣忎竴涓猰apping鍒涘缓涓�涓垎绂荤殑entry
 *         object銆傚彟澶栵紝SparseArray绫婚伩鍏嶄簡绯荤粺瀵规湁浜沰ey鐨勮嚜鍔ㄨ绠憋紝鍥犺�屽甫鏉ヤ簡鏇撮珮鐨勬晥鐜囥��
 */
public abstract class BaseActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener,
		OnLongClickListener, OnItemSelectedListener {
	private List<String> activitys;
	/** 鐗堟湰鏇存柊鎻愮ず */
	private final String url = ""; // 鑾峰彇鏈嶅姟鍣ㄤ笂apk鏈�鏂扮増鏈殑url
	private final String path = ""; // 淇濆瓨鍒版湰鍦扮殑鏂囦欢璺緞
	private SharedPreferences sp;
	private AppUtils app_util;
	private FragmentManager manager;
	public MyLogger Log;

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log = MyLogger.kLog();
		sp = getSharedPreferences(SPContants.CONFIG,Context.MODE_PRIVATE);
		manager = getFragmentManager();
		app_util = new AppUtils(getApplicationContext());
		if (getApplication() instanceof BaseAppcation) { // 灏哸ctivity娣诲姞鍒拌嚜瀹氫箟鐣岄潰闆嗗悎
			((BaseAppcation) getApplication()).addActivity(this);
			activitys = ((BaseAppcation) getApplication()).getUIS();
		}
		onCreate(sp, manager, savedInstanceState);
		// 鑷姩妫�娴嬬増鏈洿鏂版彁绀�
		autoLoad();
	}

	private void autoLoad() {
		boolean flag = ((BaseAppcation) getApplication()).isFlag();
		if (flag) { // 鍒ゆ柇鏄惁鏈娴嬭繃鏂扮増鏈�
			if (NetWorkUtils.getNetConnecState(this) && sp.getBoolean(SPContants.AUTO_UPLOAD,true)) { // 鍒ゆ柇褰撳墠鏄惁鏈夌綉缁滃拰鏄惁宸插紑鍚嚜鍔ㄦ洿鏂扮増鏈殑鍔熻兘
				((BaseAppcation) getApplication()).setFlag(false); // 淇敼鏍囪瘑锛屾爣璇嗗凡缁忔娴嬭繃鏂扮増鏈�
				AsyncTaskUtil.sendGet(url,new OnResultListener() {
							@Override
							public void onGetResult(Object result,int iError) throws Exception {
								JSONObject json = new JSONObject(result.toString());
								int flags = json.getInt("flag");
								if(flags == 1) {
									double version = json.getDouble("appversion");
									String content = json.getString("content");
									String downurl = json.getString("url"); // 鑾峰彇涓嬭浇鏈�鏂扮増鏈殑url
									double currentVersion = Double.parseDouble(getVersionName());
									if (currentVersion < version) {
										showUploadDialog(content,downurl);
									}
								}
							}
						}, this);
			} else {
				DialogTools.showNoNetWork(this);
			}
		}
	}

	/**
	 * 鏄剧ず鏇存柊鎻愮ず瀵硅瘽妗�
	 * 
	 * @param content
	 *                 鏇存柊鎻愮ず鍐呭
	 * @param url
	 *                 鏈�鏂扮増鏈殑涓嬭浇鍦板潃
	 */
	protected void showUploadDialog(String content, final String downurl) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(content);
		builder.setPositiveButton("鍙栨秷",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,int which) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("纭畾",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,int which) {
				// 涓嬭浇鏈�鏂扮増鏈苟瀹夎
				FileDownLoad.down(path,url,getApplicationContext(),new OnResultListener() {
					@Override
					public void onGetResult(Object result,int iError) throws Exception {
						installApk(path);
					}
				});
			}
		});
		builder.show();
	}
	/**
	 * 瀹夎鎸囧畾鏂囦欢璺緞鐨刟pk鏂囦欢
	 * @param path
	 */
	protected void installApk(String path) {
		app_util.installApk(path);
	}
	/**
	 * 灏咥ctivity娣诲姞鍒伴��鍑洪�氱煡
	 * @param activityName
	 *                 activity鐨勭被鍚�
	 */
	public void addFirstToast(String activityName) {
		activitys.add(activityName);
	}
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000&& activitys.contains(getActivityName())) {
				Toast.makeText(getApplicationContext(),"鍐嶆寜涓�娆￠��鍑虹▼搴�",Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else if (activitys.contains(getActivityName())) {
				if (getApplication() instanceof BaseAppcation) {
					((BaseAppcation) getApplication()).exit();
				}
			}
		}
		if (keyCode == KeyEvent.KEYCODE_BACK&& activitys.contains(getActivityName())) {
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (getApplication() instanceof BaseAppcation) {
				((BaseAppcation) getApplication()).delActivity(this);
			}
			return true;
		}
		return onKeyDownMethod(keyCode, event);
	}

	/**
	 * 鏃犻渶閲嶅啓onKeyDown()鏂规硶,闇�瑕佹椂搴旇閲嶅啓璇ユ柟娉� 宸茬粡灞忚斀浜嗚繑鍥為敭锛屽闇�澶勭悊杩斿洖閿紝璇烽噸鍐檕nKeyDown()鏂规硶
	 * @param keyCode
	 * @param event
	 * @return
	 */
	protected boolean onKeyDownMethod(int keyCode, KeyEvent event) {
		//濡傛灉鍚敤浜咮asePage绫伙紝涓嬮潰涓よ浠ｇ爜闇�瑕佸鍒跺湪activity鐨刼nKeyDown()鏂规硶涓�
//		BaseFragment frag = (BaseFragment) manager.findFragmentByTag("");
//		return frag.getPager().onKeyDownMethod(keyCode,event);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		if (getApplication() instanceof BaseAppcation) {
			((BaseAppcation) getApplication()).clearAsyncTask();
		}
		super.onDestroy();
	}

	/**
	 * 鑾峰彇褰撳墠姝ｅ湪杩愯鐨凙ctivity
	 * 
	 * @return
	 */
	private String getActivityName() {
		return app_util.getActivityName();
	}

	/**
	 * 鑾峰彇褰撳墠搴旂敤鐨勭増鏈彿
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getVersionName() {
		return app_util.getVersionName();
	}

	public <T extends BaseFragment> T getFragment(String tag) {
		return (T) manager.findFragmentByTag(tag);
	}

	public <D extends View> D findViewToId(int id) {
		return (D) (getWindow().findViewById(id));
	}

	/**
	 * 鏅�氭帶浠剁偣鍑讳簨浠�
	 */
	@Override
	public void onClick(View v) {

	}

	/**
	 * 鏅�氭帶浠堕暱鎸夌偣鍑讳簨浠�
	 */
	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	/**
	 * ListView鐨刬tem鐐瑰嚮浜嬩欢
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
	}

	public void startActivity(Class<BaseActivity> clz) {
		Intent intent = new Intent(getApplicationContext(), clz);
		startActivity(intent);
	}

	public void startActivity(Class<BaseActivity> clz, int enterAnim,
			int exitAnim) {
		Intent intent = new Intent(getApplicationContext(), clz);
		startActivity(intent, enterAnim, exitAnim);
	}

	public void startActivity(String action) {
		Intent intent = new Intent(action);
		startActivity(intent);
	}

	/**
	 * activity涔嬮棿鐨勫垏鎹紝榛樿娓愬叆娓愬嚭鍔ㄧ敾
	 */
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.fade, R.anim.hold);
		// overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit); //缂╂斁鍔ㄧ敾
	}

	@Override
	public Context getApplicationContext() {
		return new SoftReference<Context>(super.getApplicationContext()).get();
	}

	/**
	 * 鑷畾涔塧ctivity涔嬮棿鐨勫垏鎹㈠姩鐢�
	 * 
	 * @param intent
	 * @param enterAnim
	 *                 杩涘叆鏃跺姩鐢�
	 * @param exitAnim
	 *                 鍑哄幓鏃跺姩鐢�
	 */
	public void startActivity(Intent intent, int enterAnim, int exitAnim) {
		super.startActivity(intent);
		overridePendingTransition(enterAnim, exitAnim);
	}

	/**
	 * ListView鐨刬tem闀挎寜浜嬩欢鐩戝惉
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		return false;
	}

	/**
	 * Spinner鎺т欢鐨勪簨浠剁洃鍚�
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view,
			int position, long id) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	/**
	 * Activity鐨勭敓鍛藉懆鏈無nCreate()鏂规硶
	 * @param sp
	 *                 SharedPreferences鏂囦欢瀵硅薄
	 * @param manager
	 *                 绠＄悊Fragment鐨勫璞★紝濡傛灉浣跨敤Fragment鍙互璺熸湰妗嗘灦鍋氬埌鏃犵紳鐨勭粨鍚�
	 * @param savedInstanceState
	 *                 Activity鐨勭姸鎬佷繚瀛�
	 */
	public abstract void onCreate(SharedPreferences sp,FragmentManager manager, Bundle savedInstanceState);
	/**
	 * Fragment涔嬮棿鐨勫垏鎹㈡柟娉曪紝鍔犺浇Fragment鏂规硶.<br />
	 * 閲嶅啓璇ユ柟娉曟椂瑕佹敞鎰忕殑闂锛�
	 * <ol>
	 * <li>璇ユ柟娉曞敖閲忔斁鍦╝ctivity涓�傞伩鍏岶ragment鐨勫祵濂楀嚭鐜癷d璧勬簮鎵句笉鍒扮殑闂,鏈�濂紽ragment閲岄潰涓嶈鍦ㄥ祵濂桭ragment.</li>
	 * <li>鍦ㄥ姞杞戒箣鍓嶉渶鍒ゆ柇鏄惁宸茬粡鍔犺浇浜嗭紝濡傛灉鏄� 璇峰厛绉婚櫎锛屽湪鍔犺浇銆傞伩鍏嶆姤璇ragment宸茬粡鍔犺浇浜嗙殑闂.</li>
	 * <li>璇ユ柟娉曚笉鑳藉湪activity鐨勭敓鍛藉懆鏈熶腑浣跨敤銆傞伩鍏嶅洖閫�鏍堢殑闂.<br />
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 * (鍘熷洜锛歛ddToBackStack()鏂规硶涓嶈兘鍦╝ctivity鐨勭敓鍛藉懆鏈熶腑浣跨敤)</li>
	 * </ol>
	 * @param frag
	 *                 涓棿瀹瑰櫒鏄剧ず鐨凢ragment
	 * @param tag
	 *                 涓棿瀹瑰櫒鐨勬爣璇�
	 */
	public void jumpFragment(BaseFragment frag, String tag) {
	}
	/**
	 * @param frag
	 * @param tag
	 * @param isHiddenNav  鏄惁闅愯棌瀵艰埅鏍忥紝鑷充簬濡備綍瀹炵幇闅愯棌闇�寮�鍙戣�呰嚜宸辫�冭檻
	 */
	public void jumpFragment(BaseFragment frag, String tag,boolean isHiddenNav) {
	}
}