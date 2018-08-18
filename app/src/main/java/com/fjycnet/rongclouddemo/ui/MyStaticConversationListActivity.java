package com.fjycnet.rongclouddemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fjycnet.rongclouddemo.R;

/**
 * 会话列表
 * <p>
 * 配置 intent-filter：
 * <p>
 * 融云 SDK 是通过隐式调用的方式来实现界面跳转的。
 * 因此您需要在 AndroidManifest.xml 中，
 * 您的会话列表 Activity 下面配置 intent-filter，
 * 其中，android:host 是您应用的包名，
 * 需要手动修改，其他请保持不变。
 */
public class MyStaticConversationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_conversation_list);
    }
}
