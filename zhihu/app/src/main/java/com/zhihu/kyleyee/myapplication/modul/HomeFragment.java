package com.zhihu.kyleyee.myapplication.modul;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.adapter.HomeAdapter;
import com.zhihu.kyleyee.myapplication.adapter.HomeViewpagerAdapter;
import com.zhihu.kyleyee.myapplication.base.BaseFragment;
import com.zhihu.kyleyee.myapplication.manager.ApiManager;
import com.zhihu.kyleyee.myapplication.model.New;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 首页
 * Created by yunnnn on 2016/7/26.
 */
public class HomeFragment extends BaseFragment implements HomeAdapter.OnItemClickListener, ViewPager.OnPageChangeListener {

    //滑动监听
    public interface ToolbarScrollListener {
        void setTitle(String content);
    }

    private ToolbarScrollListener mToolbarScrollListener;

    public void setToolbarScrollListener(ToolbarScrollListener toolbarScrollListener) {
        this.mToolbarScrollListener = toolbarScrollListener;
    }

    private static final int DELAY_MILLIS = 4000;

    @Bind(R.id.recycler_home)
    RecyclerView mRecyclerHome;
    @Bind(R.id.refresh_home)
    SwipeRefreshLayout mRefreshHome;
    @Bind(R.id.back_top)
    FloatingActionButton mBackTop;

    private New mNewData;//最新消息
    private HomeAdapter mAdapter;//最新消息适配器
    private boolean mLoadingMore = false;//正在加载更多
    private String beforeDate = "0";//过去时间
    private HomeViewpagerAdapter mViewpagerAdapter;//轮播图适配器
    private List<View> mListView;//轮播图 图片集合
    private List<View> mPointView;//轮播图下方小点
    private View mBeforePoint;//切换前的小点
    private int mCurrentPosition;//当前位置
    private boolean isScrolling = false;//是否正手动在滑动
    private ViewPager mViewpager;
    private LinearLayoutManager mLayoutManager;
    private boolean mBackTopIsShow = false;
    private Activity mActivity;

    /**
     * 自动轮播
     */
    private Handler mPagerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mViewpager == null) {
                return;
            }
            if (isScrolling) {
                mPagerHandler.sendEmptyMessageDelayed(0, DELAY_MILLIS);
                return;
            }
            if (mCurrentPosition == mNewData.top_stories.size() - 1) {
                mCurrentPosition = 0;
            } else {
                mCurrentPosition += 1;
            }
            mViewpager.setCurrentItem(mCurrentPosition, true);
            mPagerHandler.sendEmptyMessageDelayed(0, DELAY_MILLIS);
        }
    };


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(LayoutInflater inflater, View rootView, Bundle savedInstanceState) {
        super.init(inflater, rootView, savedInstanceState);
        loadData();
        initRefresh();
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
                    mPointView = null;
                    mCurrentPosition = 0;
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
     * 初始化新消息列表，ViewPager放在header中，刷新用
     */
    private void initRecyclerView() {
        beforeDate = mNewData.date;
        //设置每一天的第一个item的date值
        mNewData.stories.get(0).date = beforeDate;
        mAdapter = new HomeAdapter(getContext());
        mAdapter.setData(mNewData.stories);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerHome.setLayoutManager(mLayoutManager);
        //设置轮播图
        initViewpager();
        mAdapter.setOnItemClickListener(this);
        loadMore(mLayoutManager);
        mRecyclerHome.setAdapter(mAdapter);
    }

    /**
     * 加载更多
     *
     * @param layoutManager
     */
    private void loadMore(final LinearLayoutManager layoutManager) {
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
                                    if (beforeData == null) return;
                                    beforeData.stories.get(0).date = beforeData.date;
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
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                setToolbar(manager);
            }
        });
    }

    private void setToolbar(LinearLayoutManager layoutManager) {
        int currentPosition = layoutManager.findFirstVisibleItemPosition();
        if (currentPosition == 0) {
//            mToolbar.setTitle("首页");
            mToolbarScrollListener.setTitle("首页");
            if (mBackTopIsShow) {
                setGoneAnimation(mBackTop);
                mBackTopIsShow = false;
            }
            return;
        }

        if (!mBackTopIsShow) {
            setVisiblityAnimation(mBackTop);
            mBackTopIsShow = true;
        }
        View view = mRecyclerHome.getChildAt(0);
        TextView date = (TextView) view.findViewById(R.id.date);
        if (date != null && date.getText() != null && !date.getText().equals("")) {
            mToolbarScrollListener.setTitle(date.getText() + "");
        }
    }

    /**
     * 显示动画
     *
     * @param mBackTop
     */
    private void setVisiblityAnimation(FloatingActionButton mBackTop) {
        PropertyValuesHolder pvA = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder pvT = PropertyValuesHolder.ofFloat("translationY", mBackTop.getTranslationY(), -80);
        ObjectAnimator.ofPropertyValuesHolder(mBackTop, pvA, pvT).setDuration(800).start();
    }

    /**
     * 隐藏动画
     *
     * @param mBackTop
     */
    private void setGoneAnimation(FloatingActionButton mBackTop) {
        PropertyValuesHolder pvA = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        PropertyValuesHolder pvT = PropertyValuesHolder.ofFloat("translationY", mBackTop.getTranslationY(), 80);
        ObjectAnimator.ofPropertyValuesHolder(mBackTop, pvA, pvT).setDuration(800).start();
    }


    /**
     * 初始化轮播图
     */
    private void initViewpager() {
        //要添加带轮播图上的图片
        mListView = new ArrayList<>();
        mPointView = new ArrayList<>();
        View pagerLayout = LayoutInflater.from(mContext).inflate(R.layout.item_home_viewpager, null);
        mViewpager = (ViewPager) pagerLayout.findViewById(R.id.home_viewpager);
        LinearLayout viewpagerPoint = (LinearLayout) pagerLayout.findViewById(R.id.ll_viewpager_point);

        int with = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        for (int i = 0; i < mNewData.top_stories.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_viewpager_iamge, mViewpager, false);
            View point = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_point, viewpagerPoint, false);
            if (i == 0) {
                point.setEnabled(false);
                mBeforePoint = point;
            }
            mPointView.add(point);
            mListView.add(view);
            viewpagerPoint.addView(point);
        }
        mViewpagerAdapter = new HomeViewpagerAdapter(mListView);
        //有Viewpager的布局
//        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.setAdapter(mViewpagerAdapter);
        mAdapter.setHeaderView(pagerLayout);
        for (int i = 0; i < mNewData.top_stories.size(); i++) {
            ImageView draweeView = (ImageView) mListView.get(i).findViewById(R.id.home_viewpager_image);
            Glide.with(this)
                    .load(mNewData.top_stories.get(i).image)
                    .override(with, (int) height)
                    .centerCrop()
                    .into(draweeView);
        }

        //设置viewpager监听事件
        mViewpager.addOnPageChangeListener(this);

        //设置自动轮播
        setAutoPlay();
    }


    //自动轮播
    private void setAutoPlay() {
        mPagerHandler.sendEmptyMessageDelayed(1, DELAY_MILLIS);
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
        NewsContent.StartActivity(getActivity(), Id);
    }

    @OnClick(R.id.back_top)
    public void BackTop(View view) {
        mLayoutManager.scrollToPosition(0);
    }

    /**
     * viewpager页卡切换监听
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPointView.get(position).setEnabled(false);
        mBeforePoint.setEnabled(true);
        mBeforePoint = mPointView.get(position);
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                //正在拖动
                isScrolling = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                //没有动作的时候
            case ViewPager.SCROLL_STATE_SETTLING:
                //滑动完了
                isScrolling = false;
                break;
        }
    }
}
