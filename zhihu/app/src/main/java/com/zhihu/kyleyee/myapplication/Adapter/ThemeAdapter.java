package com.zhihu.kyleyee.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.model.Stories;

import java.util.List;

import butterknife.Bind;

/**
 * 主题日报列表适配器
 * Created by yunnnn on 2016/7/26.
 */
public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

    private Context mContext;
    private List<Stories> mData;

    public ThemeAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Stories> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_cardview, parent, true));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_home_text)
        TextView des;
        @Bind(R.id.card_home_image)
        SimpleDraweeView image;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
