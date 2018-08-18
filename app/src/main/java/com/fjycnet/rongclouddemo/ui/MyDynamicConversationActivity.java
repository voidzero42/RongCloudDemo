package com.fjycnet.rongclouddemo.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fjycnet.rongclouddemo.R;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * 会话界面
 * <p>
 * http://www.rongcloud.cn/docs/android.html#integration_conversation_config
 */
public class MyDynamicConversationActivity extends AppCompatActivity {
    //目标 Id
    private String mTargetId;
    //会话类型
    private Conversation.ConversationType mConversationType;
    private String mTargetIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_conversation);
        getIntentData();
        initConversationFragment();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mTargetId = intent.getData().getQueryParameter("targetId");
        mTargetIds = intent.getData().getQueryParameter("targetIds");
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase());
    }

    private void initConversationFragment() {

       /* 新建 ConversationFragment 实例，通过 setUri() 设置相关属性*/
        ConversationFragment fragment = new ConversationFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);

        /* 加载 ConversationFragment */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.conversation, fragment);
        transaction.commit();
    }
}
