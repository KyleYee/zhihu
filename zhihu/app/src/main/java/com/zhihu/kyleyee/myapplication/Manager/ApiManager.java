package com.zhihu.kyleyee.myapplication.manager;

import com.zhihu.kyleyee.myapplication.model.New;
import com.zhihu.kyleyee.myapplication.model.NewsContentModel;
import com.zhihu.kyleyee.myapplication.model.Start;
import com.zhihu.kyleyee.myapplication.model.Themes;
import com.zhihu.kyleyee.myapplication.model.Version;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求  采用单列模式
 * Created by kyleYee on 2016/6/24.
 */
public class ApiManager {
    private static volatile ApiManager apiManager;

    private ApiManager() {
    }

    public static synchronized ApiManager getInstance() {
        if (apiManager == null) {
            synchronized (ApiManager.class) {
                apiManager = new ApiManager();
            }
        }
        return apiManager;
    }

    private static okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://news-at.zhihu.com/api/4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();

    public ApiService apiService = retrofit.create(ApiService.class);

    /*************
     * 分割线上面是初始化操作，下面是具体方法操作
     *************/
    public interface ResultCallBack {
        void onTaskSuccess(Object data);

        void onError(Object error);

        void onFinish();
    }

    /**
     * 获取欢迎页面
     *
     * @param callBack
     */
    public void getStartImg(final ResultCallBack callBack) {
        Call<Start> call = apiService.getStartImg();

        call.enqueue(new retrofit2.Callback<Start>() {
            @Override
            public void onResponse(Call<Start> call, Response<Start> response) {
                if (response.isSuccessful()) {
                    Start start = response.body();
                    callBack.onTaskSuccess(start);
                } else {
                    callBack.onError("error：" + response.message());
                }
                callBack.onFinish();
            }

            @Override
            public void onFailure(Call<Start> call, Throwable t) {
                callBack.onError("error：" + t.toString());
                callBack.onFinish();
            }
        });
    }

    /**
     * 获取版本号
     */
    public void getVersion(final ResultCallBack callBack) {
        Call<Version> call = apiService.getVersion();

        call.enqueue(new retrofit2.Callback<Version>() {
            @Override
            public void onResponse(Call<Version> call, Response<Version> response) {
                if (response.isSuccessful()) {
                    Version start = response.body();
                    callBack.onTaskSuccess(start);
                } else {
                    callBack.onError("error：" + response.message());
                }
                callBack.onFinish();
            }

            @Override
            public void onFailure(Call<Version> call, Throwable t) {
                callBack.onError("error：" + t.toString());
                callBack.onFinish();
            }
        });
    }

    /**
     * 获取最新消息列表
     *
     * @param callBack
     */
    public void getNewList(final ResultCallBack callBack) {
        Call<New> call = apiService.getNewList();

        call.enqueue(new retrofit2.Callback<New>() {
            @Override
            public void onResponse(Call<New> call, Response<New> response) {
                New news = response.body();
                if (response.isSuccessful()) {
                    callBack.onTaskSuccess(news);
                } else {
                    callBack.onError("error：" + response.message());
                }
                callBack.onFinish();
            }

            @Override
            public void onFailure(Call<New> call, Throwable t) {
                callBack.onError("error：" + t.toString());
                callBack.onFinish();
            }
        });
    }

    /**
     * 获取之前的消息
     * 若果需要查询 11 月 18 日的消息，before 后的数字应为 20131119
     *
     * @param before
     * @param callBack
     */
    public void getNewBefore(String before, final ResultCallBack callBack) {
        Call<New> call = apiService.getNewBefore(before);

        call.enqueue(new Callback<New>() {
            @Override
            public void onResponse(Call<New> call, Response<New> response) {
                if (response.isSuccessful()) {
                    callBack.onTaskSuccess(response.body());
                } else {
                    callBack.onError("error：" + response.message());
                }
                callBack.onFinish();
            }

            @Override
            public void onFailure(Call<New> call, Throwable t) {
                callBack.onError("error：" + t.toString());
                callBack.onFinish();
            }
        });
    }

    /**
     * 主题日报列表查看
     *
     * @param callBack
     */
    public void getThemes(final ResultCallBack callBack) {
        Call<Themes> call = apiService.getThemes();

        call.enqueue(new Callback<Themes>() {
            @Override
            public void onResponse(Call<Themes> call, Response<Themes> response) {
                if (response.isSuccessful()) {
                    callBack.onTaskSuccess((Themes) response.body());
                }
                callBack.onError("error：" + response.message());
                callBack.onFinish();
            }

            @Override
            public void onFailure(Call<Themes> call, Throwable t) {
                callBack.onError("error：" + t.toString());
                callBack.onFinish();
            }
        });
    }

    /**
     * 获取新闻内容
     *
     * @param id
     * @param callBack
     */
    public void getNewsContent(int id, final ResultCallBack callBack) {
        Call<NewsContentModel> call = apiService.getNewsContent(id);
        call.enqueue(new Callback<NewsContentModel>() {
            @Override
            public void onResponse(Call<NewsContentModel> call, Response<NewsContentModel> response) {
                if (response.isSuccessful()) {
                    callBack.onTaskSuccess(response.body());
                }
                callBack.onError("error：" + response.message());
                callBack.onFinish();
            }

            @Override
            public void onFailure(Call<NewsContentModel> call, Throwable t) {
                callBack.onError("error：" + t.toString());
                callBack.onFinish();
            }
        });
    }

}
