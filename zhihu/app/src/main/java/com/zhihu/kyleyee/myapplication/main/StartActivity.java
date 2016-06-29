package com.zhihu.kyleyee.myapplication.main;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.apiManager.ApiManager;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;
import com.zhihu.kyleyee.myapplication.model.StartModel;

import java.util.Objects;

import butterknife.Bind;

/**
 * 欢迎页面
 *
 * @author Kyle yee
 * @create 2016/6/24
 */
public class StartActivity extends BaseActivity {

    @Bind(R.id.start_txt_text)
    TextView mStart;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity.startMainActivity(StartActivity.this);
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_start;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        ApiManager.getInstance().getStartImg(new ApiManager.ResultCallBack() {
            @Override
            public void onTaskSuccess(StartModel data) {
                mStart.setText(data.getText());
                Uri uri = Uri.parse(data.getImg());
                SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
                draweeView.setImageURI(uri);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                }, 7000);


            }

            @Override
            public void onError(Objects error) {

            }

            @Override
            public void onFinal(Objects data) {

            }
        });

    }
}
