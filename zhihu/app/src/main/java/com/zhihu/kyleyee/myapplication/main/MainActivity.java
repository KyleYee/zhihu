package com.zhihu.kyleyee.myapplication.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zhihu.kyleyee.myapplication.Adapter.HomeAdapter;
import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;
import com.zhihu.kyleyee.myapplication.model.New;

import butterknife.Bind;

/**
 * 主界面
 * Created by kyleYee on 2016/6/29.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;
    @Bind(R.id.recycler_home)
    RecyclerView mRecyclerHome;
    @Bind(R.id.refresh_home)
    SwipeRefreshLayout mRefreshHome;

    private New mNewData;//最新消息
    private HomeAdapter mAdapter;//最新消息适配器

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
        initToolbar();
        initRecyclerView();
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        mNewData = (New) intent.getBundleExtra(StartActivity.NEW_BUNDLE)
                .getSerializable(StartActivity.NEW_BUNDLE);
    }


    /**
     * 初始化新消息列表，ViewPager放在header中，刷新用
     */
    private void initRecyclerView() {
        mAdapter = new HomeAdapter(this, mNewData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerHome.setLayoutManager(layoutManager);
        mRecyclerHome.setAdapter(mAdapter);
    }
}
