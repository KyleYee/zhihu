package com.zhihu.kyleyee.myapplication.modul;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;
import com.zhihu.kyleyee.myapplication.manager.ApiManager;
import com.zhihu.kyleyee.myapplication.model.NewsContentModel;

import butterknife.Bind;

/**
 * 新闻内容显示
 * Created by kyleYee on 2016/7/20.
 */
public class NewsContent extends BaseActivity {

    @Bind(R.id.webView)
    WebView mWebView;
    //获取下来的数据
    private NewsContentModel mNewsContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_news_content;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        ApiManager.getInstance().getNewsContent(id, new ApiManager.ResultCallBack() {
            @Override
            public void onTaskSuccess(Object data) {
                mNewsContent = (NewsContentModel) data;
                if (mNewsContent == null) {
                    return;
                }
                setWebView();
            }

            @Override
            public void onError(Object error) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 设置webView
     */
    private void setWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + mNewsContent.body + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }
}
