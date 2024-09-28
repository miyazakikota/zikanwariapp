package com.example.zikanwariapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // idの取得
        int id = intent.getIntExtra("id",0);
        if(id == 0) {
            Log.e("test","idを受け取れませんでした");
            return;
        }
        Log.i("test","receiver "+String.valueOf(id)+" move");
    }

}
