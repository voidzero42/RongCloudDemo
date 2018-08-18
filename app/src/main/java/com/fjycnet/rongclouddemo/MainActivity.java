package com.fjycnet.rongclouddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 官方文档:
 * <p>
 * Android SDK 开发指南 - 融云 RongCloud
 * http://www.rongcloud.cn/docs/android.html
 * <p>
 * 预下载内容
 * http://www.rongcloud.cn/docs/android.html#prepare_download
 * <p>
 * 下载 - 融云即时通讯云，iOS下载_Android下载_SDK下载
 * http://www.rongcloud.cn/downloads
 * <p>
 * 源码:sealtalk/sealtalk-android
 * https://github.com/sealtalk/sealtalk-android
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private TextView tvConnStatus;
    /**
     * 暂时从融云平台的API接口获取token
     */
    private final String rongToken = "bBqfdQu8+OACs4EP1YynMtYVA0ZGn0MhZXQ2jZpWm2z+VEIa3F3jgjpaDMPlPSaTqYwLvXHe/1SHJLgJ3m1BRQ==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);
        tvConnStatus = findViewById(R.id.tvConnStatus);
        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                connectRongImServer(rongToken);
            }
        });
        findViewById(R.id.btnDisconnect).setOnClickListener();
        setConnectionStatusListener();
    }

    //设置状态监听器
    private void setConnectionStatusListener() {
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                switch (connectionStatus) {

                    case CONNECTED://连接成功。
                        Log.e("rongclouddemo app", "连接成功");
                        break;

                    case DISCONNECTED://断开连接。
                        Log.e("rongclouddemo app", "断开连接");
//                String rong_token = SharedPreferencesUtils.getString(MyApplication.sContext, "rong_token", "");
                        //链接融云
//                connect(rong_token);
                        break;

                    case CONNECTING://连接中。
                        Log.e("rongclouddemo app", "连接中");
                        break;

                    case NETWORK_UNAVAILABLE://网络不可用。
                        Log.e("YCS", "网络不可用");
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT://TODO 用户账户在其他设备登录，本机会被踢掉线,一般做重新登录操作
                        Log.e("YCS", "用户账户在其他设备登录");
//                        new Handler(Looper.getMainLooper())
//                                .post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        IntentHelper.jumpWithExtra(mContext, LoginSelectActivity.class, "TYPE", "RELOGIN", Intent.FLAG_ACTIVITY_NEW_TASK |
//                                                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                                    }
//                                });
                        break;
                }
            }
        });
    }

    /**
     * 连接融云服务器
     */
    private void connectRongImServer(String rongToken) {
        RongIM.connect(rongToken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {//TODO 需要重新获取Token
                Log.e("rongclouddemo app", "onTokenIncorrect");
                tvResult.setText("onTokenIncorrect,需要重新获取Token");
            }

            @Override
            public void onSuccess(String s) {
                Log.e("rongclouddemo app", "连接成功");
                tvResult.setText("连接成功");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("rongclouddemo app", "连接失败");
                tvResult.setText("连接失败");
            }
        });
    }
}
