package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


//このクラスでは、アプリ固有のデータベース利用処理を扱う--------------

public class DataBaseOperator {
    private Context context;

    DataBaseOperator(Context context){
        this.context = context;
        //初回起動時のみデータベースの初期化
        DataBaseHelper helper = new DataBaseHelper(this.context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM lectures";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()) {
            Log.i("DataBaseOperator","cursor.moveToNext() is not null");
        } else {
            Log.i("DataBaseOperator","cursor.moveToNext() is null");
            initializeDataBase();
        }
        if(helper != null) helper.close();

    }



    public String[] getDataById(int id){

        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM lectures WHERE _id="+String.valueOf(id);
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();

        int nameIdx = cursor.getColumnIndex("name");
        int roomIdx = cursor.getColumnIndex("room");
        int notificationIdx = cursor.getColumnIndex("notification");

        String name = cursor.getString(nameIdx);
        String room = cursor.getString(roomIdx);
        int notification = cursor.getInt(notificationIdx);
        String[] result = {name,room,String.valueOf(notification)};

        if(helper != null) helper.close();
        return result;
    }


    public void saveData(int id,String name,String room,int notification){
        DataBaseHelper helper = new DataBaseHelper(this.context);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("room", room);
        cv.put("notification",notification);
        db.update("lectures",cv,"_id = ?",args);
        Log.d("saveData",String.valueOf(id)+","+name+","+room+","+String.valueOf(notification));
        if(helper != null) helper.close();

    }

    public void setAllButton(Context activity, MainActivity.OnButtonClick listener, LinearLayout llJugyo) {
        //データの取得
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM lectures";
        Cursor cursor = db.rawQuery(sql, null);

        //ボタンを作成、LinearLayoutにセットする
        int idIdx = cursor.getColumnIndex("_id");
        int nameIdx = cursor.getColumnIndex("name");
        int roomIdx = cursor.getColumnIndex("room");
        int notificationIdx = cursor.getColumnIndex("notification");

        int n = 0;
        while (cursor.moveToNext()) {
            //ボタンの作成
            int id = cursor.getInt(idIdx);
            String name = cursor.getString(nameIdx);
            String room = cursor.getString(roomIdx);
            int notification = cursor.getInt(notificationIdx);

            if(name.length() > 6) name = name.substring(0,5)+"…";
            String showText=name+"\n \n"+room+"\n";
            Button bt = new Button(activity);
            bt.setTag(Integer.toString(id));
            bt.setText(showText);
            bt.setOnClickListener(listener);
            Log.d("check_showText",showText);
//            float scale = activity.getResources().getDisplayMetrics().density;
//            int margin = (int)(scale*1);

            LinearLayout.LayoutParams btParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
//            btParams.setMargins(margin,margin,margin,margin);
            btParams.weight = 1;
            bt.setLayoutParams(btParams);

            //llJugyoの1+(i+5)/5番目のLinearLayoutにボタンを追加
            ((LinearLayout)llJugyo.getChildAt((n+5)/5)).addView(bt);
            n++;
        }
        cursor.close();
        if(helper != null) helper.close();
    }





    private void initializeDataBase() {
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        for (int i = 1;i <= 25;i++) {
            String sqlInsert = "INSERT INTO lectures (_id, name, room, notification) VALUES (?, ?, ?, ?)";
            SQLiteStatement stmt = db.compileStatement(sqlInsert);
            stmt.bindLong(1, i);
            stmt.bindString(2, "");
            stmt.bindString(3, "");
            stmt.bindLong(4, 1);
            stmt.executeInsert();
        }

        if(helper != null) helper.close();
    }


}
