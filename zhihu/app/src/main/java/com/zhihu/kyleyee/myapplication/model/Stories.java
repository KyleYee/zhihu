package com.zhihu.kyleyee.myapplication.model;

import java.util.List;

/**
 * 当日新闻
 * Created by kyleYee on 2016/7/3.
 */
public class Stories {
    private String title;//新闻标题
    private String ga_prefix;//供 Google Analytics 使用
    private int type;//
    private int id;//url 与 share_url 中最后的数字（应为内容的 id）
    private String image;//顶部ViewPager图片
    private List<String> images;//列表图片地址 可能为空
}
