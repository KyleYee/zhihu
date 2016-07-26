package com.zhihu.kyleyee.myapplication.manager;

import com.zhihu.kyleyee.myapplication.model.New;
import com.zhihu.kyleyee.myapplication.model.NewsContentModel;
import com.zhihu.kyleyee.myapplication.model.Start;
import com.zhihu.kyleyee.myapplication.model.ThemeContent;
import com.zhihu.kyleyee.myapplication.model.Themes;
import com.zhihu.kyleyee.myapplication.model.ThemesList;
import com.zhihu.kyleyee.myapplication.model.Version;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by kyleYee on 2016/6/28.
 */
public interface ApiService {

    @GET("start-image/1080*1776")
    Call<Start> getStartImg();

    @GET("version/android/2.3.0")
    Call<Version> getVersion();

    @GET("news/latest")
    Call<New> getNewList();

    @GET("news/before/{before}")
    Call<New> getNewBefore(@Path("before") String before);

    @GET("themes")
    Call<Themes> getThemes();

    @GET("theme/{id}")
    Call<ThemeContent> getThemeContent(@Path("id") int id);

    @GET("news/{id}")
    Call<NewsContentModel> getNewsContent(@Path("id") int id);
}
