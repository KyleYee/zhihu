package com.zhihu.kyleyee.myapplication.modul;

import android.content.Intent;
import android.os.Bundle;

import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;
import com.zhihu.kyleyee.myapplication.manager.ApiManager;
import com.zhihu.kyleyee.myapplication.model.NewsContentModel;

/**
 * 新闻内容显示
 * Created by kyleYee on 2016/7/20.
 */
public class NewsContent extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_news_content;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
       Intent intent =  getIntent();
        int id = intent.getIntExtra("id", 0);
        ApiManager.getInstance().getNewsContent(id, new ApiManager.ResultCallBack() {
            @Override
            public void onTaskSuccess(Object data) {
                NewsContentModel model = (NewsContentModel) data;
            }

            @Override
            public void onError(Object error) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
