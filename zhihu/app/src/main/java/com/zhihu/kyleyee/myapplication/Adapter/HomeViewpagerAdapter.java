package com.zhihu.kyleyee.myapplication.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 轮播图
 * Created by kyleYee on 2016/7/18.
 */
public class HomeViewpagerAdapter extends PagerAdapter {

    private List<View> mListView;

    public HomeViewpagerAdapter(List<View> mListView) {
        this.mListView = mListView;
    }

    @Override
    public int getCount() {
        return mListView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;//官方提示这样写
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mListView.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListView.get(position));
    }
}
