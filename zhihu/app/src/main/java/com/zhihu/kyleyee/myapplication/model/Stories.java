package com.zhihu.kyleyee.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * 当日新闻
 * Created by kyleYee on 2016/7/3.
 */
public class Stories implements Serializable{
    private static final long serialVersionUID = 102451236432309360L;
    public String title;//新闻标题
    public String ga_prefix;//供 Google Analytics 使用
    public int type;//
    public int id;//url 与 share_url 中最后的数字（应为内容的 id）
    public String image;//顶部ViewPager图片
    public List<String> images;//列表图片地址 可能为空
}
