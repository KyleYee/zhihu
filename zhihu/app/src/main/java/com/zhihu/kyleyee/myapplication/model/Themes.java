package com.zhihu.kyleyee.myapplication.model;

import java.io.Serializable;

/**
 * 主题日报列表查看
 * Created by kyleYee on 2016/7/3.
 */
public class Themes implements Serializable {
    private static final long serialVersionUID = -6795449449816820856L;
    private int limit;
    private ThemesList subscribed;//已订阅条目
    private ThemesList others;//其他条目

}
