package com.example.zikanwariapp;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

///////////////////////データベース(三上)//////////////////////////////////////////////


//このクラスでは、アプリ固有のデータベース利用処理を扱う
//製作者：三上

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



//  String型の配列　{name,room,notification}　を返す
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
        //        もしもすでにLinearLayoutにボタンが設置されていたら一度ボタンを全て削除する
        for(int i = 2;i < 7;i++){
            LinearLayout child = (LinearLayout) llJugyo.getChildAt(i);
            if(child.getChildCount() >= 2){
                for(int j = 1;j < 6;j++){
                    child.removeViewAt(1);
                }
            }
        }

        if(llJugyo.getChildCount() >= 2){

        }
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
            if(room.length() > 6) room = room.substring(0,5)+"…";
            String showText=name+"\n \n"+room+"\n";
            Button bt = new Button(activity);
            bt.setTag(Integer.toString(id));
            bt.setText(showText);
//            通知がOFFだったら1,OFFなら0
            if(notification == 1) bt.setBackground(context.getDrawable(R.drawable.my_button));
            else bt.setBackground(context.getDrawable(R.drawable.my_button_notfy_on));
            bt.setOnClickListener(listener);
            Log.d("check_showText",showText);
            float scale = activity.getResources().getDisplayMetrics().density;

            LinearLayout.LayoutParams btParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            btParams.weight = 1;

            bt.setLayoutParams(btParams);

            //llJugyoの1+(i+5)/5番目のLinearLayoutにボタンを追加
            ((LinearLayout)llJugyo.getChildAt(1+(n+5)/5)).addView(bt);
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
//////////////////////////////////////////////////////////////////////////////////////
