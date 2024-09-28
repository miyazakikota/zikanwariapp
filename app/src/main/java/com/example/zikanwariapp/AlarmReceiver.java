package com.example.zikanwariapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



public class AlarmReceiver extends BroadcastReceiver {

    //-----------------------通知の内容---------------------------------------------
    public void notifyTest(Context context) {
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(context, "CHANNEL_ID")     // ・・・(4)
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setContentTitle("の出席時間です")
                .setContentText("出欠登録が必要な場合は忘れないでください！")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager
                = NotificationManagerCompat.from(context);

        notificationManager.notify(R.string.app_name, builder.build());
        // ・・・(5)
    }
    //---------------------------------------------------------------------------------
    @Override
    public void onReceive(Context context, Intent intent) {
        // 作りたい処理を書く
        Log.i("test","receiver move");
        Log.i("test","context is "+context);

        notifyTest(context);

    }

}
