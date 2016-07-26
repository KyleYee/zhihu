package com.zhihu.kyleyee.myapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by yunnnn on 2016/7/26.
 */
public class BaseFragment extends Fragment {
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = getContentView(inflater, container);
        ButterKnife.bind(this, rootView);
        init(inflater, rootView, savedInstanceState);
        return rootView;
    }

    protected void init(LayoutInflater inflater, View rootView, Bundle savedInstanceState) {

    }

    protected int getContentViewLayoutId() {
        return 0;
    }

    private View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getContentViewLayoutId(), container, false);
    }
}
