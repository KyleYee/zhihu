package com.zhihu.kyleyee.myapplication.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;
import com.zhihu.kyleyee.myapplication.model.New;

/**
 * 主界面
 * Created by kyleYee on 2016/6/29.
 */
public class MainActivity extends BaseActivity {

    //最新消息
    private New newData;

    public static void startMainActivity(Context context, New newData) {
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StartActivity.NEW_BUNDLE, newData);
        intent.putExtra(StartActivity.NEW_BUNDLE, bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        newData = (New) intent.getBundleExtra(StartActivity.NEW_BUNDLE)
                .getSerializable(StartActivity.NEW_BUNDLE);
    }
}
