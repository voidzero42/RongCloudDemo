package com.fjycnet.rongclouddemo.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;

/**
 * Created by ZeroVoid on 2018-2-27.
 */

public class RongUtil {

    public interface OnGetUserInfoProviderListener {
        UserInfo updateUserInfo();
    }

    public static void initRong(Application context, final OnGetUserInfoProviderListener listener) {
//        if (context.getApplicationInfo().packageName.equals(context.getCurProcessName(context))) {
        RongIM.init(context, "pgyu6atqpei9u");
        RongIM.setConnectionStatusListener(new YcConnectionStatusListener(context));
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                //是否接收消息
                return true;
            }
        });
        RongPushClient.init(context, "pgyu6atqpei9u");
//        RongPushClient.registerHWPush(context);

        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                return listener.updateUserInfo();
            }
        }, true);

        try {
            List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
            if (moduleList != null) {
                IExtensionModule module = null;
                for (IExtensionModule extensionModule : moduleList) {
                    if (extensionModule instanceof DefaultExtensionModule) {
                        module = extensionModule;
                        break;
                    }
                }
                RongExtensionManager.getInstance().unregisterExtensionModule(module);//注销之前的
                RongExtensionManager.getInstance().registerExtensionModule(new AddIPluginModule());//注册新的
                List<IExtensionModule> moduleList2 = RongExtensionManager.getInstance().getExtensionModules();
                Log.i("app", "moduleList.size() = " + moduleList2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String nickName = "系统消息";
//        String userID = "100000";
//        String photo = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527490397376&di=ac09d688310417e8753bc5c1feee92a2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01af985927beeeb5b3086ed47f7e57.png%401280w_1l_2o_100sh.png";
//        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userID, nickName, Uri.parse(photo)));
    }


//    /**
//     * 开关华为推送
//     *
//     * @param isOpen
//     */
//    public static void enableHuaWeiPush(Context context, boolean isOpen) {
//        HuaweiApiClient client = new HuaweiApiClient.Builder(context)
//                .addApi(HuaweiPush.PUSH_API)
//                .addConnectionCallbacks(new HuaweiApiClient.ConnectionCallbacks() {
//                    @Override
//                    public void onConnected() {
//
//                    }
//
//                    @Override
//                    public void onConnectionSuspended(int i) {
//
//                    }
//                })
//                .addOnConnectionFailedListener(new HuaweiApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//                    }
//                })
//                .build();
//        client.connect();
//
//        if (!client.isConnected()) {
//            return;
//        }
//        HuaweiPush.HuaweiPushApi.enableReceiveNormalMsg(client, isOpen);
//    }

    public static void switchNotice(Context context, boolean isEnable) {
        if (isEnable) {
            MiPushClient.enablePush(context);
//            enableHuaWeiPush(context, true);
        } else {
            MiPushClient.disablePush(context);
//            enableHuaWeiPush(context, false);
        }
//        RongIM.
    }

    public interface OnRongImConnectOkListener {
        void jumpMain();
    }

    //获取融云的token之后,连接到融云的IM服务器
    public static void connect_rong(final Activity act, String token, final OnRongImConnectOkListener listener) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e("LoginActivity", "onTokenIncorrect");
//                YcToastHelper.get()._toast("融云Token错误");
            }

            @Override
            public void onSuccess(String userid) {
                Log.e("LoginActivity", "连接融云IM成功,userid=" + userid);
//                YcToastHelper.get()._toast("连接融云IM成功,userid=" + userid);
                if (listener != null) {
                    listener.jumpMain();
                }
//                jumpMain();

//                sendMsg();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
//                YcToastHelper.get()._toast("连接融云失败,errorCode=" + errorCode);
                Log.e("LoginActivity", "ErrorCode");
            }


        });

    }

}
