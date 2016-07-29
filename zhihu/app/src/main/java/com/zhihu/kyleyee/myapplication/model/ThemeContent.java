package com.zhihu.kyleyee.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * 主题内容
 * Created by kyleYee on 2016/7/3.
 */
public class ThemeContent implements Serializable {
    private static final long serialVersionUID = 1434595093415641905L;
    public List<Stories> stories;// 该主题日报中的文章列表
    public String description;//该主题日报的介绍
    public String background;//该主题日报的背景图片（大图）
    public int color;
    public String name;//该主题日报的名称
    public String image;// 背景图片的小图版本
    public String image_source;//图像的版权信息
    public List<Editor> editors;//该主题日报的编辑（『用户推荐日报』中此项的指是一个空数组，在 App 中的主编栏显示为『许多人』，点击后访问该主题日报的介绍页面，请留意）
}
