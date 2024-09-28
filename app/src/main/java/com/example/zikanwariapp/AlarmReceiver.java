package com.example.zikanwariapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 作りたい処理を書く
        Log.i("test","receiver move");
        Log.i("test","context is "+context);

    }
}
