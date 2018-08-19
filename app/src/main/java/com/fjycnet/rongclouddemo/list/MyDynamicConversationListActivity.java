package com.fjycnet.rongclouddemo.list;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fjycnet.rongclouddemo.R;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * 动态配置的会话列表
 *
 * @author 绯若虚无
 */
public class MyDynamicConversationListActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvMsg;
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
        Toast.makeText(this, "添加接收消息监听器", Toast.LENGTH_SHORT).show();
        if (onReceiveMessageListener == null) {
            onReceiveMessageListener = new RongIMClient.OnReceiveMessageListener() {
                /**
                 * 收到消息的处理。
                 *
                 * @param message 收到的消息实体。
                 * @param left    剩余未拉取消息数目。
                 * @return 收到消息是否处理完成，true 表示自己处理铃声和后台通知，false 走融云默认处理方式。
                 */
                @Override
                public boolean onReceived(final Message message, final int left) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyDynamicConversationListActivity.this, "Message=" + message.getContent().toString() + ",left=" + left, Toast.LENGTH_SHORT).show();
                            tvMsg.setText("Message=" + message.getContent().toString() + ",left=" + left);
                        }
                    });
                    return isRecvMsg;
                }
            };
            RongIM.setOnReceiveMessageListener(onReceiveMessageListener);
        }
    }

    /**
     * 自己处理铃声和后台通知
     */
    private void customHanleRing() {
        Toast.makeText(this, "自己处理铃声和后台通知", Toast.LENGTH_SHORT).show();
        isRecvMsg = true;
    }

    /**
     * 自己处理铃声和后台通知
     */
    private void defaultHanleRing() {
        Toast.makeText(this, "让融云默认处理铃声和后台通知", Toast.LENGTH_SHORT).show();
        isRecvMsg = false;
    }

    private void initView() {
        tvMsg = findViewById(R.id.tvMsg);
        findViewById(R.id.btnAddMsgListener).setOnClickListener(this);
        findViewById(R.id.btnCustomHandleRing).setOnClickListener(this);
        findViewById(R.id.btnDefaultHandleRing).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddMsgListener:
                addReceiveMessageListener();
                break;
            case R.id.btnCustomHandleRing:
                customHanleRing();
                break;
            case R.id.btnDefaultHandleRing:
                defaultHanleRing();
                break;
        }
    }
}
