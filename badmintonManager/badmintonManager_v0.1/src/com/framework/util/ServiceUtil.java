package com.framework.util;
/**
 * Created by Administrator on 15-5-22.
 */

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * User: puppey
 * Date: 2015-05-22
 * Time: 11:46
 * FIXME
 */
public class ServiceUtil {

    public static void hideInput(Context context, View v){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        /*InputMethodManager m=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);*/
    }
}
