package com.zhihu.kyleyee.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zhihu.kyleyee.myapplication.model.New;
import com.zhihu.kyleyee.myapplication.model.Stories;
import com.zhihu.kyleyee.myapplication.modul.NewsContent;

import java.util.List;

/**
 * 轮播图
 * Created by kyleYee on 2016/7/18.
 */
public class HomeViewpagerAdapter extends PagerAdapter {

    private List<View> mListView;
    private Activity mActivity;
    private List<Stories> mData;

    public HomeViewpagerAdapter(List<View> mListView, Activity activity, List<Stories> data) {
        this.mListView = mListView;
        this.mActivity = activity;
        this.mData = data;
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
    public Object instantiateItem(final ViewGroup container, final int position) {
        container.addView(mListView.get(position));
        mListView.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData == null) return;

                NewsContent.StartActivity(mActivity, mData.get(position).id);
            }
        });
        return mListView.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
