package com.zhihu.kyleyee.myapplication.apiManager;

import com.zhihu.kyleyee.myapplication.model.StartModel;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kyleYee on 2016/6/28.
 */
public interface ApiService {

    @GET("start-image/1080*1776")
    Call<StartModel> getStartImg();
}
