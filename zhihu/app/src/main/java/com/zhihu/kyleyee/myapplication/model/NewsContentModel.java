package com.zhihu.kyleyee.myapplication.model;

import java.io.Serializable;
import java.util.List;

/**
 * 消息内容
 * Created by kyleYee on 2016/7/20.
 */
public class NewsContentModel implements Serializable {
    private static final long serialVersionUID = -9105697414366111016L;

    public String body;//HTML 格式的新闻
    public String image_source;//图片的内容提供方。为了避免被起诉非法使用图片，在显示图片时最好附上其版权信息。
    public String title;//新闻标题
    public String image;//获得的图片同 最新消息 获得的图片分辨率不同。这里获得的是在文章浏览界面中使用的大图。
    public String share_url;//供在线查看内容与分享至 SNS 用的 URL
    public List<Recommenders> recommenders;//这篇文章的推荐者
    public String ga_prefix;//供 Google Analytics 使用
    public Section section;//栏目的信息
    public int type;//新闻的类型
    public int id;//新闻的 id
    public List<String> css;//供手机端的 WebView(UIWebView) 使用

}
