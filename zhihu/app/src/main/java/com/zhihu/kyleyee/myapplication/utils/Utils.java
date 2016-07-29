package com.zhihu.kyleyee.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * 工具类
 * Created by yunnnn on 2016/7/23.
 */
public class Utils {

    //获取屏幕宽度
    public static int getScreenWith(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }

    //像素转dp
    public static int pxToDp(int px, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }

    public static int pxToSp(int px,Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,px,context.getResources().getDisplayMetrics());
    }
}
