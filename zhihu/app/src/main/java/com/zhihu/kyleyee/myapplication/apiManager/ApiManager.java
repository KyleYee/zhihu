package com.zhihu.kyleyee.myapplication.apiManager;

import android.util.ArrayMap;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.zhihu.kyleyee.myapplication.model.StartModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求  采用单列模式
 * Created by kyleYee on 2016/6/24.
 */
public class ApiManager {

    private static ApiManager apiManager = null;

    public ApiManager() {
    }

    public static synchronized ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public static final String API_BASE_URL = "http://news-at.zhihu.com/api/4";

    private static okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://news-at.zhihu.com/api/4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();

    public ApiService apiService = retrofit.create(ApiService.class);

    public interface ResultCallBack{
        void onTaskSuccess(StartModel data);
        void onError(Objects error);
        void onFinal(Objects data);
    }

    public void getStartImg(final ResultCallBack callBack) {
        Call<StartModel> call = apiService.getStartImg();

        call.enqueue(new retrofit2.Callback<StartModel>() {
            @Override
            public void onResponse(Call<StartModel> call, Response<StartModel> response) {
               StartModel startModel =  response.body();
                if (response.isSuccessful()){
                    callBack.onTaskSuccess(startModel);
                }
            }

            @Override
            public void onFailure(Call<StartModel> call, Throwable t) {

            }
        });
    }
}
