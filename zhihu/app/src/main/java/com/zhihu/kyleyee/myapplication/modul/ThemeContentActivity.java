package com.zhihu.kyleyee.myapplication.modul;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;
import com.zhihu.kyleyee.myapplication.main.AppConstants;
import com.zhihu.kyleyee.myapplication.manager.ApiManager;
import com.zhihu.kyleyee.myapplication.model.NewsContentModel;

import butterknife.Bind;

/**
 * 新闻内容显示
 * Created by kyleYee on 2016/7/20.
 */
public class ThemeContentActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    //获取下来的数据
    private NewsContentModel mNewsContent;
    private RequestQueue mQueue;

    public static void StartActivity(Activity context, int Id) {
        Intent intent = new Intent(context, ThemeContentActivity.class);
        intent.putExtra("id", Id);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(AppConstants.SAVE_INSTANCE_NEW_CONTENT_DATA, mNewsContent);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_common_content;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        if (savedInstanceState != null) {
            mNewsContent = (NewsContentModel) savedInstanceState
                    .getSerializable(AppConstants.SAVE_INSTANCE_NEW_CONTENT_DATA);

        }
        if (mNewsContent != null) {
            setToolBar();
            setWebView();
        } else {
            loadData();
        }
    }

    private void loadData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        mQueue = Volley.newRequestQueue(getApplicationContext());

        ApiManager.getInstance().getNewsContent(id, new ApiManager.ResultCallBack() {
            @Override
            public void onTaskSuccess(Object data) {
                mNewsContent = (NewsContentModel) data;
                if (mNewsContent == null) {
                    return;
                }
                setToolBar();
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
     * 设置toolbar
     */
    private void setToolBar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, mNewsContent.css.get(0), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String css = response;
                if (css != null) {
                    String html = "<html><head><style type=\"text/css\">" + css + "</style></head><body>" + mNewsContent.body + "</body></html>";
                    html = html.replace("<div class=\"img-place-holder\">", "");
                    mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(stringRequest);
    }


}
