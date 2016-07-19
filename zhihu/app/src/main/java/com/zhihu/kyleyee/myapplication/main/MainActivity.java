package com.zhihu.kyleyee.myapplication.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhihu.kyleyee.myapplication.Adapter.HomeAdapter;
import com.zhihu.kyleyee.myapplication.Adapter.HomeViewpagerAdapter;
import com.zhihu.kyleyee.myapplication.Manager.ApiManager;
import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;
import com.zhihu.kyleyee.myapplication.model.New;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 主界面
 * Created by kyleYee on 2016/6/29.
 */
public class MainActivity extends BaseActivity implements HomeAdapter.OnItemClickListener {

    @Bind(R.id.toolbar_home)
    Toolbar mToolbar;
    @Bind(R.id.recycler_home)
    RecyclerView mRecyclerHome;
    @Bind(R.id.refresh_home)
    SwipeRefreshLayout mRefreshHome;

    private New mNewData;//最新消息
    private HomeAdapter mAdapter;//最新消息适配器
    private boolean mLoadingMore = false;//正在加载更多
    private String beforeDate = "0";//过去时间
    private HomeViewpagerAdapter mViewpagerAdapter;//轮播图适配器
    private List<View> mListView;//轮播图 图片集合

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
//        initData();
        loadData();
        initToolbar();
        initRefresh();
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
        beforeDate = mNewData.date;
    }


    /**
     * 初始化新消息列表，ViewPager放在header中，刷新用
     */
    private void initRecyclerView() {
        beforeDate = mNewData.date;
        mAdapter = new HomeAdapter(this, mNewData);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerHome.setLayoutManager(layoutManager);
        //设置轮播图
        initViewpager();
        mAdapter.setOnItemClickListener(this);
        mRecyclerHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastPosition >= layoutManager.getItemCount() - 1) {
                        //加载更多
                        if (!mLoadingMore) {
                            mLoadingMore = true;
                            beforeDate = String.valueOf(Integer.parseInt(beforeDate) - 1);
                            ApiManager.getInstance().getNewBefore(beforeDate, new ApiManager.ResultCallBack() {
                                @Override
                                public void onTaskSuccess(Object data) {
                                    New beforeData = (New) data;
                                    mNewData.stories.addAll(beforeData.stories);
                                    mAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onError(Object error) {
                                }

                                @Override
                                public void onFinish() {
                                    mLoadingMore = false;
                                }

                            });
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mRecyclerHome.setAdapter(mAdapter);
    }

    /**
     * 初始化刷新
     */
    private void initRefresh() {
        mRefreshHome.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.refresh));
        mRefreshHome.setRefreshing(false);
        mRefreshHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshHome.setRefreshing(true);
                loadData();
            }
        });
    }

    /**
     * 刷新或加载跟多数据
     */
    private void loadData() {
        ApiManager.getInstance().getNewList(new ApiManager.ResultCallBack() {
            @Override
            public void onTaskSuccess(Object data) {
                mNewData = (New) data;
                if (mNewData != null) {
                    mListView = null;
                    initRecyclerView();
                }
            }

            @Override
            public void onError(Object error) {
                Log.e("new", error.toString());
            }

            @Override
            public void onFinish() {
                mRefreshHome.setRefreshing(false);

            }

        });
    }

    /**
     * 初始化轮播图
     */
    private void initViewpager() {
        //要添加带轮播图上的图片
        mListView = new ArrayList<>();
        View pagerLayout = LayoutInflater.from(this).inflate(R.layout.item_home_viewpager, null);
        ViewPager viewpager = (ViewPager) pagerLayout.findViewById(R.id.home_viewpager);

        int with = getWindowManager().getDefaultDisplay().getWidth();
        float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        for (int i = 0; i < mNewData.top_stories.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_viewpager_iamge, viewpager, false);
            mListView.add(view);
        }
        mViewpagerAdapter = new HomeViewpagerAdapter(mListView);
        //有Viewpager的布局
//        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(mViewpagerAdapter);
        mAdapter.setHeaderView(pagerLayout);
        for (int i = 0; i < mNewData.top_stories.size(); i++) {
            ImageView draweeView = (ImageView) mListView.get(i).findViewById(R.id.home_viewpager_image);
            Glide.with(this)
                    .load(mNewData.top_stories.get(i).image)
                    .override(with, (int) height)
                    .centerCrop()
                    .into(draweeView);
        }
    }

    /**
     * Item点击事件
     *
     * @param position 位置
     * @param itemView 当前view
     * @param Id       当前点击新闻ID
     */
    @Override
    public void onItemClick(int position, View itemView, int Id) {
        Toast.makeText(this, mNewData.stories.get(position).title, Toast.LENGTH_SHORT).show();
    }
}
