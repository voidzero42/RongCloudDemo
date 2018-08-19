package com.fjycnet.rongclouddemo.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fjycnet.rongclouddemo.R;
import com.fjycnet.rongclouddemo.list.MyDynamicConversationListActivity;
import com.fjycnet.rongclouddemo.ui.SetUserIdActivity;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

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
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvInit;
    private TextView tvResult;
    private TextView tvConnStatus;
    private TextView tvToken;
    private Button btnJumpDynamicConversationList;
    /**
     * 暂时从融云平台的API接口获取token
     */
    private String rongToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //设置连接状态监听器
        setConnectionStatusListener();
    }

    /**
     * 初始化融云
     */
    private void initRongCloud() {
        // RongIMClient.init(this); 区别是什么?
        Toast.makeText(this, "初始化融云SDK", Toast.LENGTH_SHORT).show();
        RongIM.init(this);
        tvInit.setText("融云SDK已初始化");
    }

    /**
     * 连接融云服务器
     * <p>
     * 融云控制台API调用 https://developer.rongcloud.cn/apitool
     */
    private void connectRongServer() {
        // 从服务端获取融云token (暂时先用控制台的API获取)
        //130的token
        rongToken = "V2NbHPSo4I/NST2BCzRGfdYVA0ZGn0MhZXQ2jZpWm2z+VEIa3F3jgi5GudbepRJLaelFyw1YmCmHJLgJ3m1BRQ==";
        tvToken.setText("融云token = " + rongToken);
        //129的token
//        rongToken = "1x3bPSUKSZSdiqy+SsioLdYVA0ZGn0MhZXQ2jZpWm2z+VEIa3F3jgk1t/aMGe18FeS4mYztEZ+GHJLgJ3m1BRQ==";

        //静态函数
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

    /**
     * 断开融云连接
     */
    private void disconnectRongServer() {
        tvResult.setText("");
        RongIM.getInstance().disconnect();
    }

    /**
     * 设置融云连接状态监听器
     * TODO: 需要放到Application里,监听全局
     */
    private void setConnectionStatusListener() {
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                switch (connectionStatus) {

                    case CONNECTED://连接成功。
                        Log.e("rongclouddemo app", "连接成功");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "融云服务器连接成功", Toast.LENGTH_SHORT).show();
                                tvConnStatus.setText("RongIM onChanged 连接成功");
                            }
                        });
                        break;

                    case DISCONNECTED://断开连接。
                        Log.e("rongclouddemo app", "断开连接");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "融云服务器断开连接", Toast.LENGTH_SHORT).show();
                                tvConnStatus.setText("RongIM onChanged 断开连接");
                            }
                        });
                        break;
                    case CONNECTING://连接中。
                        Log.e("rongclouddemo app", "连接中");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "融云服务器连接中", Toast.LENGTH_SHORT).show();
                                tvConnStatus.setText("RongIM onChanged 连接中");
                            }
                        });
                        break;

                    case NETWORK_UNAVAILABLE://网络不可用。
                        Log.e("YCS", "网络不可用");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                                tvConnStatus.setText("RongIM onChanged 网络不可用");
                            }
                        });

                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT://TODO 用户账户在其他设备登录，本机会被踢掉线,一般做重新登录操作
                        Log.e("YCS", "用户账户在其他设备登录");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "用户账户在其他设备登录", Toast.LENGTH_SHORT).show();
                                tvConnStatus.setText("RongIM onChanged 用户账户在其他设备登录,请重新登录");
                            }
                        });
                        break;
                }
            }
        });
    }


    /**
     * 打开静态配置的聊天列表
     * <p>
     * 文档地址:
     * http://www.rongcloud.cn/docs/android.html#integration_conversation_start
     * http://www.rongcloud.cn/docs/android.html#other_fragment
     */
    private void jumpStaticConversationListActivity() {
//        Intent intent = new Intent();
//        intent.setClass(MainActivity.this, MyStaticConversationListActivity.class);
//        startActivity(intent);
        Map<String, Boolean> map = new HashMap<>();
        map.put(Conversation.ConversationType.PRIVATE.getName(), false); // 会话列表需要显示私聊会话, 第二个参数 true 代表私聊会话需要聚合显示
        map.put(Conversation.ConversationType.GROUP.getName(), false);  // 会话列表需要显示群组会话, 第二个参数 false 代表群组会话不需要聚合显示
        try {
            RongIM.getInstance().startConversationList(getApplicationContext(), map);
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
            tvInit.setText("请先初始化融云SDK");
            Toast.makeText(this, "请先初始化融云SDK", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开动态配置的聊天列表
     */
    private void jumpDynamicConversationList() {
        Intent intent = new Intent();
        intent.setClass(this, MyDynamicConversationListActivity.class);
        startActivity(intent);
    }

    /**
     * 打开动态配置的会话界面
     */
    private void jumpSetConversation() {
        startActivity(new Intent(this, SetUserIdActivity.class));
    }

    private void initView() {
        tvInit = findViewById(R.id.tvInit);
        tvResult = findViewById(R.id.tvResult);
        tvConnStatus = findViewById(R.id.tvConnStatus);
        tvToken = findViewById(R.id.tvToken);
        btnJumpDynamicConversationList = findViewById(R.id.btnJumpDynamicConversationList);
        findViewById(R.id.btnInit).setOnClickListener(this);
        findViewById(R.id.btnConnect).setOnClickListener(this);
        findViewById(R.id.btnDisconnect).setOnClickListener(this);
        findViewById(R.id.btnJumpConversationList).setOnClickListener(this);
        findViewById(R.id.btnJumpSetConversation).setOnClickListener(this);
        btnJumpDynamicConversationList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInit:
                initRongCloud();//初始化融云SDK
                break;
            case R.id.btnConnect:
                connectRongServer();//连接融云服务端
                break;
            case R.id.btnDisconnect:
                disconnectRongServer();//断开连接
                break;
            case R.id.btnJumpConversationList:
                jumpStaticConversationListActivity();//跳转到静态配置的会话列表
                break;
            case R.id.btnJumpDynamicConversationList:
                jumpDynamicConversationList();//打开动态配置的聊天列表
                break;
            case R.id.btnJumpSetConversation:
                jumpSetConversation();//配置会话界面
                break;
        }
    }
}
