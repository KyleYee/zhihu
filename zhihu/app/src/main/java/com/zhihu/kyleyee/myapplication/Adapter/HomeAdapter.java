package com.zhihu.kyleyee.myapplication.adapter;

import android.content.Context;
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

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页ViewAdapter
 * Created by yunnnn on 2016/7/15.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_COMMENT = 0;

    private int mHeadCount = 0;

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView, int Id);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private List<Stories> mData;
    private Context mContext;
    private LayoutInflater mInflater;
    private View mHeaderView;

    public HomeAdapter(Context context ){
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setHeaderView(View view) {
        mHeaderView = view;
        mHeadCount = 1;
        notifyItemInserted(0);
    }

    public void setData(List<Stories> data) {
        this.mData = data;
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
        if (mHeaderView != null && getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final Stories stories = mData.get(position - mHeadCount);
        if (stories.images != null && stories.images.size() != 0) {
            holder.image.setImageURI(Uri.parse(stories.images.get(0)));
            holder.image.setVisibility(View.VISIBLE);
        } else {
            holder.image.setVisibility(View.GONE);
        }
        holder.des.setText(stories.title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position - mHeadCount, v, stories.id);
                }
            }
        });
        //设置时间
        setDate(stories, holder, position);
    }

    //设置时间
    private void setDate(Stories stories, HomeHolder holder, int position) {
        if (stories.date != null) {
            if (position == mHeadCount) {
                holder.date.setText("今日热文");
            } else {
                String date = stories.date;
                String year = date.substring(0, 4);
                String month = date.substring(4, 6);
                String day = date.substring(6, 8);
                final Calendar c = Calendar.getInstance();
                c.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                String way = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
                if ("1".equals(way)) {
                    way = "天";
                } else if ("2".equals(way)) {
                    way = "一";
                } else if ("3".equals(way)) {
                    way = "二";
                } else if ("4".equals(way)) {
                    way = "三";
                } else if ("5".equals(way)) {
                    way = "四";
                } else if ("6".equals(way)) {
                    way = "五";
                } else if ("7".equals(way)) {
                    way = "六";
                }
                holder.date.setText(month + "月" + day + "日" + "  " + "星期" + way);
            }
            holder.date.setVisibility(View.VISIBLE);
        } else {
            holder.date.setText("");
            holder.date.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderView != null ? (
                mData.size() + mHeadCount) : mData.size();
    }

    class HomeHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_home_text)
        TextView des;
        @Bind(R.id.card_home_image)
        SimpleDraweeView image;
        @Bind(R.id.date)
        TextView date;

        public HomeHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) return;
            ButterKnife.bind(this, itemView);
        }
    }
}
