package com.example.zikanwariapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
                //通知のアイコンを設定
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                //通知のタイトル
                .setContentTitle(data[0]+"の出席時間です")
                //通知の詳細テキスト
                .setContentText("出欠登録が必要な場合は忘れないでください！")
                //通知の優先度(ヘッドアップ通知)
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
        //        アラームの再設定
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        i.putExtra("id",id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, i, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+AlarmManager.INTERVAL_DAY*7,alarmIntent);
        Log.i("test","System.currentTimeMillis() is "+System.currentTimeMillis());

        notifyTest(context,intent);

    }
    //////////////////////////////////////////////////////////////////////
}

