package com.framework.base;
import android.content.Context;
/**
 * 去除了系统Toast的重叠问题
 * @author lee
 *
 */
public class Toast extends android.widget.Toast {
	private static android.widget.Toast toast;
	public Toast(Context context) {
		super(context);
	}
	/**
	 * 推荐使用，去除重叠了的
	 */
	public static android.widget.Toast makeText(CharSequence text,Context context, int duration) {
		if(toast == null){
			toast = makeText(context,text,duration);
		}else{
			toast.setText(text);
		}
		return toast;
	}
	/**
	 * 推荐使用，去除重叠了的
	 */
	public static android.widget.Toast makeText(int resId,Context context, int duration) {
		if(toast == null){
			toast = makeText(context,resId,duration);
		}else{
			toast.setText(resId);
		}
		return toast;
	}
	
	public static void showLong(Context context, String message) {
        show(context, message, Toast.LENGTH_LONG);
    }
 
    public static void showShort(Context context, String message) {
        show(context, message, Toast.LENGTH_SHORT);
    }
 
    public static void showLong(Context context, int textId) {
        show(context, textId, Toast.LENGTH_LONG);
    }
 
    public static void showShort(Context context, int textId) {
        show(context, textId, Toast.LENGTH_SHORT);
    }
 
    public static void show(Context context, String text, int duration) {
        toast = makeText(text, context, duration);
//        toast.setGravity(GRAVITY, 80, 80);
        toast.show();
    }
 
    public static void show(Context context, int textId, int duration) {
    	toast = makeText(textId, context, duration);
//        toast.setGravity(GRAVITY, 80, 80);
        toast.show();
    }
}
