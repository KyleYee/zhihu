package com.zhihu.kyleyee.myapplication.Manager;

import com.zhihu.kyleyee.myapplication.model.New;
import com.zhihu.kyleyee.myapplication.model.Start;
import com.zhihu.kyleyee.myapplication.model.Themes;
import com.zhihu.kyleyee.myapplication.model.ThemesList;
import com.zhihu.kyleyee.myapplication.model.Version;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
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

    @GET("news/{before}")
    Call<New> getNewBefore(@Path("before") int before);

    @GET("themes")
    Call<Themes> getThemes();

    @GET("theme/{id}")
    Call<Objects> getThemeContent(@Path("id") int id);
}
