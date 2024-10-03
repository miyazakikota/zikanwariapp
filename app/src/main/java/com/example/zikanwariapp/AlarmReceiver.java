package com.example.zikanwariapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class AlarmReceiver extends BroadcastReceiver {



    //-----------------------通知の内容（宮崎）------------------------------------------
    public void notifyTest(Context context,Intent intent) {
        int id = intent.getIntExtra("id",0);
        DataBaseOperator operator = new DataBaseOperator(context);
        String[] data = operator.getDataById(id);
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setContentTitle(data[0]+"の出席時間です")
                .setContentText("出欠登録が必要な場合は忘れないでください！")
                //ヘッドアップ通知
                .setPriority(NotificationCompat.PRIORITY_MAX);


        NotificationManagerCompat notificationManager
                = NotificationManagerCompat.from(context);

        notificationManager.notify(R.string.app_name, builder.build());
    }
    //---------------------------------------------------------------------------------

    ///////////////////////////onReceive(三上)///////////////////////////
    @Override
    public void onReceive(Context context, Intent intent) {

        // idの取得
        int id = intent.getIntExtra("id",0);
        if(id == 0) {
            Log.e("test","idを受け取れませんでした");
            return;
        }
        Log.i("test","receiver "+String.valueOf(id)+" move");

        notifyTest(context,intent);

    }
    //////////////////////////////////////////////////////////////////////
}

