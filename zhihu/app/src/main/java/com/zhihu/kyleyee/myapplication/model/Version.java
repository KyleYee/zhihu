package com.zhihu.kyleyee.myapplication.model;

/**
 * 版本号
 * Created by kyleYee on 2016/7/3.
 */
public class Version {
    private int status;//0 代表软件为最新版本，1 代表软件需要升级
    private String latest;// 软件最新版本的版本号（数字的第二段会比最新的版本号低 1）
    private String msg;//仅出现在软件需要升级的情形下，提示用户升级软件的对话框中显示的消息
}
