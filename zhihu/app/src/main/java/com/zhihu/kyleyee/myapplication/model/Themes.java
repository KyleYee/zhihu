package com.zhihu.kyleyee.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * 主题日报列表查看
 * Created by kyleYee on 2016/7/3.
 */
public class Themes implements Serializable {
    private static final long serialVersionUID = -6795449449816820856L;
    public int limit;
    public List<ThemesList> subscribed;//已订阅条目
    public List<ThemesList> others;//其他条目

}
