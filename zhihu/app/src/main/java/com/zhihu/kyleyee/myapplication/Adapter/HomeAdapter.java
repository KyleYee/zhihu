package com.zhihu.kyleyee.myapplication.Adapter;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.model.New;
import com.zhihu.kyleyee.myapplication.model.Stories;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页ViewAdapter
 * Created by yunnnn on 2016/7/15.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_COMMENT = 0;

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView, int Id);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private New mNewData;//最新消息数据
    private Context mContext;
    private LayoutInflater mInflater;
    private View mHeaderView;

    public HomeAdapter(Context context, New newData) {
        this.mNewData = newData;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setHeaderView(View view) {
        mHeaderView = view;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_COMMENT;
        if (position == 0) return TYPE_HEADER;
        return TYPE_COMMENT;
    }

    @Override
    public HomeAdapter.HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new HomeAdapter.HomeHolder(mHeaderView);
        View v = mInflater.inflate(R.layout.item_home_cardview, parent, false);
        return new HomeAdapter.HomeHolder(v);
    }

    @Override
    public void onBindViewHolder(final HomeHolder holder, final int position) {
        if (mHeaderView != null && getItemViewType(position) == TYPE_HEADER) return;
        final Stories stories = mNewData.stories.get(position);
        holder.image.setImageURI(Uri.parse(stories.images.get(0)));
        holder.des.setText(stories.title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(getPosition(position), v, stories.id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? (mNewData.stories.size() + 1) : mNewData.stories.size();
    }

    public int getPosition(int position) {
        return mHeaderView == null ? position : position + 1;
    }

    class HomeHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_home_text)
        TextView des;
        @Bind(R.id.card_home_image)
        SimpleDraweeView image;

        public HomeHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            ButterKnife.bind(this, itemView);
        }
    }
}
