package com.zhihu.kyleyee.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * 最新消息
 * Created by kyleYee on 2016/7/3.
 */
public class New implements Serializable {
    private String date;
    private List<Stories> stories;
    private List<Stories> top_stories;
}
