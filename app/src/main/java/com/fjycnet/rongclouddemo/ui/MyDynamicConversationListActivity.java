package com.fjycnet.rongclouddemo.ui;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fjycnet.rongclouddemo.R;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class MyDynamicConversationListActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAddMsgListener;
    private Button btnCancelMsgListener;
    private boolean isRecvMsg = false;
    private RongIMClient.OnReceiveMessageListener onReceiveMessageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dynamic_conversation_list);
        initConversationFragment();
        initView();
    }

    /**
     * 动态配置 会话列表 Fragment
     */

    private void initConversationFragment() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话，该会话非聚合显示
                .build();
        fragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();
    }

    /**
     * 接收消息监听器的实现，所有接收到的消息、通知、状态都经由此处设置的监听器处理。包括私聊消息、群组消息、聊天室消息以及各种状态。
     */
    private void addReceiveMessageListener() {
        isRecvMsg = true;
        Toast.makeText(this, "添加接收消息监听器", Toast.LENGTH_SHORT).show();
        if (onReceiveMessageListener == null) {
            onReceiveMessageListener = new RongIMClient.OnReceiveMessageListener() {
                @Override
                public boolean onReceived(Message message, int i) {
                    Toast.makeText(MyDynamicConversationListActivity.this, "Message=" + message.getContent() + ",i=" + i, Toast.LENGTH_SHORT).show();
                    return isRecvMsg;
                }
            };
            RongIM.setOnReceiveMessageListener(onReceiveMessageListener);
        }
    }

    /**
     * 不再接收消息
     */
    private void cancelReceiveMessageListener() {
        Toast.makeText(this, "取消接收消息监听器", Toast.LENGTH_SHORT).show();
        isRecvMsg = false;
    }

    private void initView() {
        btnAddMsgListener = findViewById(R.id.btnAddMsgListener);
        btnAddMsgListener.setOnClickListener(this);
        btnCancelMsgListener = findViewById(R.id.btnCancelMsgListener);
        btnCancelMsgListener.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddMsgListener:
                addReceiveMessageListener();
                break;
            case R.id.btnCancelMsgListener:
                cancelReceiveMessageListener();
                break;
        }
    }
}
