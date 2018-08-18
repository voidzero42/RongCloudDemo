package com.fjycnet.rongclouddemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fjycnet.rongclouddemo.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class MessageApiActivity extends AppCompatActivity {

    private Button btnSendSingleMsg;
    private EditText etSingMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_api);
        initView();
        setButtonListener();
    }

    private void sendSingleMsg() {
        final String msg = etSingMsg.getText().toString();
        // 构造 TextMessage 实例
        TextMessage myTextMessage = TextMessage.obtain(msg);

        /* 生成 Message 对象。
         * "7127" 为目标 Id。根据不同的 conversationType，可能是用户 Id、群组 Id 或聊天室 Id。
         * Conversation.ConversationType.PRIVATE 为私聊会话类型，根据需要，也可以传入其它会话类型，如群组。
         */
        Message myMessage = Message.obtain("130", Conversation.ConversationType.PRIVATE, myTextMessage);
        sendMyMessage(myMessage, null, null, null);
    }

    /**
     * <p>发送消息。
     * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
     * 中的方法回调发送的消息状态及消息体。</p>
     *
     * @param message     将要发送的消息体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
     *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
     */
    private void sendMyMessage(Message message, String pushContent, String pushData, IRongCallback.ISendMessageCallback callback) {

        RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
            }
        });
    }

    private void initView() {
        btnSendSingleMsg = findViewById(R.id.btnSendSingleMsg);
        etSingMsg = findViewById(R.id.etSingleMsg);
    }

    private void setButtonListener() {
        btnSendSingleMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSingleMsg();
            }
        });
    }
}
