package com.example.zikanwariapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    /////////AlarmManager(三上)////////////////////////////////
    private AlarmManager alarmMgr;
    private final int[][] TIME_FOR_NOTIFICATION = {{8,35},{10,15},{12,45},{14,25},{16,5}};
    //////////////////////////////////////////////////////////


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
        ////////////////ボタンの追加(三上)/////////////////////////
        LinearLayout llJugyo = findViewById(R.id.ll_jugyo);
        DataBaseOperator operator = new DataBaseOperator(MainActivity.this);
        operator.setAllButton(MainActivity.this,new OnButtonClick(),llJugyo);
//        ((LinearLayout)llJugyo.getChildAt(2)).removeViewAt(0);


        //通知の設定
        setAlarm(operator);
        Log.i("test","mainactivity started.");
        /////////////////////////////////////////////////////////

        //-----通知機能の追加(宮崎)-------------------------------------------------//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel
                    = new NotificationChannel("CHANNEL_ID", "サンプルアプリ", importance);


            channel.setDescription("説明・説明 ここに通知の説明を書くことができます");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        //------------------------------------------------------------------//

//        ボタンに機能をセット
        Button reset = findViewById(R.id.bt_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseOperator btOperator = new DataBaseOperator(MainActivity.this);
                for (int i = 1; i <= 25; i++) {
                    btOperator.saveData(i, "", "", 1);
                }
                operator.setAllButton(MainActivity.this,new OnButtonClick(),llJugyo);
            }



        });

        Button all_off = findViewById(R.id.bt_notify_off);
        all_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseOperator btOperator = new DataBaseOperator(MainActivity.this);
                for (int i = 1; i <= 25; i++) {
                    String[] data = btOperator.getDataById(i);
                    operator.saveData(i, data[0], data[1], 1);
                }
                operator.setAllButton(MainActivity.this,new OnButtonClick(),llJugyo);

            }
        });

    }
    //------------------------ライフサイクル(宮崎)----------------------------//
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
    //------------------------------------------------------------------//

    ///////////-------------ボタンの機能(宮崎・三上)---------------//////////////////////
    public class OnButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Button clickedButton = (Button) view;//(三上)
            String tag = (String) clickedButton.getTag();//(三上)
            Intent intent=new Intent(MainActivity.this,SubActivity.class); //(宮崎)
            intent.putExtra("tag",tag);//(三上)
            startActivity(intent); //(宮崎)
        }
    }
    ////////////--------------------------------------------------//////////////////////


    ////////////////////////startAlarm,setAlarm(三上)////////////////////////////////////////////////
    public void startAlarm(int id,Calendar calendar){
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("id",id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_IMMUTABLE);
// setRepeating() lets you specify a precise custom interval--in this case,
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY*7, alarmIntent);
        Log.i("test","Set id="+id+" "+calendar.getTime());
    }

    public void setAlarm(DataBaseOperator operator){
        alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            alarmMgr.cancelAll();
        }

        for(int i = 1;i <= 25;i++){
            String[] data = operator.getDataById(i);
            int notification = Integer.parseInt(data[2]);
            //通知ONだったら
            if(notification == 0){
                int targetWeek = i%5==0 ? 6 : i%5+1;
                int targetTime = (i-1)/5;
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                for(int j = 1;j <= 8;j++){
                    Calendar now = Calendar.getInstance();
                    now.setTimeInMillis(System.currentTimeMillis());
                    if(calendar.get(Calendar.DAY_OF_WEEK) == targetWeek){
                        calendar.set(Calendar.HOUR_OF_DAY, TIME_FOR_NOTIFICATION[targetTime][0]);
                        calendar.set(Calendar.MINUTE, TIME_FOR_NOTIFICATION[targetTime][1]);
                        calendar.set(Calendar.SECOND,0);
                        if(calendar.after(now)) {
                            startAlarm(i, calendar);
                            break;
                        } else {
                            calendar.add(Calendar.DATE,1);
                        }
                    } else {
                        calendar.add(Calendar.DATE,1);
                    }
                }
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////



}