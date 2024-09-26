package com.example.zikanwariapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Zikanwari App","Sub onCreate() called.");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onStart(){
        Log.i("Zikanwari App","Sub onStart() called.");
        super.onStart();
    }
    @Override
    public void onRestart(){
        Log.i("Zikanwari App","Sub onRestart() called.");
        super.onRestart();
    }
    @Override
    public void onResume(){
        Log.i("Zikanwari App","Sub onResume() called.");
        super.onResume();
    }
    @Override
    public void onPause(){
        Log.i("Zikanwari App","Sub onPause() called.");
        super.onPause();
    }
    @Override
    public void onStop(){
        Log.i("Zikanwari App","Sub onStop() called.");
        super.onStop();
    }
    @Override
    public void onDestroy(){
        Log.i("Zikanwari App","Sub onDestroy() called.");
        super.onDestroy();
    }

    public  void onClick(View view){
        EditText lecture=findViewById(R.id.et_lecture);
        EditText place=findViewById(R.id.et_place);


    }

    public void onButtonClick(View view) {
        finish();
    }


}