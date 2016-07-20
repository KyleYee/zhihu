package com.zhihu.kyleyee.myapplication.manager;

import android.app.Activity;

/**
 * 图片加载管理类
 * Created by kyleYee on 2016/7/3.
 */
public class ImgLoadManager {
    private static volatile ImgLoadManager mImgLoadManager;

    private ImgLoadManager() {
    }

    public static synchronized ImgLoadManager getInstance(Activity activity) {
        if (mImgLoadManager == null) {
            synchronized (ImgLoadManager.class) {
                if (mImgLoadManager == null) {
                    mImgLoadManager = new ImgLoadManager();
                }
            }
        }
        return mImgLoadManager;
    }

    ;
}
