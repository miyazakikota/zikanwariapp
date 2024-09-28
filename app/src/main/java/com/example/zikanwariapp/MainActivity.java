package com.example.zikanwariapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
//まいとだよ２
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
        startAlarm();
        Log.i("test","mainactivity started.");
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


    public void startAlarm(){
        AlarmManager alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 29);

// setRepeating() lets you specify a precise custom interval--in this case,
        int interval = 1000;
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000, alarmIntent);
    }
}