package com.fjycnet.rongclouddemo.util;

import android.content.Context;
import android.util.Log;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class NotificationReceiver extends PushMessageReceiver {

    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        Log.e(NotificationReceiver.class.getName(), "onNotificationMessageArrived");
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        Log.e(NotificationReceiver.class.getName(), "onNotificationMessageClicked");
        return false;
    }
}