package com.zhihu.kyleyee.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SquaringDrawable;
import com.zhihu.kyleyee.myapplication.utils.Utils;

/**
 * 圆形头像
 * Created by yunnnn on 2016/7/27.
 */
public class CircleHeaderView extends ImageView {
    private Paint mPaint;
    private int mRadius;
    private int mReactWith;

    private Context mContext;

    public CircleHeaderView(Context context) {
        super(context, null);
    }

    public CircleHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //由于是画圆和正方形，所以不需要知道宽和高的模式。
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        mReactWith = Math.min(width, height);
        width = mReactWith;
        height = mReactWith;
        mRadius = mReactWith / 2;

        setMeasuredDimension(width, height);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    /*    Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        SquaringDrawable bitmapDrawable = (SquaringDrawable) drawable;
//        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        if (bitmap == null)
            return;

        canvas.drawBitmap(bitmap, mReactWith / 2, mReactWith / 2, null);
        mPaint.setColor(Color.WHITE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawCircle(mReactWith / 2, mReactWith / 2, mRadius - 20, mPaint);
        mPaint.setXfermode(null);*/
    }
}
