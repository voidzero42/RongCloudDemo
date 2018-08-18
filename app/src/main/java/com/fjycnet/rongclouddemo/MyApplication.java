package com.fjycnet.rongclouddemo;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * APP
 * (这里只做融云IM的功能测试,不做代码封装,有需要自己封装里面的函数)
 * Created by ZeroVoid on 2018-8-14.
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initRongCloud();
        setRongImConnectionStatusListener();
    }

    /**
     * 初始化融云
     */
    private void initRongCloud() {
        // RongIMClient.init(this); 区别是什么?
        RongIM.init(this);
    }



    /**
     * 设置融云的状态监听器
     */
    private void setRongImConnectionStatusListener() {

    }
}
