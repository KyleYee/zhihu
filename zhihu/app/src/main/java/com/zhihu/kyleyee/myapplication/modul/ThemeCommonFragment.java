package com.zhihu.kyleyee.myapplication.modul;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.adapter.HomeAdapter;
import com.zhihu.kyleyee.myapplication.base.BaseFragment;
import com.zhihu.kyleyee.myapplication.main.AppConstants;
import com.zhihu.kyleyee.myapplication.manager.ApiManager;
import com.zhihu.kyleyee.myapplication.model.ThemeContent;
import com.zhihu.kyleyee.myapplication.model.ThemesList;

import butterknife.Bind;

/**
 * 主题列表
 * Created by yunnnn on 2016/7/26.
 */
public class ThemeCommonFragment extends BaseFragment implements HomeAdapter.OnItemClickListener {

    @Bind(R.id.recycler_theme)
    RecyclerView mRecyclerView;

    private HomeAdapter mAdapter;
    private ThemesList mThemes;
    private ThemeContent mThemeContent;
    private LinearLayoutManager mLayoutManager;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(AppConstants.SAVE_INSTANCE_THEME_COMMON_DATA, mThemeContent);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_theme_common;
    }

    @Override
    protected void init(LayoutInflater inflater, View rootView, Bundle savedInstanceState) {
        super.init(inflater, rootView, savedInstanceState);

        if (savedInstanceState != null) {
            mThemeContent = (ThemeContent) savedInstanceState.getSerializable(AppConstants.SAVE_INSTANCE_THEME_COMMON_DATA);
        }
        if (mThemeContent != null) {
            setupRecyclerView();
        } else {
            loadData();
        }
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
        mAdapter = new HomeAdapter(mContext);
        mAdapter.setData(mThemeContent.stories);
        setRecyclerHeader(mAdapter);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
    }

    private void setRecyclerHeader(HomeAdapter mAdapter) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_header_common_fragment, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        Glide.with(mContext).load(mThemeContent.image).centerCrop().into(imageView);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(mThemeContent.description);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.editor);

        for (int i = 0; i < mThemeContent.editors.size(); i++) {
            View header = LayoutInflater.from(getContext()).inflate(R.layout.item_headerview, linearLayout, false);
            SimpleDraweeView headerView = (SimpleDraweeView) header.findViewById(R.id.header_view);
            headerView.setImageURI(Uri.parse(mThemeContent.editors.get(i).avatar));
            linearLayout.addView(header);
        }
        mAdapter.setHeaderView(view);
    }

    @Override
    public void onItemClick(int position, View itemView, int Id) {
        if (mThemeContent.stories.get(position).images != null
                && mThemeContent.stories.get(position).images.size() != 0) {
            NewsContent.StartActivity(getActivity(), Id);
        } else {
            ThemeContentActivity.StartActivity(getActivity(), Id);
        }
    }
}
