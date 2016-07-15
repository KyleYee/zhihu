package com.zhihu.kyleyee.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * 最新消息
 * Created by kyleYee on 2016/7/3.
 */
public class New implements Serializable {

    private static final long serialVersionUID = 4070510273198935460L;
    public String date;
    public List<Stories> stories;
    public List<Stories> top_stories;


}
