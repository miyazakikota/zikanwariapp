package com.example.zikanwariapp;

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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;

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

//        //----------アラーム機能の追加------------------//
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerfetch.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, 3000, AlarmManager.INTERVAL_DAY*7, pendingIntent);
//        //-------------------------------------------//
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

//-------------------------------------------------------------------------------
// BoardcastReceiverを継承
public class AlarmManagerfetch extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 作りたい処理を書く
    }
}
//-------------------------------------------------------------------------------

}