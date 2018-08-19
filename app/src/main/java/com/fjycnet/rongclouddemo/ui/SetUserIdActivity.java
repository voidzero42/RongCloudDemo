package com.fjycnet.rongclouddemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fjycnet.rongclouddemo.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class SetUserIdActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUid;
    private EditText etChatTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_id);
        initView();
    }

    /**
     * 启动动态配置的会话界面
     */
    private void startDynamicConversationActivity() {
        String uid = etUid.getText().toString();
        String chatTitle = etChatTitle.getText().toString();
        if (TextUtils.isEmpty(uid)) {
            Toast.makeText(this, "请先填写UID", Toast.LENGTH_SHORT).show();
            return;
        }
        //启动会话界面
        RongIM.getInstance().startConversation(this, Conversation.ConversationType.PRIVATE, uid, chatTitle);
    }

    private void initView() {
        etUid = findViewById(R.id.etUid);
        etChatTitle = findViewById(R.id.etChatTitle);
        findViewById(R.id.btnOpenDynamicConversationActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOpenDynamicConversationActivity:
                startDynamicConversationActivity();
                break;
        }
    }
}
