package com.zhihu.kyleyee.myapplication.modul;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.adapter.HomeAdapter;
import com.zhihu.kyleyee.myapplication.adapter.ThemeAdapter;
import com.zhihu.kyleyee.myapplication.base.BaseFragment;
import com.zhihu.kyleyee.myapplication.manager.ApiManager;
import com.zhihu.kyleyee.myapplication.model.ThemeContent;
import com.zhihu.kyleyee.myapplication.model.Themes;
import com.zhihu.kyleyee.myapplication.model.ThemesList;

import butterknife.Bind;

/**
 * Created by yunnnn on 2016/7/26.
 */
public class ThemeCommonFragment extends BaseFragment {

    @Bind(R.id.recycler_theme)
    RecyclerView mRecyclerView;

    private HomeAdapter mAdapter;
    private ThemesList mThemes;
    private ThemeContent mThemeContent;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_theme_common;
    }

    @Override
    protected void init(LayoutInflater inflater, View rootView, Bundle savedInstanceState) {
        super.init(inflater, rootView, savedInstanceState);
        loadData();
    }

    private void loadData() {
        mThemes = (ThemesList) getArguments().getSerializable("themes");
        if (mThemes == null) return;

        ApiManager.getInstance().getThemeContent(mThemes.id, new ApiManager.ResultCallBack() {
                    @Override
                    public void onTaskSuccess(Object data) {
                        mThemeContent = (ThemeContent) data;
                        if (mThemeContent == null) return;
                        setupRecyclerView();
                    }

                    @Override
                    public void onError(Object error) {
                        Log.e("error:", error.toString());
                    }

                    @Override
                    public void onFinish() {

                    }
                }
        );
    }

    private void setupRecyclerView() {
        mAdapter = new HomeAdapter(getContext());
        mAdapter.setData(mThemeContent.stories);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
