package com.example.zikanwariapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

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
/////////////////////////////データベース(三上)/////////////////////////////////////////////////////////////////
        //初期設定(画面を開いたときに、データベースからデータを読み出してEditTextとSwitchにデータを反映する処理)
        EditText etLecture = findViewById(R.id.et_lecture);
        EditText etPlace = findViewById(R.id.et_place);
        Switch switchNotice = findViewById(R.id.switch_notice);
        DataBaseOperator operator = new DataBaseOperator(SubActivity.this);
        Intent intent = getIntent();
        String tag = intent.getStringExtra("tag");
        String[] contents = operator.getDataById(Integer.parseInt(tag));
        etLecture.setText(contents[0]);
        etPlace.setText(contents[1]);
        if(Objects.equals(contents[2], "0")) switchNotice.setChecked(true);
        else switchNotice.setChecked(false);


        //保存ボタンの処理
        ((Button)findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //入力の取得
                String lecture = etLecture.getText().toString();
                String place = etPlace.getText().toString();
                int notification = 1;
                if(switchNotice.isChecked()){
                    notification = 0;
                }
                int id = Integer.parseInt(tag);


                operator.saveData(id,lecture,place,notification);

                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //削除ボタンの処理
        ((Button)findViewById(R.id.bt_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(tag);
                operator.saveData(id,"","",1);
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////
    }

//---------------------------------ライフサイクル(宮崎)---------------------------------------------//
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
//--------------------------------------------------------------------------------------------//


}