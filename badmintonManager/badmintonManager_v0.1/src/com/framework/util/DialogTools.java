package com.framework.util;

import java.util.Calendar;

import com.example.badmintonmanager.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogTools {

	/**
	 * 鍒涘缓鏅崟鎸夐挳瀵硅瘽妗�
	 * 
	 * @param ctx
	 * @param iconId
	 *            鍥炬爣锛屽锛歊.drawable.icon 蹇呭～
	 * @param title
	 *            鏍囬 蹇呭～
	 * @param message
	 *            鏄剧ず鍐呭 蹇呭～
	 * @param btnName
	 *            鎸夐挳鍚嶇О 蹇呭～
	 * @param listener
	 *            鐩戝惉鍣�
	 * @return
	 */
	public static Dialog createDialog(Context ctx, int iconId, String title, String message, String btnName, android.content.DialogInterface.OnClickListener listener) {
		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
		// 璁剧疆瀵硅瘽妗嗙殑鍥炬爣
		builder.setIcon(iconId);
		// 璁剧疆瀵硅瘽妗嗙殑鏍囬
		builder.setTitle(title);
		// 璁剧疆瀵硅瘽妗嗙殑鏄剧ず鍐呭
		builder.setMessage(message);
		// 娣诲姞鎸夐挳锛宎ndroid.content.DialogInterface.OnClickListener.OnClickListener
		builder.setPositiveButton(btnName, listener);
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 鍒涘缓鍙屾寜閽璇濇
	 * 
	 * @param ctx
	 *            涓婁笅鏂囧繀濉�
	 * @param iconId
	 *            鍥炬爣锛屽锛歊.drawable.icon[涓嶆兂鏄剧ず灏卞啓0] 蹇呭～
	 * @param title
	 *            鏍囬 蹇呭～
	 * @param message
	 *            鏄剧ず鍐呭 蹇呭～
	 * @param btnPositiveName
	 *            绗竴涓寜閽悕蹇呭～
	 * @param listener_Positive
	 *            绗竴涓洃鍚櫒锛岄渶瀹炵幇android.content.DialogInterface.OnClickListener鎺ュ彛 蹇呭～
	 * @param btnNegativeName
	 *            绗簩涓寜閽悕蹇呭～
	 * @param listener_Negative
	 *            绗簩涓洃鍚櫒锛岄渶瀹炵幇android.content.DialogInterface.OnClickListener鎺ュ彛 蹇呭～
	 * @return 瀵硅瘽妗嗗疄鐜�
	 */
	public static Dialog createDialog(Context ctx, int iconId, String title, String message, String btnPositiveName, android.content.DialogInterface.OnClickListener listener_Positive,
			String btnNegativeName, android.content.DialogInterface.OnClickListener listener_Negative) {
		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
		// 璁剧疆瀵硅瘽妗嗙殑鍥炬爣
		builder.setIcon(iconId);
		// 璁剧疆瀵硅瘽妗嗙殑鏍囬
		builder.setTitle(title);
		// 璁剧疆瀵硅瘽妗嗙殑鏄剧ず鍐呭
		builder.setMessage(message);
		// 娣诲姞鎸夐挳锛宎ndroid.content.DialogInterface.OnClickListener.OnClickListener
		builder.setPositiveButton(btnPositiveName, listener_Positive);
		// 娣诲姞鎸夐挳锛宎ndroid.content.DialogInterface.OnClickListener.OnClickListener
		builder.setNegativeButton(btnNegativeName, listener_Negative);
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 鍒涘缓鍒楄〃瀵硅瘽妗�
	 * 
	 * @param ctx
	 *            涓婁笅鏂囧繀濉�
	 * @param iconId
	 *            鍥炬爣锛屽锛歊.drawable.icon 蹇呭～
	 * @param title
	 *            鏍囬 蹇呭～
	 * @param itemsId
	 *            瀛楃涓叉暟缁勮祫婧恑d 蹇呭～
	 * @param listener
	 *            鐩戝惉鍣紝闇�瀹炵幇android.content.DialogInterface.OnClickListener鎺ュ彛 蹇呭～
	 * @return
	 */
	public static Dialog createListDialog(Context ctx, int iconId, String title, int itemsId, android.content.DialogInterface.OnClickListener listener) {
		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
		// 璁剧疆瀵硅瘽妗嗙殑鍥炬爣
		builder.setIcon(iconId);
		// 璁剧疆瀵硅瘽妗嗙殑鏍囬
		builder.setTitle(title);
		// 娣诲姞鎸夐挳锛宎ndroid.content.DialogInterface.OnClickListener.OnClickListener
		builder.setItems(itemsId, listener);
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 
	 * @param ctx
	 *            涓婁笅鏂囧繀濉�
	 * @param iconId
	 *            鍥炬爣锛屽锛歊.drawable.icon 蹇呭～
	 * @param title
	 *            鏍囬 蹇呭～
	 * @param itemsId
	 *            瀛楃涓叉暟缁勮祫婧恑d 蹇呭～
	 * @param listener
	 *            鐩戝惉鍣紝闇�瀹炵幇android.content.DialogInterface.OnClickListener鎺ュ彛 蹇呭～
	 * @param btnName
	 *            鎸夐挳鍚嶇О 蹇呭～
	 * @param listener2
	 *            鎸夐挳鐩戝惉鍣紝闇�瀹炵幇android.content.DialogInterface.OnClickListener鎺ュ彛 蹇呭～
	 * @return
	 */
	public static Dialog createRadioDialog(Context ctx, int iconId, String title, int itemsId, android.content.DialogInterface.OnClickListener listener, String btnName,
			android.content.DialogInterface.OnClickListener listener2) {
		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
		// 璁剧疆瀵硅瘽妗嗙殑鍥炬爣
		builder.setIcon(iconId);
		// 璁剧疆瀵硅瘽妗嗙殑鏍囬
		builder.setTitle(title);
		// 0: 榛樿绗竴涓崟閫夋寜閽閫変腑
		builder.setSingleChoiceItems(itemsId, 0, listener);
		builder.setPositiveButton(btnName, listener2);
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 鍒涘缓澶嶆潅瀵硅瘽妗�
	 * 
	 * @param ctx
	 *            涓婁笅鏂囧繀濉�
	 * @param iconId
	 *            鍥炬爣锛屽锛歊.drawable.icon 蹇呭～
	 * @param title
	 *            鏍囬 蹇呭～
	 * @param itemsId
	 *            瀛楃涓叉暟缁勮祫婧恑d 蹇呭～
	 * @param flags
	 *            鍒濆鎯呭喌 蹇呭～
	 * @param listener
	 *            鍗曞嚮鎸夐挳椤圭洃鍚櫒锛岄渶瀹炵幇android.content.DialogInterface. OnMultiChoiceClickListener鎺ュ彛 蹇呭～
	 * @param btnName
	 *            鎸夐挳鍚嶇О 蹇呭～
	 * @param listener2
	 *            鎸夐挳鐩戝惉鍣紝闇�瀹炵幇android.content.DialogInterface.OnClickListener鎺ュ彛 蹇呭～
	 * @return
	 */
	public static Dialog createCheckBoxDialog(Context ctx, int iconId, String title, int itemsId, boolean[] flags, android.content.DialogInterface.OnMultiChoiceClickListener listener, String btnName,
			android.content.DialogInterface.OnClickListener listener2) {
		Dialog dialog = null;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ctx);
		// 璁剧疆瀵硅瘽妗嗙殑鍥炬爣
		builder.setIcon(iconId);
		// 璁剧疆瀵硅瘽妗嗙殑鏍囬
		builder.setTitle(title);
		builder.setMultiChoiceItems(itemsId, flags, listener);
		builder.setPositiveButton(btnName, listener2);
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 鏃ユ湡瀵硅瘽妗�
	 * 
	 * @param context
	 *            涓婁笅鏂�
	 * @param v
	 */
	public static Dialog createDateDialog(Context context, final View v) {
		Dialog dialog = null;
		Calendar calender = Calendar.getInstance();
		dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

				if (v instanceof TextView) {

					((TextView) v).setText(year + "骞�"+ (monthOfYear + 1) + "鏈�"+ dayOfMonth + "鏃�");
				}
				if (v instanceof EditText) {
					((EditText) v).setText(year + "骞�" + (monthOfYear + 1) + "鏈�" + dayOfMonth + "鏃�");
				}
			}
		}, calender.get(calender.YEAR), calender.get(calender.MONTH), calender.get(calender.DAY_OF_MONTH));

		return dialog;
	}

	/**
	 * 鍔犺浇瀵硅瘽妗�
	 * 
	 * @param context
	 *            涓婁笅鏂�
	 * @param msg
	 *            鍐呭
	 * @return
	 */
	public static ProgressDialog createLoadDialog(Context context, String msg) {
		android.app.ProgressDialog dialog = new ProgressDialog(context);
		// 璁剧疆椋庢牸涓哄渾褰㈣繘搴︽潯
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 璁剧疆鍐呭
		dialog.setMessage(msg);
		// 璁剧疆杩涘害鏉℃槸鍚︿负涓嶆槑锟�?
		dialog.setIndeterminate(false);
		// 璁剧疆杩涘害鏉℃槸鍚﹀彲浠ユ寜锟�?锟斤拷閿彇锟�?
		dialog.setCancelable(false);
		return dialog;
	}

	private static Dialog dialog;
	private static android.app.AlertDialog.Builder builder;

	public static void showProgressDialog(Context context) {
		showProgressDialog(context, "璇风瓑鍊欙紝鏁版嵁鍔犺浇涓�...");
	}

	public static void showProgressDialog(Context context, String msg) {
		 LayoutInflater inflater = LayoutInflater.from(context);  
	        View v = inflater.inflate(R.layout.loading_dialog, null);// 寰楀埌鍔犺浇view  
	        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 鍔犺浇甯冨眬  
	        // main.xml涓殑ImageView  
	        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);  
	        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 鎻愮ず鏂囧瓧  
	        // 鍔犺浇鍔ㄧ敾  
	        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(  
	                context, R.anim.dialog_anim);  
	        // 浣跨敤ImageView鏄剧ず鍔ㄧ敾  
	        spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
	        tipTextView.setText(msg);// 璁剧疆鍔犺浇淇℃伅  
	  
	        dialog = new Dialog(context, R.style.loading_dialog);// 鍒涘缓鑷畾涔夋牱寮廳ialog  
	  
	        dialog.setCancelable(true);// 涓嶅彲浠ョ敤鈥滆繑鍥為敭鈥濆彇锟�? 
	        dialog.setContentView(layout, new LinearLayout.LayoutParams(  
	                LinearLayout.LayoutParams.FILL_PARENT,  
	                LinearLayout.LayoutParams.FILL_PARENT));// 璁剧疆甯冨眬  
	        dialog.show(); 

	}

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 褰撳垽鏂綋鍓嶆墜鏈烘病鏈夌綉缁滄椂浣跨敤
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage("褰撳墠鏃犵綉缁�").setPositiveButton("璁剧疆", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// 璺宠浆鍒扮郴缁熺殑缃戠粶璁剧疆鐣岄潰
						Intent intent = new Intent();
						intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
						context.startActivity(intent);

					}
				}).setNegativeButton("鐭ラ亾浜� ",null).show();
	}

	

	/**
	 * 鍒涘缓鑷畾涔夊璇濇
	 * 
	 * @param context
	 */
	public static Dialog createSelfDialog(Context context,int iconId,String title, View view) {
		builder = new android.app.AlertDialog.Builder(context);
		builder.setCancelable(true);
		builder.setIcon(iconId);
		builder.setTitle(title);
		builder.setView(view);
		return builder.create();
	}
	public static Dialog createSelfDialog(Context context,int iconId,String title, View view,DialogInterface.OnClickListener negativelistener,DialogInterface.OnClickListener positivelistener) {
		builder = new android.app.AlertDialog.Builder(context);
		builder.setCancelable(true);
		builder.setIcon(iconId);
		builder.setTitle(title);
		builder.setView(view);
		builder.setNegativeButton("纭畾", negativelistener);
		builder.setPositiveButton("鍙栨秷", positivelistener);
		return builder.create();
	}
}