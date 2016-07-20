package com.zhihu.kyleyee.myapplication.model;

import java.io.Serializable;

/**
 * 主题列表主题内容列表
 * Created by kyleYee on 2016/7/3.
 */
public class ThemesList implements Serializable{
    private static final long serialVersionUID = -7944461452985978505L;
    public int color;
    public String thumbnail; //供显示的图片地址
    public String description;//主题日报的介绍
    public int id;//该主题日报的编号
    public String name;//供显示的主题日报名称

}
