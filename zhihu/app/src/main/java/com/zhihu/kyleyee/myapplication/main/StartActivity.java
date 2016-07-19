package com.zhihu.kyleyee.myapplication.main;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhihu.kyleyee.myapplication.R;
import com.zhihu.kyleyee.myapplication.Manager.ApiManager;
import com.zhihu.kyleyee.myapplication.base.BaseActivity;
import com.zhihu.kyleyee.myapplication.model.New;
import com.zhihu.kyleyee.myapplication.model.Start;

import butterknife.Bind;

/**
 * 欢迎页面
 *
 * @author Kyle yee
 * @create 2016/6/24
 */
public class StartActivity extends BaseActivity {

    public static final String NEW_BUNDLE = "new_bundle";

    @Bind(R.id.start_txt_text)
    TextView mStart;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            New newData = (New) bundle.getSerializable(NEW_BUNDLE);
            MainActivity.startMainActivity(StartActivity.this, newData);
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            finish();
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
            public void onTaskSuccess(Object data) {
                Start model = (Start) data;
                mStart.setText(model.getText());
                Uri uri = Uri.parse(model.getImg());
                SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
                draweeView.setImageURI(uri);


                ApiManager.getInstance().getNewList(new ApiManager.ResultCallBack() {
                    @Override
                    public void onTaskSuccess(Object data) {
                        New newData = (New) data;
                        if (newData != null) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(NEW_BUNDLE, newData);
                            message.setData(bundle);
                            handler.sendMessageDelayed(message, 2000);
                        }
                    }

                    @Override
                    public void onError(Object error) {
                    }

                    @Override
                    public void onFinish() {

                    }

                });

            }

            @Override
            public void onError(Object error) {

            }

            @Override
            public void onFinish() {

            }
        });

    }
}
