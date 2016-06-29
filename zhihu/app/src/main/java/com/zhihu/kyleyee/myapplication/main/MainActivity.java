package com.zhihu.kyleyee.myapplication.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;

/**
 * 主界面
 * Created by kyleYee on 2016/6/29.
 */
public class MainActivity extends BaseActivity {


    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }
}
