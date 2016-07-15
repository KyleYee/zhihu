package com.zhihu.kyleyee.myapplication.model;

import java.io.Serializable;

/**
 * 欢迎页面
 * Created by kyleYee on 2016/6/29.
 */
public class Start  implements Serializable{
    private static final long serialVersionUID = -2086655784066657141L;
    private String text;
    private String img;

    public String getImg() {
        return img;
    }

    public String getText() {
        return text;
    }
}
