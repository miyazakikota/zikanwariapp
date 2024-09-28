package com.example.zikanwariapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
//まいとだよ２
    private AlarmManager alarmMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Zikanwari App","Main onCreate() called.");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //-------------追加---------------------------------------------------------------------
        LinearLayout llJugyo = findViewById(R.id.ll_jugyo);
        DataBaseOperator operator = new DataBaseOperator(MainActivity.this);
        operator.setAllButton(MainActivity.this,new OnButtonClick(),llJugyo);
//---------------------------------------------------------------------------------------
        alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            alarmMgr.cancelAll();
        }
        for(int i = 1;i <= 25;i++){
            String[] data = operator.getDataById(i);
            int notification = Integer.parseInt(data[2]);
//            通知ONだったら
            if(notification == 0){
                startAlarm(i,11,2);
            }
        }

        Log.i("test","mainactivity started.");

        //-----通知機能の追加---------------------//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {        // ・・・(1)
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel                              // ・・・(2)
                    = new NotificationChannel("CHANNEL_ID", "サンプルアプリ", importance);

            channel.setDescription("説明・説明 ここに通知の説明を書くことができます");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //いつ処理するか
        //--------------------------------------//
    }
    
    @Override
    public void onStart(){
        Log.i("Zikanwari App","Main onStart() called.");
        super.onStart();
    }
    @Override
    public void onRestart(){
        Log.i("Zikanwari App","Main onRestart() called.");
        super.onRestart();
    }
    @Override
    public void onResume(){
        Log.i("Zikanwari App","Main onResume() called.");
        super.onResume();
    }
    @Override
    public void onPause(){
        Log.i("Zikanwari App","Main onPause() called.");
        super.onPause();
    }
    @Override
    public void onStop(){
        Log.i("Zikanwari App","Main onStop() called.");
        super.onStop();
    }
    @Override
    public void onDestroy(){
        Log.i("Zikanwari App","Main onDestroy() called.");
        super.onDestroy();
    }
    //-------------------変更(もともとあったonButtonClickに上書き)----------------------------
    public class OnButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Button clickedButton = (Button) view;
            String tag = (String) clickedButton.getTag();
            Intent intent=new Intent(MainActivity.this,SubActivity.class);
            intent.putExtra("tag",tag);
            startActivity(intent);
        }
    }
//-----------------------------------------------------------------------------


    public void startAlarm(int id,int hour,int minute){
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("id",id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_IMMUTABLE);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

// setRepeating() lets you specify a precise custom interval--in this case,
        int interval = 1000;
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000*60, alarmIntent);


    }


}