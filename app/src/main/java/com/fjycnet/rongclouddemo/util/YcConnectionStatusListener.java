package com.woai.thirdsdk.rong;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.woai.common.util.IntentHelper;
import com.woai.vichat.login.act.LoginActivity;

import io.rong.imlib.RongIMClient;


/**
 * Created by McGrady on 2017/3/2.
 */

public class YcConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

    private Context mContext;

    public YcConnectionStatusListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

        switch (connectionStatus) {

            case CONNECTED://连接成功。
                Log.e("YCS", "连接成功");
                break;

            case DISCONNECTED://断开连接。
                Log.e("YCS", "断开连接");
//                String rong_token = SharedPreferencesUtils.getString(MyApplication.sContext, "rong_token", "");
                //链接融云
//                connect(rong_token);
                break;

            case CONNECTING://连接中。
                Log.e("YCS", "连接中");
                break;

            case NETWORK_UNAVAILABLE://网络不可用。
                Log.e("YCS", "网络不可用");
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                Log.e("YCS", "用户账户在其他设备登录");
                new Handler(Looper.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                IntentHelper.jumpWithExtra(mContext, LoginActivity.class, "TYPE", "RELOGIN", Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            }
                        });
                break;
        }
    }

//    /**
//     * 连接融云
//     *
//     * @param token
//     */
//    ic_private void connect(String token) {
//        RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//            @Override
//            public void onTokenIncorrect() {
//                final String photoUrl = SharedPreferencesUtils.getString(MyApplication.sContext, "photoUrl", "");
//                final String userId = SharedPreferencesUtils.getString(MyApplication.sContext, "userId", "");
//                final String nikename = SharedPreferencesUtils.getString(MyApplication.sContext, "nikename", "");
//                ApiConstant.getRongToken(MyApplication.sContext, ConnectionListener.this, userId, nikename, photoUrl);
//            }
//
//            @Override
//            public void onSuccess(String userid) {
//                final String photoUrl = SharedPreferencesUtils.getString(MyApplication.sContext, "photoUrl", "");
//                final String userId = SharedPreferencesUtils.getString(MyApplication.sContext, "userId", "");
//                final String nikename = SharedPreferencesUtils.getString(MyApplication.sContext, "nikename", "");
//                if (RongIM.getInstance() != null) {
//                    RongIM.getInstance().setCurrentUserInfo(new UserInfoBean(userId, nikename, Uri.parse(photoUrl)));
//                }
//                RongIM.getInstance().setMessageAttachedUserInfo(true);
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                final String photoUrl = SharedPreferencesUtils.getString(MyApplication.sContext, "photoUrl", "");
//                final String userId = SharedPreferencesUtils.getString(MyApplication.sContext, "userId", "");
//                final String nikename = SharedPreferencesUtils.getString(MyApplication.sContext, "nikename", "");
//                ApiConstant.getRongToken(MyApplication.sContext, ConnectionListener.this, userId, nikename, photoUrl);
//            }
//        });
//    }
//
//    @Override
//    public void onSuccess(String apiUrl, String t) {
//        if (ApiConstant.RONG_TOKEN.equals(apiUrl)) {
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//            LoginUserData userInfo = gson.fromJson(t, LoginUserData.class);
//            if (userInfo.isSucceed()) {
//                if (userInfo.getData() != null) {
//                    String token = userInfo.getData().getToken();
//                    if (!TextUtils.isEmpty(token)) {
//                        SharedPreferencesUtils.setString(MyApplication.sContext, "rong_token", token);
//                        connect(token);
//                    }
//                }
//            } else {
//                Toast.makeText(MyApplication.sContext, userInfo.getMessage(), Toast.LENGTH_SHORT).show();
//                //用户超时清除状态
//                if (userInfo.getCode() == 1000) {
//                    MyApplication.sContext.startActivity(new Intent(MyApplication.sContext, LoginActivity.class));
//                    EventBus.getDefault().post(new MessageEvent("overtime"));
//                    Utils.clearLogin(MyApplication.sContext,null);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onError(String apiUrl, String msg) {
//
//    }
}
